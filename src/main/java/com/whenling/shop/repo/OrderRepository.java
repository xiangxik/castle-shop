package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.Order;
import com.whenling.shop.entity.QOrder;

public interface OrderRepository extends BaseJpaRepository<Order, Long>, QuerydslBinderCustomizer<QOrder> {

	@Override
	default void customize(QuerydslBindings bindings, QOrder root) {
		bindings.bind(root.sn, root.product.sn, root.product.name, root.consignee, root.phone, root.address)
				.first((path, value) -> path.contains(value));
	}

}
