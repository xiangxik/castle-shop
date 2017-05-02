package com.whenling.shop.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.integration.webapp.json.PathFilter;
import com.whenling.castle.integration.webapp.mvc.RangeParam;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.security.CurrentUser;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.entity.DeliveryCenter;
import com.whenling.shop.entity.DeliveryTemplate;
import com.whenling.shop.entity.Order;
import com.whenling.shop.entity.QOrder;
import com.whenling.shop.repo.DeliveryCenterRepository;
import com.whenling.shop.repo.DeliveryTemplateRepository;
import com.whenling.shop.support.mvc.CrudController;
import com.whenling.shop.support.setting.SettingUtils;
import com.whenling.shop.support.sms.SmsUtils;

@Controller
@RequestMapping("/order")
public class OrderController extends CrudController<Order, Long> {

	@Autowired
	private DeliveryTemplateRepository deliveryTemplateRepository;

	@Autowired
	private DeliveryCenterRepository deliveryCenterRepository;

	private SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

	@Override
	protected void onShowIndexPage(Model model) {
		super.onShowIndexPage(model);

		model.addAttribute("currentUser", getCurrentUser());
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	@PathFilter("*,*.*,*.*.id,*.*.name,*.*.sn")
	public Page<Order> doPage(@CurrentUser Admin currentUser, Predicate predicate,
			@PageableDefault(sort = "orderedDate", direction = Direction.DESC) Pageable pageable,
			@RangeParam(value = "dateRange", elementClass = Date.class) Range<Date> range) {
		BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
		if (range != null) {
			booleanBuilder.and(QOrder.order.orderedDate.between(range.getLowerBound(), range.getUpperBound()));
		}
		if (currentUser.isSalesman()) {
			booleanBuilder.and(QOrder.order.operator.eq(currentUser).and(QOrder.order.orderStatus.ne("pending")));
		} else if (currentUser.isShipper()) {
			booleanBuilder.and(QOrder.order.orderStatus.in("confirmed", "completed"));
		} else if (currentUser.isTempSalesman()) {
			booleanBuilder.and(QOrder.order.orderStatus.eq("followup"));
		}
		return getBaseJpaRepository().findAll(booleanBuilder, pageable);
	}

	@RequestMapping(value = "/orderStatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result changeOrderStatus(@PathVariable("id") Order order, String orderStatus, Model model) {
		order.setOrderStatus(orderStatus);
		getBaseJpaRepository().save(order);
		return Result.success();
	}

	@RequestMapping(value = "/shippingStatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result changeShippingStatus(@PathVariable("id") Order order, String shippingStatus, Model model) {
		order.setShippingStatus(shippingStatus);
		order.setShipper(getCurrentUser());
		getBaseJpaRepository().save(order);
		if (Objects.equal(order.getShippingStatus(), "shipped")) {
			String template = SettingUtils.get().getShippingTemplate();
			if (!Strings.isNullOrEmpty(template) && !Strings.isNullOrEmpty(order.getPhone())) {
				SmsUtils.send(order.getPhone(), StringUtils.replace(template, "[sn]", order.getSn()));
			}

		}
		return Result.success();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result getOrder(@CurrentUser Admin saler) {
		if (!saler.isSalesman()) {
			return Result.failure().message("当前用户不是销售人员");
		}

		Integer maxPendingCount = SettingUtils.get().getSaleOrderCount();
		if (maxPendingCount == null) {
			maxPendingCount = 3;
		}
		Long pendingOrderCount = getBaseJpaRepository()
				.count(QOrder.order.operator.eq(saler).and(QOrder.order.orderStatus.eq("inhand")));
		Integer getOrderCount = maxPendingCount - pendingOrderCount.intValue();
		if (getOrderCount <= 0) {
			return Result.failure().message(String.format("你已有%d张未处理订单，最多只能有%d张。", pendingOrderCount, maxPendingCount));
		}
		Iterable<Order> pendingOrders = getBaseJpaRepository().findAll(QOrder.order.orderStatus.eq("pending"),
				new PageRequest(0, getOrderCount, new Sort("orderedDate")));
		if (pendingOrders != null && Iterables.size(pendingOrders) > 0) {
			for (Order pendingOrder : pendingOrders) {
				pendingOrder.setOperator(saler);
				pendingOrder.setOrderStatus("inhand");
				getBaseJpaRepository().save(pendingOrder);
			}
		} else {
			return Result.failure().message("无未处理订单");
		}
		return Result.success().addProperties("orderCount", Iterables.size(pendingOrders));
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Order entity, Model model) {
		model.addAttribute("entity", entity);
		onShowEditPage(entity, model);
		return getBaseTemplatePath() + "/view";
	}

	@RequestMapping(value = "/print/{id}", method = RequestMethod.GET)
	public String print(@PathVariable("id") Order entity,
			@RequestParam(value = "template", required = false) DeliveryTemplate deliveryTemplate,
			@RequestParam(value = "center", required = false) DeliveryCenter deliveryCenter, Model model) {
		model.addAttribute("entity", entity);

		if (deliveryTemplate == null) {
			deliveryTemplate = deliveryTemplateRepository.findDefault();
		}
		if (deliveryCenter == null) {
			deliveryCenter = deliveryCenterRepository.findDefault();
		}
		model.addAttribute("currentTemplate", deliveryTemplate);
		model.addAttribute("currentCenter", deliveryCenter);

		model.addAttribute("templates", deliveryTemplateRepository.findAll());
		model.addAttribute("centers", deliveryCenterRepository.findAll());

		if (deliveryTemplate != null && !Strings.isNullOrEmpty(deliveryTemplate.getContent())
				&& deliveryCenter != null) {
			Context context = new Context();
			context.setVariable("order", entity);
			context.setVariable("product", entity.getProduct());
			context.setVariable("deliveryCenter", deliveryCenter);
			context.setVariable("currentDate", new Date());
			model.addAttribute("content", springTemplateEngine.process(deliveryTemplate.getContent(), context));
		} else {
			model.addAttribute("content", "");
		}

		return getBaseTemplatePath() + "/print";
	}

	@Override
	protected void onShowEditPage(Order entity, Model model) {
		super.onShowEditPage(entity, model);
		Admin currentUser = getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("allowModifyOrderStatus",
				currentUser.isAdmin()
						|| (currentUser.isSalesman() && !Objects.equal(entity.getOrderStatus(), "completed")
								&& !Objects.equal(entity.getOrderStatus(), "canceled")));
		model.addAttribute("allowModifyShippingStatus", currentUser.isAdmin()
				|| (currentUser.isShipper() && !Objects.equal(entity.getShippingStatus(), "completed")));
	}

}
