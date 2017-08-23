package com.example.administrator.demo.recycleView;

/**
 * Created by samhaus on 2017/8/22.
 */

public class HomeListBean {

    private String itemName;
    private int pos;


    public  HomeListBean(String itemName, int pos) {
        this.itemName = itemName;
        this.pos = pos;
        setItemName(itemName);
        setPos(pos);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
