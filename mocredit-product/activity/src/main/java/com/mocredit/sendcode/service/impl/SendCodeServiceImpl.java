package com.mocredit.sendcode.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.mocredit.sendcode.constant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.Activity;
import com.mocredit.activity.model.ActivityStore;
import com.mocredit.activity.model.Batch;
import com.mocredit.activity.model.BatchBvo;
import com.mocredit.activity.model.BatchCode;
import com.mocredit.activity.model.BatchCodeBvo;
import com.mocredit.activity.model.BatchCodeVO;
import com.mocredit.activity.model.BatchVO;
import com.mocredit.activity.model.Mms;
import com.mocredit.activity.persitence.ActivityStoreMapper;
import com.mocredit.activity.persitence.BatchCodeMapper;
import com.mocredit.activity.persitence.BatchMapper;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.activity.service.MmsframeService;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.ExcelUtil;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.base.util.ValidatorUtil;
import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.persitence.EnterpriseMapper;
import com.mocredit.manage.service.MerchantService;
import com.mocredit.order.service.OrderService;
import com.mocredit.sendcode.service.SendCodeService;
import com.mocredit.sys.service.OptLogService;

import cn.m.mt.common.DateTimeUtils;
import cn.m.mt.common.MMSBO;
import cn.m.mt.common.Variable;
import org.springframework.util.StringUtils;

/**
 * Created by ytq on 2015/10/23.
 * 发码组建接口实现类
 */
@Service
public class SendCodeServiceImpl implements SendCodeService {
    @Autowired
    private BatchMapper batchMapper;
    @Autowired
    private BatchCodeMapper batchCodeMapper;
    @Autowired
    private OptLogService optLogService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityStoreMapper activityStoreMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MmsframeService mmsframeService;

    @Override
    @Transactional
    public List<BatchCode> downloadList(String type, String name, String id, Integer codeCount) {
        //    CODE("01", "验码"), BATCH("02", "批次"), ACTIVITY("03", "活动");
        Map<String, Object> batchMap = new HashMap<>();
        Activity activity = null;
        if (DownloadType.ACTIVITY.getValue().equals(type)) {
            activity = activityService.getActivityById(id);
            try {
                String batchId = activityService.extractedCode(id, name, BatchType.DOWNLOAD.getValue(), codeCount);
                batchMap.put("batchId", batchId);
            } catch (Exception e) {
                throw new BusinessException("提码失败,error:" + e.getMessage());
            }
        }
        if (DownloadType.BATCH.getValue().equals(type)) {
            String actId = batchMapper.getBatchById(id).getActivityId();
            activity = activityService.getActivityById(actId);
            batchMap.put("batchId", id);
        }
        List<BatchCode> batchCodeAllList = new ArrayList<>();
        int pageNum = 1;
        boolean isFinish = false;
        while (!isFinish) {
            PageHelper.startPage(pageNum, pageSize);
            List<BatchCode> batchCodeList = batchCodeMapper.queryBatchCodeList(batchMap);
            if (batchCodeList.isEmpty()) {
                isFinish = true;
            } else {
                batchCodeAllList.addAll(batchCodeList);
                pageNum += 1;
            }
        }
        for (BatchCode batchCode : batchCodeAllList) {
            batchCode.setStatus(BatchCodeStatus.ALREADY_SEND.getValue());
            batchCode.setStartTime(new Date());
            batchCodeMapper.updateBatchCode(batchCode);
        }
        Batch batchOri = batchMapper.getBatchById(batchMap.get("batchId") + "");
        if (!BatchStatus.ALREADY_SEND.getValue().equals(batchOri.getStatus())) {
            carryVerifyCode(activity, batchMap.get("batchId") + "", batchCodeAllList);
        }
        Batch batch = new Batch();
        batch.setId(batchMap.get("batchId") + "");
        batch.setPickNumber(batchCodeAllList.size());
        batch.setPickSuccessNumber(batchCodeAllList.size());
        batch.setSendNumber(batchCodeAllList.size());
        batch.setSendSuccessNumber(batchCodeAllList.size());
        batch.setStatus(BatchStatus.ALREADY_SEND.getValue());
        batchMapper.updateBatch(batch);
        return batchCodeAllList;
    }

