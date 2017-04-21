package com.whenling.shop.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.querydsl.core.types.Predicate;
import com.whenling.shop.entity.DeliveryTemplate;
import com.whenling.shop.entity.QDeliveryTemplate;
import com.whenling.shop.support.mvc.CrudController;

@Controller
@RequestMapping("/deliveryTemplate")
public class DeliveryTemplateController extends CrudController<DeliveryTemplate, Long> {

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<DeliveryTemplate> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onBeforeSave(DeliveryTemplate entity) {
		super.onBeforeSave(entity);

		if (entity.getIsDefault()) {
			Iterable<DeliveryTemplate> defaultDeliveryTemplates = getBaseJpaRepository().findAll(QDeliveryTemplate.deliveryTemplate.isDefault.isTrue());
			if (defaultDeliveryTemplates != null) {
				for (DeliveryTemplate defaultDeliveryTemplate : defaultDeliveryTemplates) {
					if (!Objects.equal(defaultDeliveryTemplate, entity)) {
						defaultDeliveryTemplate.setIsDefault(false);
						getBaseJpaRepository().save(defaultDeliveryTemplate);
					}
				}
			}
		}
	}

}
