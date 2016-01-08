package com.yimeihuijin.commonlibrary.utils;

public class BankInfoUtil {

	private final static long[] bankBin = { 451291, 456351, 622752, 622753,
			622754, 622755, 622756, 622757, 622758, 622759, 622760, 622761,
			622762, 622763, 409666, 438088, 622750, 622751, 622770, 427020,
			427030, 530990, 622230, 622235, 622210, 622215, 622200, 451804,
			451804, 451810, 451810, 458060, 458060, 458071, 458071, 489734,
			489735, 489736, 955880, 955881, 955882, 955888, 491020, 622821,
			622822, 622823, 622824, 622825, 622826, 622836, 622837, 622840,
			622844, 622845, 622846, 622847, 622848, 552599, 404119, 404121,
			519412, 403361, 558730, 520083, 520082, 519413, 404120, 404118,
			404117, 453242, 489592, 622700, 622725, 622728, 436742, 436745,
			622280, 622678, 622679, 622680, 622688, 622689, 622690, 622691,
			622692, 622696, 622698, 376968, 376966, 622918, 622916, 518212,
			520108, 376969, 622919, 556617, 403391, 558916, 514906, 400360,
			433669, 433667, 433666, 404173, 404172, 404159, 404158, 403393,
			403392, 433668, 404157, 404171, 404174, 628209, 628208, 628206,
			458123, 458124, 521899, 622260, 456418, 843010, 843360, 843420,
			843610, 843730, 843800, 843850, 843900, 870000, 870100, 870300,
			870400, 870500, 984301, 418152, 622521, 404738, 404739, 498451,
			622517, 622518, 515672, 517650, 525998, 356850, 356851, 356852,
			468203, 479228, 479229, 690755, 690755, 518710, 518718, 622588,
			622575, 545947, 521302, 439229, 552534, 622577, 622579, 439227,
			356890, 356889, 356885, 439188, 545948, 545623, 552580, 552581,
			552582, 552583, 552584, 552585, 552586, 552588, 552589, 645621,
			545619, 356886, 622578, 622576, 622581, 439228, 439225, 439226,
			628262, 628362, 628362, 628262, 472067, 472068, 407405, 517636,
			512466, 415599, 421870, 622622, 528948, 552288, 556610, 622600,
			622601, 622602, 622603, 421869, 421871, 628258, 650600, 650700,
			650800, 650900, 451289, 486493, 486494, 622901, 622908, 622909,
			622902, 527414, 524070, 451290, 523036, 486861, 622922, 486497,
			481699, 622650, 622655, 622658, 622660, 406254, 356839, 543159,
			425862, 406252, 356837, 356838, 356840, 622161, 628201, 628202,
			486466, 622892, 682900, 402674, 487013, 685800, 685800, 685800,
			622568, 520152, 520382, 911121, 548844, 512431, 520194, 622318,
			622778, 622282, 622898, 622900, 694301, 435744, 622526, 435745,
			998801, 998802, 622525, 622538, 622155, 622156, 528020, 526855,
			622632, 622633, 539867, 528709, 523959, 622637, 622636, 528708,
			539868, 622877, 622879, 622681, 622682, 622684, 622884, 622880,
			622881, 622777, 622188, 602969, 622855, 622856, 622858, 622859,
			622860, 622861, 622864, 622865, 622866, 622867, 622885, 622869,
			622870, 622871, 622878, 622882, 622886, 622891, 622893, 622895,
			622897, 683970, 695800, 888000 };

