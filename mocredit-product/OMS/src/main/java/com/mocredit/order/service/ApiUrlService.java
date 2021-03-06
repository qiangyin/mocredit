package com.mocredit.order.service;

import org.apache.ibatis.annotations.Param;

import com.mocredit.order.entity.ApiUrl;

public interface ApiUrlService extends BaseService<ApiUrl> {
	/**
	 * 根据code更新
	 * 
	 * @param t
	 * @return
	 */
	public boolean updateByCode(ApiUrl t);

	ApiUrl selectByCode(String code);
}
