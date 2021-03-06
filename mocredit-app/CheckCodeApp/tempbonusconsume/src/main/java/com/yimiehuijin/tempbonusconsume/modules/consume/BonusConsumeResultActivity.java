package com.yimiehuijin.tempbonusconsume.modules.consume;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.data.ActivitiesResponse;
import com.yimiehuijin.codeandbonuslibrary.data.ActivityInfo;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumeResponse;
import com.yimiehuijin.codeandbonuslibrary.utils.BankInfoUtil;
import com.yimiehuijin.codeandbonuslibrary.utils.PrinterUtils;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;
import com.yimiehuijin.tempbonusconsume.R;
import com.yimiehuijin.tempbonusconsume.base.IBaseActivity;


public class BonusConsumeResultActivity extends IBaseActivity {

	private Bundle data;

	private TextView content, error,remind;

	private boolean result;

	private BonusConsumeResponse ret;

	private int from = 0;

	public static final int FROM_CONSUME = 0;

	public static final int FROM_REVOKE = 1;

	private final String  signInfo = "\n持卡人签名：\n\n" + "本人已确认以上交易，统一将其计入本账户\n"
			+ PrinterUtils.printDivider + "\n";

	private int printNum = 0;

	private String orderId,cardNo,newOrderId;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		result = getIntent().getExtras().getBoolean("result");
		if (result) {
			return R.layout.activity_code_scan_result;
		} else {
			return R.layout.activity_code_scan_result_fail;
		}

	}

	@Override
	public void initAactivity() {
		findView();
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		data = getIntent().getExtras();
		orderId = data.getString("oldid");
		cardNo = data.getString("cardNo");
		newOrderId = data.getString("orderid");
		if(orderId == null){
			orderId = "";
		}
		if(cardNo == null){
			cardNo = "";
		}
		if(newOrderId == null){
			newOrderId = "";
		}
		ret = (BonusConsumeResponse) data
				.getSerializable("data");
		if (ret == null && result) {
		} else if (ret != null && result) {
			content.setText(ret.errorMes);
			printInfo();
		} else if (ret != null && !result) {
			if (ret.errorMes != null || "".equals(ret.errorMes)) {
				error.setText(ret.errorMes);
			}
		}
	}

	protected void findView() {
		from = getIntent().getIntExtra("from",0);
		// TODO Auto-generated method stub
		if (result) {
			remind = (TextView) f(R.id.checkcode_remind);
			content = (TextView) f(R.id.checkcode_result);
			if(from == FROM_CONSUME){
				remind.setText("交易成功");
			}else{
				remind.setText("撤销成功");
			}
		} else {
			error = (TextView) f(R.id.checkcode_result_error);
			if(from == FROM_REVOKE){
				error.setText("撤销失败");
			}
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.check_code_result_back:
			finish();
			break;
		case R.id.check_code_result_print:
			printInfo();
			break;
		case R.id.check_code_result_fail_back:
			finish();
			break;
		}
	}

	private void printInfo(){
		String printInfo = null;
		if(from == FROM_CONSUME){
			if(ret != null && ret.printInfo != null){
				printInfo = ret.printInfo;
			}else{
				Toast.makeText(this, "没有可用的打印信息", Toast.LENGTH_LONG).show();
				return;
			}
		}else{
			printInfo = "\n商户编号："+App.getInstance().deviceInfo.mcode+
					"\n终端编号："+App.getInstance().deviceInfo.en+
					"\n订单编号："+ newOrderId+
					"\n被撤销的订单编号："+ orderId+
					"\n所属银行:"+ BankInfoUtil.getNameOfBank(cardNo.substring(0, 6).toCharArray(), 0)+
					"\n卡号："+cardNo+
					"\n打印日期："+StringUtils.getCurrentDate()+
					"\n打印时间"+StringUtils.getCurrentTime()+"\n";
		}
		if(ret != null && ret.printInfo != null) {
			if (printInfo != null) {
				if (printNum == 0) {
					PrinterUtils.printNormal(printInfo);
					printNum++;
				} else if (printNum == 1) {
					PrinterUtils.printNormal(printInfo);
				}
			}
			if(ret.qr != null){
//				PrinterUtils.printNormal(PrinterUtils.printDivider);
				PrinterUtils.printQR(ret.qr);
			}
		}else{
			Toast.makeText(this, "没有可用的打印信息", Toast.LENGTH_LONG).show();
		}
	}

	private String getActivityPrintInfo(Bundle data){
		BonusConsumeResponse ret = (BonusConsumeResponse) data
				.getSerializable("data");
		if (ret == null) {
			return null;
		}
		String data_print = ret.data;
		if (data_print == null) {
			return null;
		}
		ActivityId activity_id = new Gson().fromJson(data_print,
				ActivityId.class);
		if (activity_id == null || activity_id.activityId == null
				|| activity_id.orderId == null) {
			return null;
		}
		ActivitiesResponse ar = App.getInstance().getDBHelper().getActivitiesJson(
				ActivitiesResponse.class);
		ActivityInfo act_info = null;
		for (ActivityInfo ai : ar.data) {
			if (activity_id.activityId.equals(ai.activityId)) {
				act_info = ai;
				break;
			}
		}
		if (act_info == null) {
			return null;
		}

		String commonInfo = StringUtils
				.getPrintInfo(act_info, activity_id.orderId);
		String signInfo = "\n\n持卡人签名：\n\n" + "本人已确认以上交易，统一将其计入本账户\n"
				+ PrinterUtils.printDivider + "\n";
		return commonInfo;
	}

	private static class ActivityId {
		public String activityId;
		public String orderId;
	}

}
