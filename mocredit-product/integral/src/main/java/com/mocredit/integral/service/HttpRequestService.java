package com.mocredit.integral.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.constant.OrderStatus;
import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.OutRequestLog;
import com.mocredit.integral.entity.OutResponseLog;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.util.DateTimeUtils;
import com.mocredit.integral.util.HttpRequestUtil;

@Service
public class HttpRequestService extends LogService {
	@Autowired
	private OutRequestLogService outRequestLogService;
	@Autowired
	private OutResponseLogService outResponseLogService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ActivityService activityService;
	ObjectMapper objectMapper = new ObjectMapper();

	public boolean doGetAndSaveOrder(Integer requestId, String url, Order order) {
		String response = doGet(requestId, url);
		// TODO 解析reponse 如果成功就保存order

		return true;
	}

	public int getActTRCount(String maxType) {
		// 暂定01代表每日，02代表每周，03代表每月，空代表不限制',
		int count = 0;
		switch (maxType) {
		case "01":
			Date now = new Date();
			ActivityTransRecord atr01 = activityService.statCountByTime(now,
					now);
			if (atr01 != null) {
				count = atr01.getTransCount();
			}

			break;
		case "02":
			Calendar cal02S = Calendar.getInstance();
			cal02S.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Calendar cal02E = Calendar.getInstance();
			cal02E.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal02E.add(Calendar.DATE, 6);
			ActivityTransRecord atr02 = activityService.statCountByTime(
					cal02S.getTime(), cal02E.getTime());
			if (atr02 != null) {
				count = atr02.getTransCount();
			}
			break;
		case "03":
			Calendar cal03S = Calendar.getInstance();
			cal03S.set(Calendar.DAY_OF_MONTH, 1);

			Calendar cal03E = Calendar.getInstance();
			cal03E.set(Calendar.DAY_OF_MONTH,
					cal03E.getActualMaximum(Calendar.DAY_OF_MONTH));

			ActivityTransRecord atr03 = activityService.statCountByTime(
					cal03S.getTime(), cal03E.getTime());
			if (atr03 != null) {
				count = atr03.getTransCount();
			}
			break;
		}
		return count;
	}

	public boolean doPostAndSaveOrder(Integer requestId, String url,
			Map<?, ?> paramMap, Order order, Response resp) {
		try {
			Activity activity = activityService.getByActivityId(order
					.getActivityId());
			if (activity != null) {
				order.setActivityName(activity.getActivityName());
				Date now = new Date();
				if (activity.getStartTime().getTime() <= now.getTime()
						&& now.getTime() <= activity.getEndTime().getTime()
						&& (activity.getSelectDate().contains(DateTimeUtils
								.getWeekOfDate(now)))) {
				} else {
					resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_DATE
							.getValue());
					return false;
				}
				if (activity.getMaxType() != null
						&& !"".equals(activity.getMaxType())) {
					if (activity.getMaxNumber() != null
							&& activity.getMaxNumber() < getActTRCount(activity
									.getMaxType())) {
						resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_COUNT
								.getValue());
						return false;
					}
				}
			} else {
				resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getValue());
				return false;
			}
			String response = doPost(requestId, url, paramMap);
			boolean anaFlag = analyReponse(requestId, url, paramMap, response,
					resp);
			if (anaFlag) {
				order.setRequestId(requestId);
				order.setStatus(OrderStatus.FINISH.getValue());
				if (orderService.save(order)) {
					resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getValue());
					return false;
				} else {
					return true;
				}
			} else {
				return anaFlag;
			}
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### doPostAndSaveOrder url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean analyReponse(Integer requestId, String url,
			Map<?, ?> paramMap, String reponse, Response resp) {
		if (reponse == null) {
			resp.setErrorCode(ErrorCodeType.POST_BANK_ERROR.getValue());
			return false;
		}
		try {
			return true;
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.ANA_RESPONSE_ERROR.getValue());
			LOGGER.error("### doPost url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean paymentRevoke(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentRevoke url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean paymentReserval(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentReserval url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean paymentRevokeReserval(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentRevokeReserval url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean confirmInfo(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### confirmInfo url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean activityImport(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### activityImport url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	private String doGet(Integer requestId, String url) {
		try {
			LOGGER.info("### doGet url={},requestId={} ###", url, requestId);
			outRequestLogService.save(new OutRequestLog(requestId, url, ""));
			String response = HttpRequestUtil.doGet(url);
			outResponseLogService.save(new OutResponseLog(requestId, response));
			return response;
		} catch (Exception e) {
			LOGGER.error("### doGet url={},requestId={},error={}", url,
					requestId, e);
			return null;
		}
	}

	private String doPost(Integer requestId, String url, Map<?, ?> paramMap) {
		try {
			LOGGER.info("### doPost url={},requestId={},params={} ###", url,
					requestId, paramMap.toString());
			outRequestLogService.save(new OutRequestLog(requestId, url,
					paramMap.toString()));
			String response = HttpRequestUtil.doPost(url, paramMap);
			outResponseLogService.save(new OutResponseLog(requestId, response));
			return response;
		} catch (Exception e) {
			LOGGER.error("### doPost url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return null;
		}
	}
}
