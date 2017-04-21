package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.DeliveryCenter;
import com.whenling.shop.entity.QDeliveryCenter;

public interface DeliveryCenterRepository extends BaseJpaRepository<DeliveryCenter, Long>,
		QuerydslBinderCustomizer<QDeliveryCenter>, DeliveryCenterRepositoryCustom {
	@Override
	default void customize(QuerydslBindings bindings, QDeliveryCenter root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
