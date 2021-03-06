package com.mocredit.integral.vo;

import java.io.Serializable;

public class ConfirmInfoVo implements Serializable {
	/**
	 * http请求参数 bank : //银行代码 中信：citic，民生：cmbc activityId:"" , //活动ID cardNum:""
	 * //卡号 shopId 店铺id integral 消费积分
	 */
	private static final long serialVersionUID = -1907082923614009830L;
	private String bank;
	private String activityId;
	private String cardNum;
	private String shopId;
	private String productType;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "ConformInfoVo [bank=" + bank + ", activityId=" + activityId
				+ ", cardNum=" + cardNum + ", shopId=" + shopId
				+ ", productType=" + productType + "]";
	}
}
