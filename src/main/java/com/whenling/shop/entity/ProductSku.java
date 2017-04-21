package com.whenling.shop.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.whenling.castle.repo.domain.Sortable;
import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_product_sku")
public class ProductSku extends BaseEntity<Long> implements Sortable {

	private static final long serialVersionUID = -813614996133939277L;

	/** 销售价 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/** 市场价 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal marketPrice;

	/** 排序 */
	private Integer sortNo;

	/** 商品编号 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/** 规格值 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_sku_specification_value")
	@OrderBy("specification asc")
	private Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();

	@Override
	public Integer getSortNo() {
		return sortNo;
	}

	@Override
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Set<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(Set<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
