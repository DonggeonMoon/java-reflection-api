package com.mdg;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            // Parent 클래스를 클래스 객체로 호출
            Class<?> clazz = Class.forName("com.mdg.Parent");

            // 클래스 객체에서 생성자에 접근하여 객체(인스턴스) 생성
            // 클래스 객체에서 newInstance() 메서드로 객체 생성이 가능하지만
            // 자바 9 이후로 해당 메서드가 deprecated됨
            Parent parent = (Parent)clazz.getConstructor().newInstance();

            // 클래스 객체에서 필드 접근(getField() 메서드 이용)
            // getField() 메서드는 public 필드에만 접근 가능
            Field fieldA = clazz.getField("fieldA");

            System.out.println("fieldA.get(parent) = " + fieldA.get(parent));

            // private 필드에 접근하기 위해서는
            // 필드 객체의 Accessible 값을 true로 변경해야 함
            Field fieldB = clazz.getDeclaredField("fieldB");
            fieldB.setAccessible(true);

            System.out.println("fieldB.get(parent) = " + fieldB.get(parent));

            // 필드 값을 변경하기(set() 메서드 이용)
            fieldB.set(parent, "modifiedValue");

            System.out.println("fieldB.get(parent) = " + fieldB.get(parent));

            // 클래스 객체에서 메서드 접근
            Method methodA = clazz.getDeclaredMethod("methodA");

            // 메서드를 호출하여 실행
            methodA.invoke(parent);

            // Child 클래스를 클래스 객체로 호출
            Class<?> clazz2 = Class.forName("com.mdg.Child");

            // 클래스 객체로 생성자 접근
            Constructor<?> constructor = clazz2.getConstructor(String.class);

            System.out.println("constructor.getName() = " + constructor.getName());

        } catch (ClassNotFoundException | NoSuchFieldException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
