package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.QSpecification;
import com.whenling.shop.entity.Specification;

public interface SpecificationRepository
		extends BaseJpaRepository<Specification, Long>, QuerydslBinderCustomizer<QSpecification> {
	@Override
	default void customize(QuerydslBindings bindings, QSpecification root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
