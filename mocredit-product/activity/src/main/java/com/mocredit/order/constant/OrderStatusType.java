/**
 *
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 */
public enum OrderStatusType {
    EXCHANGE("0", "消费"),
    REVOCATION("2", "撤消"),
    PAYMENT("01", "积分消费"),
    PAYMENT_REVOKE("02", "消费撤销"),
    PAYMENT_REVERSAL("03", "积分冲正"),
    PAYMENT_REVERSAL_REVOKE("04", "冲正撤销"),
    CONFIRM_INFO("05", "积分查询");
    private String value;
    private String text;

    OrderStatusType(String value, String text) {
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
