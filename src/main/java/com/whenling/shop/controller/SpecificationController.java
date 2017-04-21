package com.whenling.shop.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.shop.entity.Specification;
import com.whenling.shop.entity.SpecificationValue;
import com.whenling.shop.support.mvc.CrudController;

@Controller
@RequestMapping("/specification")
public class SpecificationController extends CrudController<Specification, Long> {

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Specification> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onBeforeSave(Specification entity) {
		super.onBeforeSave(entity);

		Integer specificationValueCount = getParameter("specificationValueCount", Integer.class, 0);
		List<SpecificationValue> submitedValues = entity.getSpecificationValues().subList(0, specificationValueCount);
		submitedValues.forEach(specificationValue -> specificationValue.setSpecification(entity));
		entity.setSpecificationValues(submitedValues);
	}

}
