package com.mocredit.integral.entity;

/**
 * @author ytq 2015年8月26日
 */
public class Store {
	/**
	 * uuid int not null auto_increment, shop_id int comment '商户ID', store_id
	 * int comment '门店ID', activity_id int comment '活动ID', primary key (uuid)
	 */
	private Integer uuid;
	private String shopId;
	private String storeId;
	private String activityId;

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	public String toString() {
		return "Store [" + (uuid != null ? "uuid=" + uuid + ", " : "")
				+ (shopId != null ? "shopId=" + shopId + ", " : "")
				+ (storeId != null ? "storeId=" + storeId + ", " : "")
				+ (activityId != null ? "activityId=" + activityId : "") + "]";
	}

}
