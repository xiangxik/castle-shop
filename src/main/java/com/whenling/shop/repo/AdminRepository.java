package com.whenling.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.shop.entity.Admin;
import com.whenling.shop.entity.QAdmin;

public interface AdminRepository extends BaseJpaRepository<Admin, Long>, QuerydslBinderCustomizer<QAdmin> {

	Admin findByUsername(String username);

	@Override
	default void customize(QuerydslBindings bindings, QAdmin root) {
		bindings.bind(root.username, root.name, root.email, root.mobile).first((path, value) -> path.contains(value));
		bindings.excluding(root.password);
	}
}
