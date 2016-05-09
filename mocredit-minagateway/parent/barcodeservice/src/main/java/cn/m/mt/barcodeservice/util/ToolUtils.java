package cn.m.mt.barcodeservice.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ToolUtils {
    public static String getPosno(){
    	Random rd = new Random();
    	DecimalFormat df1 = new DecimalFormat("000");
		String currDate1 = new SimpleDateFormat("hhmmssSSS").format(new Date());// 生成日期格式为：年年月月日日、如：110608(11年06月08日)
		String increase1 = df1.format(rd.nextInt(999));// 4位自增长数
		return  increase1 + currDate1;
    }
}
