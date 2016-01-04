package com.mocredit.manage.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Terminal {
	public static final Byte STATUS_PAUSE = 0;
	public static final Byte STATUS_ACTIVED = 1;
	public static final Byte STATUS_DELETED = 2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.store_id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String storeId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.terminal_code
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String terminalCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.create_time
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.online_time
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Date onlineTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.status
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Byte status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.version_no
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String versionNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.admin_password
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String adminPassword;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.batch_no
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String batchNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.deskey
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String deskey;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.mackey
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String mackey;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.login_password
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String loginPassword;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_terminal.login_num
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Integer loginNum;

	private String snCode;
	private String merchantName;
	private String storeName;
	private String storeCode;
	private String supplierId;// 供应商ID
	private String type;// 机具类型ID
	private String supplierName;// 供应商名
	private String typeName;// 机具类型名
	private String gateway;// 网关
	private String info;// 操作日志

	public String getGateway() {
		return gateway;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.id
	 *
	 * @return the value of t_terminal.id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.id
	 *
	 * @param id
	 *            the value for t_terminal.id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.store_id
	 *
	 * @return the value of t_terminal.store_id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.store_id
	 *
	 * @param storeId
	 *            the value for t_terminal.store_id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.terminal_code
	 *
	 * @return the value of t_terminal.terminal_code
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getTerminalCode() {
		return terminalCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.terminal_code
	 *
	 * @param terminalCode
	 *            the value for t_terminal.terminal_code
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.create_time
	 *
	 * @return the value of t_terminal.create_time
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.create_time
	 *
	 * @param createTime
	 *            the value for t_terminal.create_time
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.online_time
	 *
	 * @return the value of t_terminal.online_time
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Date getOnlineTime() {
		return onlineTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.online_time
	 *
	 * @param onlineTime
	 *            the value for t_terminal.online_time
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.status
	 *
	 * @return the value of t_terminal.status
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.status
	 *
	 * @param status
	 *            the value for t_terminal.status
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.version_no
	 *
	 * @return the value of t_terminal.version_no
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.version_no
	 *
	 * @param versionNo
	 *            the value for t_terminal.version_no
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.admin_password
	 *
	 * @return the value of t_terminal.admin_password
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.admin_password
	 *
	 * @param adminPassword
	 *            the value for t_terminal.admin_password
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.batch_no
	 *
	 * @return the value of t_terminal.batch_no
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.batch_no
	 *
	 * @param batchNo
	 *            the value for t_terminal.batch_no
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.deskey
	 *
	 * @return the value of t_terminal.deskey
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getDeskey() {
		return deskey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.deskey
	 *
	 * @param deskey
	 *            the value for t_terminal.deskey
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setDeskey(String deskey) {
		this.deskey = deskey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.mackey
	 *
	 * @return the value of t_terminal.mackey
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getMackey() {
		return mackey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.mackey
	 *
	 * @param mackey
	 *            the value for t_terminal.mackey
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setMackey(String mackey) {
		this.mackey = mackey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.login_password
	 *
	 * @return the value of t_terminal.login_password
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.login_password
	 *
	 * @param loginPassword
	 *            the value for t_terminal.login_password
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_terminal.login_num
	 *
	 * @return the value of t_terminal.login_num
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Integer getLoginNum() {
		return loginNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_terminal.login_num
	 *
	 * @param loginNum
	 *            the value for t_terminal.login_num
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
}