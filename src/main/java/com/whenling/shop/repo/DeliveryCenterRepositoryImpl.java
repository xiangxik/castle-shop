package com.whenling.shop.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.whenling.shop.entity.DeliveryCenter;

public class DeliveryCenterRepositoryImpl implements DeliveryCenterRepositoryCustom {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public DeliveryCenter findDefault() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeliveryCenter> query = criteriaBuilder.createQuery(DeliveryCenter.class);
		Root<DeliveryCenter> root = query.from(DeliveryCenter.class);
		query.where(criteriaBuilder.isTrue(root.get("isDefault")));
		return entityManager.createQuery(query).getSingleResult();
	}

}
