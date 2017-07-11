package com.shine.constant;

/**
 * 结果bean
 */
public class ResultBean {
    protected boolean success;
    protected String message;


    public ResultBean() {
    }

    public ResultBean(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
