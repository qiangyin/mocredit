package com.mocredit.integral.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mocredit.integral.entity.*;
import com.mocredit.integral.vo.StoreVo;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.integral.constant.ActivityStatus;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.persistence.ActivityMapper;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.LogService;
import com.mocredit.integral.util.DateTimeUtils;
import com.mocredit.integral.vo.ActivityVo;

/**
 * @author ytq
 */
@Service
public class ActivityServiceImpl extends LogService implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean save(Activity t) {
        return activityMapper.save(t) > 0;
    }

    @Override
    public Activity getByActivityId(String activityId) {
        return activityMapper.getByActivityId(activityId);
    }

    @Override
    public boolean operActivityAndStore(ActivityVo activity, Response resp) {
        boolean flag = true;
        // 1导入 2 更新 3 取消 4 启用
        switch (activity.getOperCode()) {
            case 1:
                Activity ac = getByActivityId(activity.getActivityId());
                if (ac != null) {
                    resp.setErrorCode(ErrorCodeType.EXIST_ACTIVITY_ERROR.getValue());
                    resp.setErrorMsg(ErrorCodeType.EXIST_ACTIVITY_ERROR.getText());
                    flag = false;
                } else {
                    try {
                        for (Store store : activity.getStoreList()) {
                            store.setActivityId(activity.getActivityId());
                        }
                        // 保存活动和对应的店铺
                        saveActivityAndStore(activity, activity.getStoreList());
                    } catch (Exception e) {
                        LOGGER.error(
                                "###operActivityAndStore storeList={} error={}###",
                                e);
                        resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
                        resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
                        flag = false;
                    }

                }
                break;
            case 2:
                try {
                    for (Store store : activity.getStoreList()) {
                        store.setActivityId(activity.getActivityId());
                    }
                    // 保存活动和对应的店铺
                    updateActivityAndStore(activity, activity.getStoreList());
                } catch (Exception e) {
                    LOGGER.error(
                            "###operActivityAndStore storeList={} error={}###", e);
                    resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
                    resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
                    flag = false;
                }
                break;
            case 3:
                if (activityMapper.updateActStatusById(activity.getActivityId(),
                        ActivityStatus.STOP.getValue()) <= 0) {
                    resp.setErrorCode(ErrorCodeType.EXIST_ACTIVITY_ERROR.getValue());
                    resp.setErrorMsg(ErrorCodeType.EXIST_ACTIVITY_ERROR.getText());
                    flag = false;
                }
                break;
            case 4:
                if (activityMapper.updateActStatusById(activity.getActivityId(),
                        ActivityStatus.START.getValue()) <= 0) {
                    resp.setErrorCode(ErrorCodeType.EXIST_ACTIVITY_ERROR.getValue());
                    resp.setErrorMsg(ErrorCodeType.EXIST_ACTIVITY_ERROR.getText());
                    flag = false;
                }
                break;
        }
        return flag;
    }


    @Override
    public Store getByShopIdStoreIdAcId(String storeId, String activityId) {
        return activityMapper.getByShopIdStoreIdAcId(storeId, activityId);
    }

    @Transactional
    public void saveActivityAndStore(Activity activity, List<StoreVo> storeLists) {
        activityMapper.save(activity);
        for (StoreVo storeVo : storeLists) {
            activityMapper.saveStore(storeVo);
            for (Terminal terminal : storeVo.getTerminals()) {
                terminal.setActivityId(activity.getActivityId());
                terminal.setStoreId(storeVo.getStoreId());
            }
        }
    }

    @Transactional
    public void updateActivityAndStore(Activity activity, List<StoreVo> storeLists) {
        // 先删除
        activityMapper.deleteActAndStoreById(activity.getActivityId());
        // 再新增
        saveActivityAndStore(activity, storeLists);
    }

    @Override
    public boolean saveActTransRecord(ActivityTransRecord actRecod) {
        return activityMapper.saveActTransRecord(actRecod) > 0;
    }

    @Override
    public ActivityTransRecord statCountByTime(Date startTime, Date endTime) {
        return activityMapper.statCountByTime(
                DateTimeUtils.parseYYYYMMDD(startTime),
                DateTimeUtils.parseYYYYMMDD(endTime));
    }

    @Override
    public Activity getActivityByOrderId(String orderId) {
        return activityMapper.getActivityByOrderId(orderId);
    }

    @Override
    public List<Activity> getActivityByEnCode(String enCode) {
        return activityMapper.getActivityByEnCode(enCode);
    }

    @Override
    public String getActIdsByEnCode(String enCode) {
        List<String> actIds = new ArrayList<>();
        for (Activity activity : activityMapper.getActivityByEnCode(enCode)) {
            actIds.add(activity.getActivityId());
        }
        return String.join(",", actIds);
    }
}
