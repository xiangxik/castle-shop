package com.whenling.shop.support.sms;

import java.util.HashMap;
import java.util.Map;

import com.whenling.shop.support.HttpUtils;
import com.whenling.shop.support.setting.Setting;
import com.whenling.shop.support.setting.SettingUtils;

public class SmsUtils {

	public static void send(String mobile, String content) {
		Setting setting = SettingUtils.get();
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("userid", setting.getSmsUserId());
		parameterMap.put("account", setting.getSmsAccount());
		parameterMap.put("password", setting.getSmsPassword());
		parameterMap.put("mobile", mobile);
		parameterMap.put("content", content);
		parameterMap.put("sendTime", "");
		parameterMap.put("action", "send");
		parameterMap.put("checkcontent", "1");
		String response = HttpUtils.get(setting.getSmsUrl(), parameterMap);
		System.out.println(response);
	}

	public static void main(String[] args) {
		send("13265323553", "尊敬的贵宾:你获得1366元新年抢购6s手机名额（限今日）点击http://t.bjrio.com/Drk抢购，回T退订【会员福利】");
	}

}
