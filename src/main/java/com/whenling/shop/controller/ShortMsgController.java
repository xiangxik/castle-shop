package com.whenling.shop.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.security.CurrentUser;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.entity.ShortMsg;
import com.whenling.shop.repo.ShortMsgRepository;
import com.whenling.shop.support.mvc.CrudController;
import com.whenling.shop.support.sms.SmsUtils;

@Controller
@RequestMapping("/sms")
public class ShortMsgController extends CrudController<ShortMsg, Long> {

	@Autowired
	private ShortMsgRepository shortMsgRepository;

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<ShortMsg> doPage(Predicate predicate, @PageableDefault(sort = "sendDate", direction = Direction.DESC) Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String showSendPage(Model model) {
		return getBaseTemplatePath() + "/send";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public Result doSend(String mobiles, String content, @CurrentUser Admin currentUser, Model model) {
		if (!Strings.isNullOrEmpty(mobiles)) {
			Set<String> mobileSet = toMobiles(mobiles);
			for (String mobile : mobileSet) {
				send(mobile, content, currentUser);
			}
		}
		return Result.success();
	}

	private Set<String> toMobiles(String mobiles) {
		mobiles = StringUtils.replace(mobiles, " ", "");
		mobiles = StringUtils.replace(mobiles, "，", ",");

		Set<String> result = new HashSet<>();
		for (String mobile : StringUtils.split(mobiles, ',')) {
			if (StringUtils.contains(mobile, '-')) {
				String[] range = StringUtils.split(mobile, '-');
				Long rangMin = Long.valueOf(range[0]);
				Long rangMax = Long.valueOf(range[1]);
				for (long i = rangMin; i <= rangMax; i++) {
					result.add(String.valueOf(i));
				}
			} else {
				result.add(mobile);
			}
		}
		return result;
	}

	private void send(String mobile, String content, Admin operator) {
		ShortMsg msg = new ShortMsg();
		msg.setMobile(mobile);
		msg.setContent(content);
		msg.setSendDate(new Date());
		msg.setOperator(operator);
		shortMsgRepository.save(msg);
		SmsUtils.send(mobile, content);
	}

}
