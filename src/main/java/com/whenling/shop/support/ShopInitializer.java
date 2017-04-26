package com.whenling.shop.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.whenling.castle.integration.ApplicationInitializer;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.repo.AdminRepository;
import com.whenling.shop.support.setting.SettingUtils;

@Component
public class ShopInitializer extends ApplicationInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;

	@Override
	public void onRootContextRefreshed() {
		if (adminRepository.count() == 0) {
			Admin admin = new Admin();
			admin.setName("管理员");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("asd123"));
			adminRepository.save(admin);
		}
	}

	@Override
	public void onServletContextRefreshed() {
		thymeleafViewResolver.addStaticVariable("setting", SettingUtils.get());
	}
}