    @Override
    public List<BatchBvo> getActBatchList(Map<String, Object> activityMap, Integer draw, Integer pageNum, Integer pageSize) {
        if (draw != null && draw != 1) {
            String searchContent = String.valueOf(activityMap.get("search[value]"));
            if (searchContent != null && !"".equals(searchContent)) {
                activityMap.put("searchInfoContent", searchContent);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return batchMapper.getActBatchList(activityMap);
    }

    @Override
    public List<Object> getBatchDetailList(String batchId, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Object uploadAndSend(String type, List<Object> customerMobileList) {
        return null;
    }

    @Override
    public Object sendCode(String type, String id) {
        return null;
    }

    @Override
    public boolean isExistName(String actId, String name) {
        Map<String, Object> batchMap = new HashMap<>();
        batchMap.put("activityId", actId);
        batchMap.put("batch", name);
        return batchMapper.getBatchTotal(batchMap) > 0;
    }

    @Override
    public Map<String, Object> getSendSmsTypeByCodeId(String id) {
        return batchCodeMapper.getSendSmsTypeByCodeId(id);
    }

    @Override
    public boolean delBatchById(String batchId) {
        Map<String, Object> batchMap = new HashMap<>();
        batchMap.put("batchId", batchId);
        batchMap.put("status", BatchStatus.DEL.getValue());
        return batchMapper.delBatchById(batchMap) > 0;
    }

    public int getBatchCodeTotal(String batchId, String status) {
        Map<String, Object> batchCodeMap = new HashMap<>();
        batchCodeMap.put("batchId", batchId);
        batchCodeMap.put("status", status);
        return batchCodeMapper.getBatchCodeTotal(batchCodeMap);
    }

    @Override
    public boolean sendCodeById(String actId, String id, String type) {
        try {
            List<BatchCode> batchCodeAllList = new ArrayList<>();
            BatchCode batchCode = batchCodeMapper.getBatchCodeById(id);
            batchCodeAllList.add(batchCode);
            switch (type) {
                case ActivityStatus.SMS: {
                    sendCode(actId, batchCode.getBatchId(), batchCodeAllList);
                    break;
                }
                case ActivityStatus.MMS: {
                    sendCodeByMMS(actId, batchCode.getBatchId(), batchCodeAllList);
                    break;
                }
            }
            //更新批次发送成功数量
            Batch batch = new Batch();
            batch.setId(batchCode.getBatchId());
            Integer sendSuc = getBatchCodeTotal(batchCode.getBatchId(), BatchCodeStatus.ALREADY_SEND.getValue());
            Integer importSuc = getBatchCodeTotal(batchCode.getBatchId(), null);
            if (importSuc > sendSuc) {
                batch.setStatus(BatchStatus.PART_ALREADY_SEND.getValue());
            } else {
                batch.setStatus(BatchStatus.ALREADY_SEND.getValue());
            }
            batch.setSendSuccessNumber(sendSuc);
            batch.setSendNumber(sendSuc);
            batchMapper.updateBatch(batch);
            //记录发送短信和保存发码两个步骤的日志
            StringBuffer optInfo1 = new StringBuffer();
            optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
            optLogService.addOptLog("活动Id:" + actId + ",码Id:" + id, "", "发送短信并保存发码记录-----" + optInfo1.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendCodeByBatchId(String actId, String batchId, String type, String sendType) {
        boolean sendSuccessFlag = true;
        try {
            List<BatchCode> batchCodeAllList = new ArrayList<>();
            Map<String, Object> batchMap = new HashMap<>();
            batchMap.put("batchId", batchId);
            if (SendType.BREAKPOINT_SEND.getValue().equals(sendType)) {
                batchMap.put("status", BatchCodeStatus.ALREADY_SEND.getValue());
            }
            int pageNum = 1;
            boolean isFinish = false;
            while (!isFinish) {
                PageHelper.startPage(pageNum, pageSize);
                List<BatchCode> batchCodeList;
                if (SendType.BREAKPOINT_SEND.getValue().equals(sendType)) {
                    batchCodeList = batchCodeMapper.queryBPBatchCodeList(batchMap);
                } else {
                    batchCodeList = batchCodeMapper.queryBatchCodeList(batchMap);
                }
                if (batchCodeList.isEmpty()) {
                    isFinish = true;
                } else {
                    batchCodeAllList.addAll(batchCodeList);
                    pageNum += 1;
                }
            }
            //更新批次导入数量和成功数量
            Batch batch = new Batch();
            batch.setId(batchId);
            //记录发送短信和保存发码两个步骤的日志
            StringBuffer optInfo1 = new StringBuffer();
            optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
            try {
                switch (type) {
                    case ActivityStatus.SMS: {
                        sendCode(actId, batchId, batchCodeAllList);
                        break;
                    }
                    case ActivityStatus.MMS: {
                        sendCodeByMMS(actId, batchId, batchCodeAllList);
                        break;
                    }
                }
                Integer sendSuc = getBatchCodeTotal(batchId, BatchCodeStatus.ALREADY_SEND.getValue());
                Integer importSuc = getBatchCodeTotal(batchId, null);
                batch.setSendSuccessNumber(sendSuc);
                batch.setSendNumber(sendSuc);
                batch.setImportSuccessNumber(importSuc);
                batch.setStatus(BatchStatus.ALREADY_SEND.getValue());
                optInfo1.append("成功数量：" + batchCodeAllList.size() + ";");
            } catch (Exception e) {
                e.printStackTrace();
                int sendSucNum = getBatchCodeTotal(batchId, BatchCodeStatus.ALREADY_SEND.getValue());
                Integer importSuc = getBatchCodeTotal(batchId, null);
                if (sendSucNum > 0) {
                    if (importSuc > sendSucNum) {
                        batch.setStatus(BatchStatus.PART_ALREADY_SEND.getValue());
                    }
                } else {
                    batch.setStatus(BatchStatus.IMPORT_NOT_CARRY.getValue());
                }
                batch.setSendSuccessNumber(sendSucNum);
                batch.setSendNumber(batchCodeAllList.size());
                batch.setImportSuccessNumber(importSuc);
                optInfo1.append("失败数量：" + (batchCodeAllList.size() - sendSucNum) + ";");
                sendSuccessFlag = false;
            }
            batchMapper.updateBatch(batch);
            optLogService.addOptLog("活动Id:" + actId + ",批次Id:" + batchId, "", "发送短信并保存发码记录-----" + optInfo1.toString());
        } catch (Exception e) {
            e.printStackTrace();
            sendSuccessFlag = false;
        }
        return sendSuccessFlag;
    }

    @Override
    public List<BatchCodeBvo> getActBatchCodeList(Map<String, Object> batchMap, Integer draw, Integer pageNum, Integer pageSize) {
        if (draw != null && draw != 1) {
            String searchContent = String.valueOf(batchMap.get("search[value]"));
            if (searchContent != null && !"".equals(searchContent)) {
                batchMap.put("searchInfoContent", searchContent);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BatchCodeBvo> batchCodeBvoList = batchMapper.getActBatchCodeList(batchMap);
        for (BatchCodeBvo batchCodeBvo : batchCodeBvoList) {
            String code = batchCodeBvo.getCode();
            if (code != null && code.length() >= 8) {
                batchCodeBvo.setCode(code.substring(0, 4) + "***" + code.substring(code.length() - 4, code.length()));
            }
        }
        return batchCodeBvoList;
    }

    @Override
    @Transactional
    public Map<String, Object> importCustomor(String activityId, String name, String type, String sType, InputStream in) {
        //定义一个返回Map
        Map<String, Object> msgMap = new HashMap<String, Object>();
        msgMap.put("success", true);
        Map<String, String> resultMap = new HashMap<String, String>();
        //解析excel流,并生成list
        List<List<Object>> excelList = ExcelUtil.excel2List(in);
        String actBatchId = validatorMobile(activityId, name, type, msgMap, resultMap, excelList);
//        Map<String, Object> batchMap = new HashMap<>();
//        batchMap.put("batchId", actBatchId);
//        batchMap.put("status", BatchStatus.IMPORT_NOT_CARRY.getValue());
//        List<BatchCode> batchCodeAllList = new ArrayList<>();
//        int pageNum = 1;
//        boolean isFinish = false;
//        while (!isFinish) {
//            PageHelper.startPage(pageNum, pageSize);
//            List<BatchCode> batchCodeList = batchCodeMapper.queryBatchCodeList(batchMap);
//            if (batchCodeList.isEmpty()) {
//                isFinish = true;
//            } else {
//                batchCodeAllList.addAll(batchCodeList);
//                pageNum += 1;
//            }
//        }
        //更新批次导入数量和成功数量
        Batch batch = new Batch();
        batch.setId(actBatchId);
        batch.setImportNumber(excelList.size() - 1);
        batch.setImportSuccessNumber(excelList.size() - 1);
        batch.setStatus(BatchStatus.IMPORT_NOT_CARRY.getValue());
        batch.setSendNumber(0);
//        batch.setSendNumber(batchCodeAllList.size());
        //记录发送短信和保存发码两个步骤的日志
        StringBuffer optInfo1 = new StringBuffer();
        optInfo1.append("导入数量：" + (excelList.size() - 1) + ";");
//        try {
//            switch (sType) {
//                case ActivityStatus.SMS: {
//                    sendCode(activityId, actBatchId, batchCodeAllList);
//                    break;
//                }
//                case ActivityStatus.MMS: {
//                    sendCodeByMMS(activityId, actBatchId, batchCodeAllList);
//                    break;
//                }
//            }
//            batch.setSendSuccessNumber(batchCodeAllList.size());
//            batch.setStatus(BatchStatus.ALREADY_SEND.getValue());
//            optInfo1.append("成功数量：" + batchCodeAllList.size() + ";");
//            msgMap.put("success", true);
//            msgMap.put("msg", "上传并发送成功" + batchCodeAllList.size() + "条");
//
//        } catch (Exception e) {
//            batch.setSendSuccessNumber(0);
//            batch.setSendFailNumber(batchCodeAllList.size());
//            batch.setStatus(BatchStatus.IMPORT_NOT_CARRY.getValue());
//            optInfo1.append("失败数量：" + batchCodeAllList.size() + ";");
//            msgMap.put("success", false);
//            msgMap.put("msg", "发送失败" + batchCodeAllList.size() + "条");
//        }
        batchMapper.updateBatch(batch);
        optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + actBatchId, "", "导入联系人-----" + optInfo1.toString());
        //返回数据
        return msgMap;
    }

    @Transactional()
    public void sendCode(String actId, String batchId, List<BatchCode> batchCodeList) {
        //定义短信内容对象
        MMSBO duanxin = new MMSBO();
        Activity activity = activityService.getActivityById(actId);
        //送码
        Batch batch = batchMapper.getBatchById(batchId);
        if (!BatchStatus.ALREADY_SEND.getValue().equals(batch.getStatus())) {
            carryVerifyCode(activity, batchId, batchCodeList);
        }
        String noticeSmsMsg = activity.getNoticeSmsMsg();
        ;//短信模版内容
        //获取是否推送短信开关
        boolean isPushSms = Boolean.parseBoolean(PropertiesUtil.getValue("isPushSms"));
        if (isPushSms) {
            //如果推送短信开关开启，为短信内容对象设置一些固定信息
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.setDeliveryPersistent(true);
            duanxin.setBarcodeno(3);
            duanxin.setEitemid(1995L);
            duanxin.setEntid(46L);
            duanxin.setIsresend(1);
            duanxin.setMttype(1);
            duanxin.setPackageid(25898810);
            duanxin.setStatus(0);
            duanxin.setStatuscode("NYYH");
            duanxin.setType(0);

            duanxin.setEorderid(5115538L);
            duanxin.setBarcodeid(73349609L);
            duanxin.setCharcode("E1073SCP70");
            duanxin.setNumberpwd("010073787632");
            duanxin.setTid("20150428111444117862");
            //duanxin.setCreatetime(activity.getEndTime());

        }

        if (isPushSms) {
            //当批次的发送短信类型为合并代码发送时
            if (BatchType.MERGE_SMS.getValue().equals(batch.getSmsType())) {
                List<BatchCode> batchCodes = new ArrayList<>();
                Map<String, List<String>> listMap = new HashMap<>();
                for (final BatchCode batchCode : batchCodeList) {
                    if (listMap.containsKey(batchCode.getCustomerMobile())) {
                        listMap.get(batchCode.getCustomerMobile()).add(batchCode.getCode());
                    } else {
                        listMap.put(batchCode.getCustomerMobile(), new ArrayList<String>() {{
                            add(batchCode.getCode());
                        }});
                        batchCodes.add(batchCode);
                    }
                    //batch_code 状态，状态暂定为01：已提码，02：已导入，03：已送码，未发码，04：已发码
                    // batch 00：已删除 01：已提码，未导入联系人  02：已导入联系人，待送码  03：已送码，待发码 04：已发码
                    Map<String, Object> batchCodeMap = new HashMap<>();
                    batchCodeMap.put("id", batchCode.getId());
                    batchCodeMap.put("status", BatchCodeStatus.ALREADY_SEND.getValue());
                    batchCodeMapper.updateBatchCodeById(batchCodeMap);
                }
                for (BatchCode batchCode : batchCodes) {
                    duanxin.setMobile(batchCode.getCustomerMobile());
                    duanxin.setCustomer(batchCode.getCustomerName());
                    if (noticeSmsMsg != null) {
                        String name = "用户";
                        if (batchCode.getCustomerName() != null) {
                            name = batchCode.getCustomerName();
                        }
                        String content = noticeSmsMsg.replace("$name", name).replace("$pwd", StringUtils.collectionToDelimitedString(listMap.get(batchCode.getCustomerMobile()), ","));//批量替换
                        duanxin.setContent(content);
                    }
                    final MMSBO sendMsg = duanxin;
                    logger.info("短信内容==电话：" + sendMsg.getMobile() + "名称:" + sendMsg.getCustomer() + "内容：" + sendMsg.getContent());
                    jmsTemplate.send("subject", new MessageCreator() {
                        public Message createMessage(Session session) throws JMSException {
                            ObjectMessage msg = session.createObjectMessage(sendMsg);
                            return msg;
                        }
                    });
                }
            } else {//不合并号码发送
                for (BatchCode batchCode : batchCodeList) {
                    duanxin.setMobile(batchCode.getCustomerMobile());
                    duanxin.setCustomer(batchCode.getCustomerName());
                    if (noticeSmsMsg != null) {
                        String content = noticeSmsMsg.replace("$name", batchCode.getCustomerName()).replace("$pwd", batchCode.getCode());//批量替换
                        duanxin.setContent(content);
                    }
                    final MMSBO sendMsg = duanxin;
                    logger.info("短信内容==电话：" + sendMsg.getMobile() + "名称:" + sendMsg.getCustomer() + "内容：" + sendMsg.getContent());
                    jmsTemplate.send("subject", new MessageCreator() {
                        public Message createMessage(Session session) throws JMSException {
                            ObjectMessage msg = session.createObjectMessage(sendMsg);
                            return msg;
                        }
                    });
                    Map<String, Object> batchCodeMap = new HashMap<>();
                    batchCodeMap.put("id", batchCode.getId());
                    batchCodeMap.put("status", BatchCodeStatus.ALREADY_SEND.getValue());
                    batchCodeMapper.updateBatchCodeById(batchCodeMap);
                }
            }
        }
    }

    @Transactional()
    public void sendCodeByMMS(String actId, String batchId, List<BatchCode> batchCodeList) {

        MMSBO mmsbo = new MMSBO();
        Activity activity = activityService.getActivityById(actId);
        //送码
        Batch batch = batchMapper.getBatchById(batchId);
        if (!BatchStatus.ALREADY_SEND.getValue().equals(batch.getStatus())) {
            carryVerifyCode(activity, batchId, batchCodeList);
        }
        Mms mms = mmsframeService.getMmsByActivityId(Long.parseLong(actId));
        for (BatchCode batchCode : batchCodeList) {
//			mmsbo.setBatchid(batchId);
            mmsbo.setCharcode(batchCode.getCode());
            mmsbo.setNumberpwd(batchCode.getCode());
            mmsbo.setBarcodeno(mms.getCode_no());
            mmsbo.setTid("20150428111444117862");
            mmsbo.setEorderid(5115538L);
            mmsbo.setChannleno("25666");
            mmsbo.setPackageid(mms.getMmspackageid());
            mmsbo.setStatus(Variable.MMSSTATUS_WAITE);
            mmsbo.setType(Variable.MMSBO_TYPE_MMS); // 发送类型为彩信
            //mmsbo.setMttype(Variable.MMSBO_MTTYPE_DEFAULT);// 默认发送级别为 实时
            mmsbo.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            mmsbo.setMobile(batchCode.getCustomerMobile());
            mmsbo.setCustomer(batchCode.getCustomerName());
//			mmsbo.setExtfield1(batchCode.getExtfield1());
//			mmsbo.setExtfield2(batchCode.getExtfield2());
//			mmsbo.setExtfield3(batchCode.getExtfield3());
            mmsbo.setIsresend(mms.isIsresend() ? 1 : 0);
            mmsbo.setMttype(1);//发送类型,直发
            mmsbo.setStatuscode("NYYH");//发送者用户名
            mmsbo.setEntid(46L);
            mmsbo.setEitemid(Long.parseLong(activity.getId()));
            mmsbo.setBarcodeid(73349609L);

            this.jmsTemplate.setDeliveryPersistent(true);
            this.jmsTemplate.convertAndSend(mmsbo);

            //batch_code 状态，状态暂定为01：已提码，02：已导入，03：已送码，未发码，04：已发码
            // batch 00：已删除 01：已提码，未导入联系人  02：已导入联系人，待送码  03：已送码，待发码 04：已发码
            Map<String, Object> batchCodeMap = new HashMap<>();
            batchCodeMap.put("id", batchCode.getId());
            batchCodeMap.put("status", BatchCodeStatus.ALREADY_SEND.getValue());
            batchCodeMapper.updateBatchCodeById(batchCodeMap);
        }
    }

    /**
     * 验码接口-送码
     *
     * @param act,活动对象
     * @param batchId,批次id
     * @param batchCodeList,活动发码批次下所有的码对象列表
     * @return
     */
    private Map<String, Object> carryVerifyCode(Activity act, String batchId, List<BatchCode> batchCodeList) {
        // 定义发码批次对象，设置上一些基本信息
        BatchVO batchVO = new BatchVO();
        batchVO.setActivityId(act.getId());// 活动Id
        batchVO.setActivityName(act.getName());// 活动名称
        batchVO.setOperType("IMPORT");// 操作编码
        batchVO.setTicketTitle(act.getReceiptTitle());// 活动小票标题
        batchVO.setTicketContent(act.getReceiptPrint());// 活动小票内容
        batchVO.setPosSuccessMsg(act.getPosSuccessMsg());// pos验证成功短信
        batchVO.setSuccessSmsMsg(act.getSuccessSmsMsg());// 验证成功提示
        batchVO.setStatus(act.getStatus());// 活动状态
        // 获取并设置关联门店数据
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("activityId", act.getId());
        List<ActivityStore> storeList = activityStoreMapper.queryActivityStoreList(queryMap);
        batchVO.setActActivityStores(storeList);
        // 解析并设置码数据
        List<BatchCodeVO> carryList = new ArrayList<BatchCodeVO>();// 定义送码的列表
        // 遍历活动发码批次下所有的码对象列表
        for (int i = 0; i < batchCodeList.size(); i++) {
            BatchCode oc = batchCodeList.get(i);
            // 组件新的活动批次码对象
            BatchCodeVO codeVO = new BatchCodeVO();
            codeVO.setCodeSerialNumber(oc.getId());//码ID
            codeVO.setCode(String.valueOf(oc.getCode()));// 码库提码的码值
            codeVO.setActivityId(act.getId());// 活动Id
            codeVO.setActivityName(act.getName());// 活动名称
            codeVO.setAmount(String.valueOf(act.getAmount()));// 活动价格
            codeVO.setCreateTime(DateUtil.getLongCurDate());// 创建时间，也就是当前时间
            codeVO.setCustomMobile(oc.getCustomerMobile());// 联系人手机号
            codeVO.setCustomName(oc.getCustomerName());// 联系人名称
            codeVO.setIssueEnterpriseId(act.getEnterpriseId());// 所属活动发行方Id
            codeVO.setIssueEnterpriseName(act.getEnterpriseName());// 所属活动发行方名称
            codeVO.setContractId(act.getContractId());// 合同
            codeVO.setMaxNum(String.valueOf(act.getMaxNumber()));// 最大次数
            codeVO.setOrderId(batchId);// 发码批次Id
            codeVO.setReleaseTime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));// 发布时间，当然时间
            codeVO.setStartTime(DateUtil.dateToStr(oc.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 活动开始时间
            codeVO.setEndTime(DateUtil.dateToStr(oc.getEndTime(), "yyyy-MM-dd HH:mm:ss"));// 活动结束时间
            codeVO.setSelectDate(act.getSelectDate());// 活动指定日期

            codeVO.setOutCode(act.getOutCode());
            codeVO.setOrderCode(batchId);
            codeVO.setActivityCode(act.getCode());
            codeVO.setExchangeChannel(act.getExchangeChannel());//兑换渠道
            Enterprise enterprise = new Enterprise();
            enterprise.setId(act.getEnterpriseId());
            Enterprise enterpriseOri = enterpriseMapper.selectOne(enterprise);
            if (enterpriseOri != null) {
                codeVO.setEnterpriseCode(enterpriseOri.getCode());
            }
            // 将新组建的码对象添加到列表中
            carryList.add(codeVO);
//            //保存订单
//            saveOrder(oc, act, enterpriseOri);
        }
        // 将列表添加到码批次对象中
        batchVO.setActivityCodeList(carryList);
        // carryJson.put("activityCodeList", carryJsonArray);
        // 获取验码系统--送码ＵＲＬ
        String carryCodeUrl = PropertiesUtil.getValue("verifyCode.carryCode");
        // 将要送码的数据JSON化
        String carryMapJson = JSON.toJSONString(batchVO);
        // String carryMapJson = JacksonJsonMapper.objectToJson(carryMap);
        // String carryMapJson= JSON.toJSONString(batchVO);
        // String carryMapJson = carryJson.toJSONString();
        logger.debug("送码，请求参数：" + carryMapJson);
        // 调用Http工具，执行送码操作，并解析返回值
        String carryCodeJson = HttpUtil.doRestfulByHttpConnection(carryCodeUrl, carryMapJson);// 送码
        Map<String, Object> carryCodeMap = JSON.parseObject(carryCodeJson, Map.class);
        logger.debug("送码，返回结果：" + carryCodeJson);
        // 保存验码模块送码的日志
        StringBuffer optInfo = new StringBuffer();
        optInfo.append("请求参数码数量：" + carryList.size() + ";");
        boolean isSuccess = Boolean.parseBoolean(String.valueOf(carryCodeMap.get("success")));
        optInfo.append("请求回应success：" + isSuccess + ";");
        optLogService.addOptLog("活动Id:" + act.getId() + ",批次Id:" + batchId, "",
                "验码接口送码-----" + optInfo.toString());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 将返回对象的success设置为true,并返回数据对象
        resultMap.put("success", isSuccess);
        if (!isSuccess) {
            throw new BusinessException("送码失败");
        }
        return resultMap;
    }

//    /**
//     * 保存订单信息
//     *
//     * @param batchCode
//     * @param activity
//     * @param enterprise
//     * @return
//     */
//    public boolean saveOrder(BatchCode batchCode, Activity activity, Enterprise enterprise) {
//        Merchant merchant = merchantService.selectByEnterpriseId(enterprise.getId());
//        Order order = new Order();
//        order.setActivityId(activity.getId());
//        order.setActivityName(activity.getName());
//        order.setPubEnterpriseId(enterprise.getId());
//        order.setPubEnterpriseName(enterprise.getName());
//        order.setCode(batchCode.getCode());
//        order.setStartTime(DateUtil.dateToStr(activity.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
//        order.setEndTime(DateUtil.dateToStr(activity.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
//        order.setStatus(OrderStatusType.SEND.getValue());
//        order.setTel(batchCode.getCustomerMobile());
//        order.setType(OrderType.CHECK_CODE_ORDER.getValue());
//        order.setId(IDUtil.getID());
//        order.setSupEnterpriseId(merchant.getId());
//        order.setSupEnterpriseName(merchant.getName());
//        return orderService.save(order);
//    }

    public String validatorMobile(String activityId, String name, String type, Map<String, Object> msgMap, Map<String, String> resultMap, List<List<Object>> excelList) {
        String actBatchId = null;
        try {
//            if ("01".equals(type)) {
//                for (int i = 1; i < excelList.size(); i++) {
//                    String customerMobile = excelList.get(i).get(0) + "";
//                    String customerName = excelList.get(i).get(1) + "";
//                    resultMap.put(customerMobile, customerName);
//                    //如果不是正确的手机格式，则返回错误信息
//                    if (!ValidatorUtil.isMobile(customerMobile)) {
//                        msgMap.put("success", false);
//                        msgMap.put("msg", "第" + (i + 1) + "行发生错误，错误原因：不是正确的手机号格式");
//                        throw new BusinessException(msgMap.get("msg") + "");
//                    }
//                }
//                actBatchId = activityService.extractedCode(activityId, name, resultMap.size());
//                for (String key : resultMap.keySet()) {
//                    String customerMobile = key;
//                    String customerName = resultMap.get(key);
//                    Map<String, Object> batchMap = new HashMap<>();
//                    batchMap.put("batchId", actBatchId);
//                    batchMap.put("customerMobile", customerMobile);
//                    batchMap.put("customerName", customerName);
//                    batchCodeMapper.updateBatchCodeByBatchId(batchMap);
//                }
//            }
//            if ("02".equals(type)) {
            actBatchId = activityService.extractedCode(activityId, name, type, excelList.size() - 1);
            for (int i = 1; i < excelList.size(); i++) {
                String customerMobile = excelList.get(i).get(0) + "";
                String customerName = null;
                if (excelList.get(i).size() > 1) {
                    customerName = excelList.get(i).get(1) + "";
                }
                //如果不是正确的手机格式，则返回错误信息
                if (!ValidatorUtil.isMobile(customerMobile)) {
                    msgMap.put("success", false);
                    msgMap.put("msg", "第" + (i + 1) + "行发生错误，错误原因：不是正确的手机号格式");
                    throw new BusinessException(msgMap.get("msg") + "");
                }
                Map<String, Object> batchMap = new HashMap<>();
                batchMap.put("batchId", actBatchId);
                batchMap.put("customerMobile", customerMobile);
                batchMap.put("customerName", customerName);
                batchCodeMapper.updateBatchCodeByBatchId(batchMap);
            }
//            }
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("请求码库接口失败");
        }
        return actBatchId;
    }
}
