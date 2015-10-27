package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.TVerifiedCode;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 提供已验证码的表的操作
 * Created by YHL on 15/7/9 09:47.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface VerifiedCodeMapper {

    /**
     * 根据 券码号，获取该券码的验证记录列表
     * @param code
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByCode(String code);

    /**
     * 根据券码和 POS的序列号，找到对应的验证记录列表
     *
     * @param code
     * @param requestSeriaNumber
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByCodeAndRequestSerialNumber(@Param("code") String code, @Param("request_serial_number") String requestSeriaNumber);

    /**
     * 根据码序列号（券码ID）获取券码的验证记录列表
     * @param codeSerialNumber
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByActiveCodeId(String codeSerialNumber);


    /**
     * 插入一条验码使用的记录
     * @param verifiedCode
     * @return
     */
    public int insertVerifiedCode(TVerifiedCode verifiedCode);


    /**
     * 根据
     * @param device  机具号
     * @param requestSeriaNumber   流水号
     * @param date 某个时间。这里 指某天
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByDeviceAndRequestSerialNumber(@Param("device") String device, @Param("request_serial_number") String requestSeriaNumber , @Param("date")Date date);



}