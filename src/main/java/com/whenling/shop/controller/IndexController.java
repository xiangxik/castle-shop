package com.whenling.shop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.expression.Numbers;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.security.CurrentUser;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.entity.Order;
import com.whenling.shop.entity.Product;
import com.whenling.shop.entity.ProductSku;
import com.whenling.shop.entity.QAdmin;
import com.whenling.shop.entity.QOrder;
import com.whenling.shop.entity.QProduct;
import com.whenling.shop.entity.SpecificationValue;
import com.whenling.shop.repo.AdminRepository;
import com.whenling.shop.repo.OrderRepository;
import com.whenling.shop.repo.ProductRepository;
import com.whenling.shop.repo.ProductSkuRepository;
import com.whenling.shop.security.AdminDetailsService.CurrentUserDetails;

@Controller
@RequestMapping
public class IndexController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductSkuRepository productSkuRepository;

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String indexPage(Model model) {
		Product product = productRepository.findOne(QProduct.product.defaultShow.isTrue());
		if (product == null) {
			Iterable<Product> products = productRepository.findAll(QProduct.product.isMarketable.isTrue());
			if (products != null && Iterables.size(products) > 0) {
				product = products.iterator().next();
			}
		}
		return p(product, model);
	}

	@RequestMapping(value = "/p/{product}", method = RequestMethod.GET)
	public String p(@PathVariable("product") Product product, Model model) {
		if(product == null) {
			return "/no_product";
		}
		
		model.addAttribute("product", product);

		model.addAttribute("specifications", product.getSpecifications());

		model.addAttribute("skus", product.getProductSkus());

		List<Map<String, Object>> skuList = new ArrayList<>();
		Set<SpecificationValue> allSpecificationValues = new HashSet<>();
		Numbers numbers = new Numbers(Locale.getDefault());
		product.getProductSkus().forEach(sku -> {
			Map<String, Object> skuMap = new HashMap<>();
			skuMap.put("price", numbers.formatDecimal(sku.getPrice(), 0, 2));
			skuMap.put("marketPrice", numbers.formatDecimal(sku.getMarketPrice(), 0, 2));
			skuMap.put("id", sku.getId());
			skuMap.put("specificationValues",
					Lists.newArrayList(Iterables.transform(sku.getSpecificationValues(), specificationValues -> String.valueOf(specificationValues.getId()))));
			skuList.add(skuMap);
			allSpecificationValues.addAll(sku.getSpecificationValues());
		});
		model.addAttribute("skuList", skuList);
		model.addAttribute("allSpecificationValues", allSpecificationValues);

		if (product.getProductSkus() != null) {

		}
		return "/index";
	}

	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrder(String name, String mobile, String address, String memo, Long productId, Long productSkuId) {
		if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(mobile) || Strings.isNullOrEmpty(address)) {
			return Result.failure();
		}

		Product product = productRepository.findOne(productId);
		if (product == null) {
			return Result.failure();
		}

		Order order = orderRepository.newEntity();
		order.setSn(String.valueOf(System.currentTimeMillis()));
		order.setProduct(product);
		order.setConsignee(name);
		order.setPhone(mobile);
		order.setMemo(memo);
		order.setAddress(address);
		order.setAmount(product.getPrice());

		if (productSkuId != null) {
			ProductSku sku = productSkuRepository.findOne(productSkuId);
			order.setProductSku(sku);
			order.setAmount(sku.getPrice());

			List<String> specifications = new ArrayList<>();
			sku.getSpecificationValues().forEach(specificationValue -> {
				specifications.add(specificationValue.getSpecification().getName() + ":" + specificationValue.getName());
			});

			order.setSpecification(Joiner.on(", ").join(specifications));
		}

		order.setOrderedDate(new Date());
		orderRepository.save(order);

		return Result.success();
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "/login";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboardPage(@CurrentUser Admin currentUser, Model model) {
		Date now = new Date();
		Date weekAgo = DateUtils.addWeeks(now, -1);
		
		long pendingOrderCount = orderRepository.count(QOrder.order.orderStatus.eq("pending").and(QOrder.order.orderedDate.after(weekAgo)));
		model.addAttribute("pendingOrderCount", pendingOrderCount);
		
		long completedOrderCount = 0l;
		if (currentUser.isAdmin()) {
			completedOrderCount = orderRepository.count(QOrder.order.orderStatus.eq("completed").and(QOrder.order.orderedDate.after(weekAgo)));
		} else if (currentUser.isSalesman()) {
			completedOrderCount = orderRepository
					.count(QOrder.order.orderStatus.eq("completed").and(QOrder.order.operator.eq(currentUser)).and(QOrder.order.orderedDate.after(weekAgo)));
		} else if (currentUser.isShipper()) {
			completedOrderCount = orderRepository
					.count(QOrder.order.orderStatus.eq("completed").and(QOrder.order.shipper.eq(currentUser)).and(QOrder.order.orderedDate.after(weekAgo)));
		}
		model.addAttribute("completedOrderCount", completedOrderCount);
		return "/dashboard";
	}

	@RequestMapping(value = "/console", method = RequestMethod.GET)
	public String dashboardPage(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
		model.addAttribute("currentUser", currentUserDetails == null ? null : currentUserDetails.getCustomUser());
		return "/console";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profilePage(@CurrentUser Admin currentUser, Model model) {
		model.addAttribute("currentUser", currentUser);
		return "/profile";
	}

	@RequestMapping(value = "/profile_save", method = RequestMethod.POST)
	@ResponseBody
	public Result saveProfile(@CurrentUser Admin currentUser, Model model, String name, String oldPassword, String newPassword, String email, String mobile) {
		List<ObjectError> objectErrors = new ArrayList<>();
		if (Strings.isNullOrEmpty(name)) {
			objectErrors.add(new FieldError("currentUser", "name", "姓名不能为空"));
		}

		if (!Strings.isNullOrEmpty(newPassword)) {
			boolean matched = passwordEncoder.matches(oldPassword, currentUser.getPassword());
			if (!matched) {
				objectErrors.add(new FieldError("currentUser", "oldPassword", "旧密码错误"));
			}
			if (!Patterns.PATTENR_PASSWORD.matcher(newPassword).matches()) {
				objectErrors.add(new FieldError("currentUser", "newPassword", "密码不符合格式"));
			}
			currentUser.setPassword(passwordEncoder.encode(newPassword));
		}

		if (!Strings.isNullOrEmpty(email)) {
			Admin otherAdmin = adminRepository.findOne(QAdmin.admin.email.eq(email));
			if (otherAdmin != null && !Objects.equal(otherAdmin, currentUser)) {
				objectErrors.add(new FieldError("currentUser", "email", "该邮箱已占用"));
			}
		}

		if (!Strings.isNullOrEmpty(mobile)) {
			Admin otherAdmin = adminRepository.findOne(QAdmin.admin.mobile.eq(mobile));
			if (otherAdmin != null && !Objects.equal(otherAdmin, currentUser)) {
				objectErrors.add(new FieldError("currentUser", "mobile", "该手机号码已占用"));
			}
		}

		if (objectErrors.size() > 0) {
			return Result.validateError().error(objectErrors);
		}

		currentUser.setName(name);
		currentUser.setEmail(email);
		currentUser.setMobile(mobile);
		adminRepository.save(currentUser);
		return Result.success();
	}

	@RequestMapping(value = "/profile_photo", method = RequestMethod.POST)
	@ResponseBody
	public Result saveProfilePhoto(@CurrentUser Admin currentUser, Model model, String url) {
		if (Strings.isNullOrEmpty(url)) {
			return Result.failure();
		}

		currentUser.setPhoto(url);
		adminRepository.save(currentUser);
		return Result.success();
	}

}
