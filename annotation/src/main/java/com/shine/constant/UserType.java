package com.shine.constant;

public enum UserType implements IEnum {

    MEMBER(0, "普通用户"), ADMIN(1, "管理员"), ;

    private final int key;
    private final String desc;

    UserType(final int key, final String desc) {
        this.key = key;
        this.desc = desc;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String desc() {
        return this.desc;
    }

}
