package com.whenling.shop.support.setting;

import com.whenling.castle.web.WebSpringContext;

public class SettingUtils {

	private static SettingUtils INSTANCE = new SettingUtils();

	private SettingService settingService;

	private SettingUtils() {
	}

	public SettingService getSettingService() {
		if (settingService == null) {
			settingService = WebSpringContext.getBean(SettingService.class);
		}
		return settingService;
	}

	public static Setting get() {
		return INSTANCE.getSettingService().get();
	}

	public static void set(Setting setting) {
		INSTANCE.getSettingService().set(setting);
	}

}
