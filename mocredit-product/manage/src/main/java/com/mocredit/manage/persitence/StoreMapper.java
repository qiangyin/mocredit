package com.mocredit.manage.persitence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mocredit.manage.model.Store;

public interface StoreMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table t_enterprise
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	int deleteById(@Param("ids") List<String> ids);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table t_enterprise
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	int insert(Store record);

	List<Store> selectAllForPage(Map<String, Object> param);

	Store selectOne(@Param("id") String id);

	int update(Store store);

}