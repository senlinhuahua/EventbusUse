package com.forest.eventbususe.bus;

/**
 * Created by forest on 2018/9/13 0013.
 */

public class Forest {
    private String name;
    private String money;

    public Forest(String name, String money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
