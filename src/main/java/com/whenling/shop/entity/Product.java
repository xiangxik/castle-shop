package com.whenling.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.domain.Defaultable;
import com.whenling.castle.repo.domain.Sortable;
import com.whenling.castle.repo.jpa.DataEntity;
import com.whenling.shop.support.repo.SaleInfoListToStringConverter;

@Entity
@Table(name = "tbl_product")
public class Product extends DataEntity<Admin, Long> implements Defaultable {

	private static final long serialVersionUID = -6934807834358886933L;

	/** 编号 */
	@NotNull
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Size(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	private String sn;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

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

	/** 展示图片 */
	@Size(max = 200)
	private String image;

	/** 单位 */
	@Size(max = 100)
	private String unit;

	/** 重量 */
	@Min(0)
	private Integer weight;

	/** 库存 */
	@Min(0)
	private Integer stock;

	/** 是否上架 */
	private Boolean isMarketable;

	/** 是否列出 */
	@NotNull
	@Column(nullable = false)
	private boolean isList = false;

	/** 是否置顶 */
	@NotNull
	@Column(nullable = false)
	private boolean isTop;

	/** 简介 */
	@Size(max = 1000)
	private String summary;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 备注 */
	@Size(max = 200)
	private String memo;

	/** 销量 */
	@Min(0)
	private Long sales;

	/** 商品图片 */
	@Valid
	@ElementCollection
	@CollectionTable(name = "tbl_product_image")
	private List<ProductImage> productImages = new ArrayList<>();

	@Convert(converter = SaleInfoListToStringConverter.class)
	private List<SaleInfo> saleInfos = new ArrayList<>();

	/** 规格 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_product_specification")
	@OrderBy("sortNo asc")
	private Set<Specification> specifications = new HashSet<>();

	/** skus */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductSku> productSkus = new ArrayList<>();

	@Size(max = 200)
	private String express;

	@Size(max = 200)
	private String monthSales;

	/** 页面标题 */
	@Size(max = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Size(max = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Size(max = 200)
	private String seoDescription;

	/** 是否默认 */
	@Column(nullable = false)
	private boolean defaulted = false;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	public boolean isList() {
		return isList;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public List<SaleInfo> getSaleInfos() {
		return saleInfos;
	}

	public void setSaleInfos(List<SaleInfo> saleInfos) {
		this.saleInfos = saleInfos;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	@Override
	public boolean isDefaulted() {
		return this.defaulted;
	}

	@Override
	public void setDefaulted(boolean defaulted) {
		this.defaulted = defaulted;
	}

	@Override
	public void markDefaulted() {
		this.defaulted = true;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(String monthSales) {
		this.monthSales = monthSales;
	}

	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	public List<ProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}

	public static class SaleInfo implements Sortable {
		private String content;

		private Integer sortNo;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public Integer getSortNo() {
			return sortNo;
		}

		@Override
		public void setSortNo(Integer sortNo) {
			this.sortNo = sortNo;
		}

	}

}
