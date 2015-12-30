package com.mocredit.testcase.activity_synchronization;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.PropertyUtil;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.util.HttpRequestUtil;
/**
 * @author sc-candao-hgy
 * 
 * @Description     活动同步_银行内部代码参数为最大长度30
 * 
 * */

public class Activity_Synchronization_Import_023_MaxProductCode_Test {
	/**
	 * 活动同步参数列表
	 * 
     *  activityId     活动ID
     *  activityName   活动名
     *  productCode    银行内部代码
     *  operCode       操作代码， 1导入 2 更新 3 取消 4 启用 
     *  startTime      活动开始时间，年月日时分秒yyyy-MM-dd HH:mm:ss
     *  endTime        活动结束时间，年月日时分秒yyyy-MM-dd HH:mm:ss
     *  selectDate     指定选择日期（周几），如果是周一和周二，则是1,2,如果是周五周六周日，则是5,6,7  使用英文字符分割
     *  integral       积分
     *  maxType        最大类型，暂定01代表每日，02代表每周，03代表每月，空代表不限制
     *  maxNumber      最大次数
     *  status         01启用，02停止
     *  storeList      门店列表        
     *  [ 
     *   {
     *   storeId       门店Id
     *   shopId        商户Id 
     *   } 
     *  ] 
     *                 其他活动信息待定 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void ActivitySynchronization_Import_MaxProductCode(String baseUrl)throws Exception {
		String activityId = PropertyUtil.getProInfo("parmeter", "activityId");
		String activityName = PropertyUtil.getProInfo("parmeter", "activityName");
		String productCode_max = PropertyUtil.getProInfo("parmeter", "productCode_max");
		String operCode_import = PropertyUtil.getProInfo("parmeter", "operCode_import");
		String startTime = PropertyUtil.getProInfo("parmeter", "startTime");
		String endTime = PropertyUtil.getProInfo("parmeter", "endTime");
		String selectDate_sigle = PropertyUtil.getProInfo("parmeter", "selectDate_sigle");
		String integral = PropertyUtil.getProInfo("parmeter", "integral");
		String maxType_everyday = PropertyUtil.getProInfo("parmeter", "maxType_everyday");
		String maxNumber = PropertyUtil.getProInfo("parmeter", "maxNumber");
		String status_enable = PropertyUtil.getProInfo("parmeter", "status_enable");
		String storeId = PropertyUtil.getProInfo("parmeter", "storeId");
		String shopId = PropertyUtil.getProInfo("parmeter", "shopId");

		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义activityId字段
		Buffer.append("\"activityId\"").append(":").append("\"").append(activityId).append("\"");
		// 定义activityName字段
		Buffer.append("\"activityName\"").append(":").append("\"").append(activityName).append("\"");
		// 定义productCode字段
		Buffer.append("\"productCode\"").append(":").append("\"").append(productCode_max).append("\"");
		// 定义operCode字段
		Buffer.append("\"operCode\"").append(":").append("\"").append(operCode_import).append("\"");
		// 定义startTime字段
		Buffer.append("\"startTime\"").append(":").append("\"").append(startTime).append("\"");
		// 定义endTime字段
		Buffer.append("\"endTime\"").append(":").append("\"").append(endTime).append("\"");
		// 定义selectDate字段
		Buffer.append("\"selectDate\"").append(":").append("\"").append(selectDate_sigle).append("\"");
		// 定义storeNintegralame字段
		Buffer.append("\"integral\"").append(":").append("\"").append(integral).append("\"");
		// 定义maxType字段
		Buffer.append("\"maxType\"").append(":").append("\"").append(maxType_everyday).append("\"");
		// 定义maxNumber字段
		Buffer.append("\"maxNumber\"").append(":").append("\"").append(maxNumber).append("\"");
		// 定义status字段
		Buffer.append("\"status\"").append(":").append("\"").append(status_enable).append("\"");
		// 定义storeList开始
		Buffer.append("\"storeList\"").append(":").append("[").append("{");
		// 定义storeId字段
		Buffer.append("\"storeId\"").append(":").append("\"").append(storeId).append("\"");
		// 定义shopId字段
		Buffer.append("\"shopId\"").append(":").append("\"").append(shopId).append("\"");
		// 定义storeList结束
		Buffer.append("}").append("]");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr = Buffer.toString();
		// 传参数
		String resp = HttpRequestUtil.doPostJson(baseUrl + "activityImport", jsonStr);
		//转换为字符串
		Response response = JSON.parseObject(resp, Response.class);
		//返回结果判定
		Assert.assertEquals(response.getSuccess(), true);


	}
}
