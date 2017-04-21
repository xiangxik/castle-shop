package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.QShortMsg;
import com.whenling.shop.entity.ShortMsg;

public interface ShortMsgRepository extends BaseJpaRepository<ShortMsg, Long>, QuerydslBinderCustomizer<QShortMsg> {

	@Override
	default void customize(QuerydslBindings bindings, QShortMsg root) {
		bindings.bind(root.mobile, root.content).first((path, value) -> path.contains(value));
	}

}
