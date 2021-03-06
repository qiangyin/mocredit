package com.mocredit.gateway.constant;

/**
 * Created by ytq on 2016/1/4.
 */
public enum ActivityStatus {
    RUNNING("01", "启用"),
    STOPPING("02", "停用");
    private String value;
    private String text;

    ActivityStatus(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
