package com.shine.annotation;

import org.springframework.stereotype.Component;

@Component
public class InjectClass3 {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void smile() {
        System.out.println(name + " ^.^");
    }

}