	// BIN号
	// "发卡行.卡种名称",
	private static final String[] bankName = { "中国银行", " 中国银行", " 中国银行",
			" 中国银行", " 中国银行", " 中国银行", " 中国银行", " 中国银行", " 中国银行", " 中国银行",
			" 中国银行", " 中国银行", " 中国银行", " 中国银行", " 中国银行", " 中国银行", " 中国银行澳门分行",
			" 中国银行澳门分行", " 中国银行澳门分行", " 中国工商银行", " 中国工商银行", " 中国工商银行",
			" 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行",
			" 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行",
			" 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行", " 中国工商银行",
			" 中国工商银行", " 中国工商银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行",
			" 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行",
			" 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行",
			" 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行",
			" 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国农业银行", " 中国建设银行",
			" 中国建设银行", " 中国建设银行", " 中国建设银行", " 中国建设银行", " 中国建设银行", " 中国建设银行",
			" 中国建设银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行",
			" 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行",
			" 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行",
			" 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行",
			" 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行",
			" 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行",
			" 中信实业银行", " 中信实业银行", " 中信实业银行", " 中信实业银行", " 交通银行", " 交通银行",
			" 交通银行", " 交通银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行",
			" 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行",
			" 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行",
			" 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行",
			" 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行", " 浦东发展银行",
			" 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行",
			" 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行",
			" 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行",
			" 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行",
			" 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行",
			" 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行", " 招商银行",
			" 招商银行", " 招商银行", " 民生银行", " 民生银行", " 民生银行", " 民生银行", " 民生银行",
			" 民生银行", " 民生银行", " 民生银行", " 民生银行", " 民生银行", " 民生银行", " 民生银行",
			" 民生银行", " 民生银行", " 民生银行", " 民生银行", " 民生银行", "民生银行",
			"Discover Financial Services，Inc",
			"Discover Financial Services，Inc",
			"Discover Financial Services，Inc",
			" Discover Financial Services，Inc", " 兴业银行", " 兴业银行", " 兴业银行",
			" 兴业银行", " 兴业银行", " 兴业银行", " 兴业银行", " 兴业银行", " 兴业银行", " 兴业银行",
			" 兴业银行", " 兴业银行", " 兴业银行", " 中国光大银行", " 中国光大银行", " 中国光大银行",
			" 中国光大银行", " 中国光大银行", " 中国光大银行", " 中国光大银行", " 中国光大银行", " 中国光大银行",
			" 中国光大银行", " 中国光大银行", " 中国光大银行", " 中国光大银行", " 中国光大银行", " 中国光大银行",
			" 中国光大银行", " 中国光大银行", " 上海银行", " 上海银行", " 上海银行", " 上海银行",
			" 广东发展银行", " 广东发展银行", " 广东发展银行", " 广东发展银行", " 广东发展银行", " 广东发展银行",
			" 广东发展银行", " 广东发展银行", " 广东发展银行", " 宁波银行", " 宁波银行", " 宁波银行",
			" 宁波银行", " 宁波银行", " 长沙市商业银行", " 长沙市商业银行", " 长沙市商业银行", " 深圳发展银行",
			" 深圳发展银行", " 深圳发展银行", " 深圳发展银行", " 深圳发展银行", " 深圳发展银行", " 深圳发展银行",
			" 深圳平安银行", " 深圳平安银行", " 深圳平安银行", " 深圳平安银行", " 华夏银行", " 华夏银行",
			" 华夏银行", " 华夏银行", " 华夏银行", " 华夏银行", " 华夏银行", " 华夏银行", " 华夏银行",
			" 徽商银行", " 徽商银行", " 江西省农村信用社联合社", " 江西省农村信用社联合社", " 渤海银行", " 渤海银行",
			" 柳州市商业银行", " 柳州市商业银行", " 曲靖市商业银行", " 中国邮政储蓄", " 北京银行",
			" 江苏东吴农村商业银行", " 桂林市商业银", " 浙江省农村信用社联合社", " 珠海农村信用合作社联社",
			" 大庆市商业银行", " 澳门永亨银行股份有限公司", " 莱芜市商业银行", " 长春市商业银行", " 徐州市商业银行",
			" 重庆市农村信用社联合社", " 重庆市农村信用社联合社", " 太仓农村商业银行", " 靖江市长江城市信用社",
			" 永亨银行", " 杭州市商业银行", " 尧都区农村信用合作社联社", " 烟台市商业银行", " 武进农村商业银行",
			" 贵州省农村信用社联合社", " 江苏锡州农村商业银行", " 南充市商业银行", " 泉州市商业银行", " 南通商业银行",
			" 贵阳市商业银行", };

	public static String getNameOfBank(char[] charBin, int offset) {
		long longBin = 0;

		for (int i = 0; i < 6; i++) {
			longBin = (longBin * 10) + (charBin[i + offset] - 48);
		}

		int index = 0;
		while(index < bankBin.length){
			if(bankBin[index] == longBin){
				break;
			}
			index ++;
		}

		if (index >= bankBin.length) {
			return "银行卡号";
		}
		return bankName[index];

	}

	// 二分查找方法
	public static int binarySearch(long[] srcArray, long des) {
		int low = 0;
		int high = srcArray.length - 1;
		while (low <= high) {
			int middle = (low + high) / 2;
			if (des == srcArray[middle]) {
				return middle;
			} else if (des < srcArray[middle]) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		return -1;
	}
}