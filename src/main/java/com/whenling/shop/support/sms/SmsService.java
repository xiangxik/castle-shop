package com.whenling.shop.support.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.whenling.shop.support.HttpUtils;

@Component
public class SmsService {

	@Value("${sms.userid?:}")
	private String userid = "";
	
	@Value("${sms.account?:}")
	private String account = "";
	
	@Value("${sms.password?:}")
	private String password = "";
	
	@Value("${sms.url?:http://www.lcqxt.com/sms.aspx}")
	private String url = "http://www.lcqxt.com/sms.aspx";
	
	public void send(String mobile, String content) {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("userid", userid);
		parameterMap.put("account", account);
		parameterMap.put("password", password);
		parameterMap.put("mobile", mobile);
		parameterMap.put("content", content);
		parameterMap.put("sendTime", "");
		parameterMap.put("action", "send");
		parameterMap.put("checkcontent", "1");
		String response = HttpUtils.get(url, parameterMap);
		System.out.println(response);
	}
	
	public static void main(String[] args) {
		new SmsService().send("13265323553", "尊敬的贵宾:你获得1366元新年抢购6s手机名额（限今日）点击http://t.bjrio.com/Drk抢购，回T退订【会员福利】");
	}
	
}
