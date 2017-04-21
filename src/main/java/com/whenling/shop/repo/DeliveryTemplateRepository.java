package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.DeliveryTemplate;
import com.whenling.shop.entity.QDeliveryTemplate;

public interface DeliveryTemplateRepository extends BaseJpaRepository<DeliveryTemplate, Long>,
		QuerydslBinderCustomizer<QDeliveryTemplate>, DeliveryTemplateRepositoryCustom {
	@Override
	default void customize(QuerydslBindings bindings, QDeliveryTemplate root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
