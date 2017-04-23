package com.whenling.shop.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.shop.entity.DeliveryTemplate;
import com.whenling.shop.support.mvc.CrudController;

@Controller
@RequestMapping("/deliveryTemplate")
public class DeliveryTemplateController extends CrudController<DeliveryTemplate, Long> {

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<DeliveryTemplate> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

}
