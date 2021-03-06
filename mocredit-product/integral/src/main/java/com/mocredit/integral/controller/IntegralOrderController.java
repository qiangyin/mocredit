package com.mocredit.integral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mocredit.integral.dto.OrderDto;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocredit.base.web.BaseController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class IntegralOrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/findOrderByList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String findOrderByList(@RequestBody String param) {
        Response resp = new Response();
        OrderDto orderDto = JSON.parseObject(param, OrderDto.class);
        orderDto.setOffset((orderDto.getPageNum() - 1) * orderDto.getPageSize());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", orderService.findOrderByList(orderDto));
        map.put("pageNum", orderDto.getPageNum());
        map.put("pageSize", orderDto.getPageSize());
        if (!orderDto.isDownload()) {
            map.put("pageCount", orderService.findOrderByListCount(orderDto));
        }
        resp.setData(map);
        return JSON.toJSONString(resp);
    }

    @RequestMapping(value = "/statActIntegral", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String statActIntegral(@RequestBody String param) {
        Response resp = new Response();
        JSONObject jsonObject = JSON.parseObject(param);
        try {
            String actId = jsonObject.getString("activityId");
            resp.setData(orderService.statActIntegral(actId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(resp);
    }

    @RequestMapping(value = "/statActStoreIntegral", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String statActStoreIntegral(@RequestBody String param) {
        Response resp = new Response();
        JSONObject jsonObject = JSON.parseObject(param);
        try {
            String actId = jsonObject.getString("activityId");
            String storeId = jsonObject.getString("storeId");
            resp.setData(orderService.statActStoreIntegral(actId, storeId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(resp);
    }
}
