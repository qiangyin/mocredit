package com.mocredit.manage.model;

import java.util.Date;

public class Contract {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_contract.id
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	private String id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_contract.enterprise_id
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	private String enterpriseId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_contract.create_time
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_contract.status
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	private Byte status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_contract.name
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	private String name;
	private String enterpriseName;

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_contract.id
	 *
	 * @return the value of t_contract.id
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_contract.id
	 *
	 * @param id
	 *            the value for t_contract.id
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_contract.enterprise_id
	 *
	 * @return the value of t_contract.enterprise_id
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_contract.enterprise_id
	 *
	 * @param enterpriseId
	 *            the value for t_contract.enterprise_id
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_contract.create_time
	 *
	 * @return the value of t_contract.create_time
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_contract.create_time
	 *
	 * @param createTime
	 *            the value for t_contract.create_time
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_contract.status
	 *
	 * @return the value of t_contract.status
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_contract.status
	 *
	 * @param status
	 *            the value for t_contract.status
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_contract.name
	 *
	 * @return the value of t_contract.name
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_contract.name
	 *
	 * @param name
	 *            the value for t_contract.name
	 *
	 * @mbggenerated Thu Oct 29 12:05:06 CST 2015
	 */
	public void setName(String name) {
		this.name = name;
	}
}