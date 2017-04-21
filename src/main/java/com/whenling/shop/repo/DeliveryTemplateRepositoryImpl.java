package com.whenling.shop.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.whenling.shop.entity.DeliveryTemplate;

public class DeliveryTemplateRepositoryImpl implements DeliveryTemplateRepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public DeliveryTemplate findDefault() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeliveryTemplate> query = criteriaBuilder.createQuery(DeliveryTemplate.class);
		Root<DeliveryTemplate> root = query.from(DeliveryTemplate.class);
		query.where(criteriaBuilder.isTrue(root.get("isDefault")));
		return entityManager.createQuery(query).getSingleResult();
	}

}
