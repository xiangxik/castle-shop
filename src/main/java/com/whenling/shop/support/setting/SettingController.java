package com.whenling.shop.support.setting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.shop.support.mvc.BaseController;
import com.whenling.shop.support.setting.Setting.WatermarkPosition;

@Controller
@RequestMapping("/setting")
public class SettingController extends BaseController {

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Model model) {
		model.addAttribute("watermarkPositions", WatermarkPosition.values());
		return "/setting";
	}

}
