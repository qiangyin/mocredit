package com.mocredit.integral.controller;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.mocredit.integral.adapter.IntegralBankAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.IpUtil;
import com.mocredit.base.web.BaseController;
import com.mocredit.integral.entity.InResponseLog;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.HttpRequestService;
import com.mocredit.integral.service.InResponseLogService;
import com.mocredit.integral.util.Utils;
import com.mocredit.integral.vo.ConfirmInfoVo;

public class IntegralBaseController extends BaseController {
    @Autowired
    private InResponseLogService inResponseLogService;
    @Autowired
    private HttpRequestService httpRequstService;

    public static String getIpAddr(HttpServletRequest request) {
        return IpUtil.getIp(request);
    }

    protected String renderJSONString(boolean success, String errorMsg,
                                      String errorCode, Object data) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("success", success);
        map.put("errorMsg", errorMsg);
        map.put("errorCode", errorCode);
        if (null == data) {
            data = "";
        }
        map.put("data", data);
        String jsonStr = JSON.toJSONString(map);
        saveReponseLog(getRequestId(), jsonStr);
        return jsonStr;
    }

    private void saveReponseLog(Integer requestId, String jsonStr) {
        inResponseLogService.save(new InResponseLog(requestId, jsonStr));
    }

    /**
     * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
     *
     * @param url
     * @param order
     * @return
     */
    protected boolean doGetAndSaveOrder(String url, Order order) {
        return httpRequstService.doGetAndSaveOrder(getRequestId(), url, order);
    }

    /**
     * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
     *
     * @param url
     * @param paramMap
     * @param order
     * @return
     */
    protected boolean doPostAndSaveOrder(String url, Map<?, ?> paramMap,
                                         Order order, Response resp) {
        return httpRequstService.doPostAndSaveOrder(getRequestId(), url,
                paramMap, order, resp);
    }

    /**
     * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
     *
     * @param param
     * @param order
     * @param resp
     * @return
     */
    protected boolean doPostJsonAndSaveOrder(String param,
                                             Order order, Response resp) {
        return httpRequstService.doPostJsonAndSaveOrder(getRequestId(), param, order, resp);
    }

    /**
     * @param url
     * @param paramMap
     * @return
     */
    protected boolean paymentRevoke(String url, Map<?, ?> paramMap,
                                    Response resp) {
        return httpRequstService.paymentRevoke(getRequestId(), url, paramMap,
                resp);
    }

    /**
     * @param param
     * @param device
     * @param orderId
     * @param resp
     * @return
     */
    protected boolean paymentRevokeJson(String param, String device, String orderId, Response resp) {
        return httpRequstService.paymentRevokeJson(getRequestId(), param, device, orderId, resp);
    }

    /**
     * 积分冲正
     *
     * @param url
     * @param paramMap
     * @return
     */
    protected boolean paymentReserval(String url, Map<?, ?> paramMap,
                                      Response resp) {
        return httpRequstService.paymentReserval(getRequestId(), url, paramMap,
                resp);
    }

    /**
     * 积分冲正
     *
     * @param param
     * @param orderId
     * @param resp
     * @return
     */
    protected boolean paymentReservalJson(String param, String orderId,
                                          Response resp) {
        return httpRequstService.paymentReservalJson(getRequestId(), param, orderId, resp);
    }

    /**
     * 积分撤销冲正
     *
     * @param url
     * @param paramMap
     * @return
     */
    protected boolean paymentRevokeReserval(String url, Map<?, ?> paramMap,
                                            Response resp) {
        return httpRequstService.paymentRevokeReserval(getRequestId(), url,
                paramMap, resp);
    }

    /**
     * 积分撤销冲正
     *
     * @param param
     * @param orderId
     * @param resp
     * @return
     */
    protected boolean paymentRevokeReservalJson(String param, String orderId,
                                                Response resp) {
        return httpRequstService.paymentRevokeReservalJson(getRequestId(), param, orderId, resp);
    }

    /**
     * 积分查询
     *
     * @param url
     * @param paramMap
     * @return
     */
    public boolean confirmInfo(String url, Map<?, ?> paramMap, Response resp) {
        return httpRequstService.confirmInfo(getRequestId(), url, paramMap,
                resp);
    }

    /**
     * 积分查询
     *
     * @param param
     * @param confirmInfo
     * @param resp
     * @return
     */
    public boolean confirmInfoJson(String param, ConfirmInfoVo confirmInfo, Response resp) {
        return httpRequstService.confirmInfoJson(getRequestId(), param,
                confirmInfo, resp);
    }

    /**
     * 活动同步
     *
     * @param url
     * @param paramMap
     * @return
     */
    public boolean activityImport(String url, Map<?, ?> paramMap, Response resp) {
        return httpRequstService.activityImport(getRequestId(), url, paramMap,
                resp);
    }

    /**
     * 获取request_id
     *
     * @return
     */
    public Integer getRequestId() {
        String requestId = (null == request.getAttribute("request_id") ? "0"
                : request.getAttribute("request_id").toString());
        return Integer.valueOf(requestId);
    }
}
