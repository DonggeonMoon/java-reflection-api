package com.mdg;

public class Child extends Parent {
    public String fieldC = "valueC";

    public Child() {
    }

    public Child(String fieldC) {
        this.fieldC = fieldC;
    }

    public void methodB(int integer) {
        System.out.println("methodB" + integer);
    }
}
