package com.forest.eventbususe.bus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by forest on 2018/9/13 0013.
 */

public class Eventbus {

    private static volatile Eventbus instance;

    private Handler mHandler;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    Map<Object,List<SubscriberMethod>> cacheMap;

    private Eventbus(){
        cacheMap = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static Eventbus getDefult(){
        if (instance == null){
            synchronized (Eventbus.class){
                if (instance==null){
                    instance = new Eventbus();
                }
            }
        }
        return instance;
    }

    public void register(Object obg){

        List<SubscriberMethod> list = cacheMap.get(obg);
        if (list == null){
            list = findSubscribeMethod(obg);
            cacheMap.put(obg,list);
        }
    }

    //拿到对应对象里的注解了的方法
    private List<SubscriberMethod> findSubscribeMethod(Object obg) {
        Class<?> clazz = obg.getClass();
        List<SubscriberMethod> list = new ArrayList<>();
        while (clazz != null){
            String classname = clazz.getName();
            //判断是否是系统类
            if (classname.startsWith("java.") || classname.startsWith("javax.")
                    ||classname.startsWith("android.")){
                break;
            }

            Method[] methods = clazz.getMethods();
            for (Method method:methods){
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null){
                    continue;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1){
                    throw new RuntimeException("参数只能有一个");
                }

                ThreadMode threadMode = subscribe.threadMode();
                SubscriberMethod subscriberMethod = new SubscriberMethod(parameterTypes[0], method,threadMode);
                list.add(subscriberMethod);
            }

            //拿到父类
            clazz = clazz.getSuperclass();

        }
        return list;

    }

    public void post(final Object type){
        Iterator iterator = cacheMap.keySet().iterator();
        while (iterator.hasNext()){
            final Object o = iterator.next();
            List<SubscriberMethod> subscriberMethods = cacheMap.get(o);
            for (final SubscriberMethod subscriberMethod:subscriberMethods){
                if (subscriberMethod.getType().isAssignableFrom(type.getClass())){
                    switch (subscriberMethod.getThreadMode()){
                        case POSTING:
                            invoke(subscriberMethod,o,type);
                            break;
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()){
                                invoke(subscriberMethod,o,type);
                            }else {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscriberMethod,o,type);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            if (Looper.myLooper() == Looper.getMainLooper()){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscriberMethod,o,type);
                                    }
                                });
                            }else {
                                invoke(subscriberMethod,o,type);
                            }
                            break;
                    }

                }
            }
        }
    }

    private void invoke(SubscriberMethod subscriberMethod, Object o, Object type) {
        Method method = subscriberMethod.getMethod();
        try {
            method.invoke(o,type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void onDestory(Object obg){
        List<SubscriberMethod> subscriberMethods = cacheMap.get(obg);
        if (subscriberMethods != null){
            cacheMap.remove(subscriberMethods);
        }
    }
}
