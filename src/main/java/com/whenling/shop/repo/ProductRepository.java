package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.Product;
import com.whenling.shop.entity.QProduct;

public interface ProductRepository extends BaseJpaRepository<Product, Long>, QuerydslBinderCustomizer<QProduct> {

	@Override
	default void customize(QuerydslBindings bindings, QProduct root) {
		bindings.bind(root.name, root.sn).first((path, value) -> path.contains(value));
	}

}
