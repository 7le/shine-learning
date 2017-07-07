package com.shine.annotation;

@MyComponent
public class InjectClass2Impl implements InjectClass2 {
    @Override
    public void fly() {
        System.out.println("i can fly");
    }
}
