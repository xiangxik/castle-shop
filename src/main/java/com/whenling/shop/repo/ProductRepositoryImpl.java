package com.whenling.shop.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.whenling.castle.repo.domain.Defaultable;
import com.whenling.shop.entity.Product;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public Product findDefault() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		query.where(criteriaBuilder.isTrue(root.get(Defaultable.PROPERTY_NAME)));
		List<Product> products = entityManager.createQuery(query).getResultList();
		return products != null && products.size() > 0 ? products.get(0) : null;
	}

}
