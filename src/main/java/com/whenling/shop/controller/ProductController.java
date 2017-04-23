package com.whenling.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.SortNoComparator;
import com.whenling.shop.entity.Product;
import com.whenling.shop.entity.ProductSku;
import com.whenling.shop.entity.QProductSku;
import com.whenling.shop.repo.ProductSkuRepository;
import com.whenling.shop.repo.SpecificationRepository;
import com.whenling.shop.support.mvc.CrudController;

@Controller
@RequestMapping("/product")
public class ProductController extends CrudController<Product, Long> {

	@Autowired
	private SpecificationRepository specificationRepository;

	@Autowired
	private ProductSkuRepository productSkuRepository;

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Product> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onShowEditPage(Product entity, Model model) {
		super.onShowEditPage(entity, model);
		if (entity.isNew()) {
			entity.setSn(String.valueOf(System.currentTimeMillis()));
		}
		entity.getProductImages().sort(SortNoComparator.COMPARATOR);
		entity.getSaleInfos().sort(SortNoComparator.COMPARATOR);

		model.addAttribute("specifications", specificationRepository.findAll());
	}

	@Override
	protected void onBeforeSave(Product entity) {
		super.onBeforeSave(entity);
		Integer productImageCount = getParameter("productImageCount", Integer.class, 0);
		entity.setProductImages(entity.getProductImages().subList(0, productImageCount));

		Integer saleInfoCount = getParameter("saleInfoCount", Integer.class, 0);
		entity.setSaleInfos(entity.getSaleInfos().subList(0, saleInfoCount));

		Integer skuCount = getParameter("skuCount", Integer.class, 0);
		List<ProductSku> skus = entity.getProductSkus().subList(0, skuCount);
		entity.setProductSkus(skus);
	}

	@Override
	protected void onAfterSave(Product entity) {
		super.onAfterSave(entity);

		for (ProductSku sku : entity.getProductSkus()) {
			sku.setProduct(entity);
			productSkuRepository.save(sku);
		}

		productSkuRepository.deleteInBatch(productSkuRepository.findAll(
				QProductSku.productSku.product.eq(entity).and(QProductSku.productSku.notIn(entity.getProductSkus()))));

	}

}
