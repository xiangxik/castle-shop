package com.whenling.shop.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.whenling.castle.integration.ApplicationInitializer;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.repo.AdminRepository;

@Component
public class FeiInitializer extends ApplicationInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminRepository adminRepository;

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
}