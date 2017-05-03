package com.whenling.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.core.helper.Patterns;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.entity.QAdmin;
import com.whenling.shop.support.mvc.CrudController;

@Controller
@RequestMapping("/admin")
public class AdminController extends CrudController<Admin, Long> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Admin> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onValidate(Admin entity, BindingResult bindingResult) {
		super.onValidate(entity, bindingResult);
		String newPassword = getRequest().getParameter("newPassword");
		if (Strings.isNullOrEmpty(newPassword)) {
			if (entity.isNew()) {
				bindingResult.addError(new FieldError("entity", "password", "密码不能为空"));
			}
		} else {
			if (!Patterns.PATTENR_PASSWORD.matcher(newPassword).matches()) {
				bindingResult.addError(new FieldError("entity", "password", "密码必须是6-16位数字、字符组合（不能是纯数字）"));
			}
		}
		
		if (!Strings.isNullOrEmpty(entity.getUsername())) {
			Admin otherAdmin = getBaseJpaRepository().findOne(QAdmin.admin.username.eq(entity.getUsername()));
			if (otherAdmin != null && !Objects.equal(otherAdmin, entity)) {
				bindingResult.addError(new FieldError("entity", "username", "该账号已占用"));
			}
		}

		if (!Strings.isNullOrEmpty(entity.getEmail())) {
			Admin otherAdmin = getBaseJpaRepository().findOne(QAdmin.admin.email.eq(entity.getEmail()));
			if (otherAdmin != null && !Objects.equal(otherAdmin, entity)) {
				bindingResult.addError(new FieldError("entity", "email", "该邮箱已占用"));
			}
		}

		if (!Strings.isNullOrEmpty(entity.getMobile())) {
			Admin otherAdmin = getBaseJpaRepository().findOne(QAdmin.admin.mobile.eq(entity.getMobile()));
			if (otherAdmin != null && !Objects.equal(otherAdmin, entity)) {
				bindingResult.addError(new FieldError("entity", "mobile", "该手机号码已占用"));
			}
		}
	}

	@Override
	protected void onBeforeSave(Admin entity) {
		super.onBeforeSave(entity);
		String newPassword = getRequest().getParameter("newPassword");
		if (!Strings.isNullOrEmpty(newPassword)) {
			entity.setPassword(passwordEncoder.encode(newPassword));
		}
	}
}
