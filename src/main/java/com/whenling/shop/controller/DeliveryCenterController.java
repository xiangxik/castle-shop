package com.whenling.shop.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.querydsl.core.types.Predicate;
import com.whenling.shop.entity.DeliveryCenter;
import com.whenling.shop.entity.QDeliveryCenter;
import com.whenling.shop.support.mvc.CrudController;

@Controller
@RequestMapping("/deliveryCenter")
public class DeliveryCenterController extends CrudController<DeliveryCenter, Long> {

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<DeliveryCenter> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onBeforeSave(DeliveryCenter entity) {
		super.onBeforeSave(entity);

		if (entity.getIsDefault()) {
			Iterable<DeliveryCenter> defaultDeliveryCenters = getBaseJpaRepository().findAll(QDeliveryCenter.deliveryCenter.isDefault.isTrue());
			if (defaultDeliveryCenters != null) {
				for (DeliveryCenter defaultDeliveryCenter : defaultDeliveryCenters) {
					if (!Objects.equal(defaultDeliveryCenter, entity)) {
						defaultDeliveryCenter.setIsDefault(false);
						getBaseJpaRepository().save(defaultDeliveryCenter);
					}
				}
			}
		}
	}

}
