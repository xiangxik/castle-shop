package com.whenling.shop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.SortEntity;

@Entity
@Table(name = "tbl_specification")
public class Specification extends SortEntity<Admin, Long> {

	private static final long serialVersionUID = 316421106717443159L;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	/** 类型 text、image */
	@NotNull
	@Column(nullable = false)
	private String type = "text";

	/** 备注 */
	@Size(max = 200)
	private String memo;

	/** 规格值 */
	@Valid
	@NotNull
	@OneToMany(mappedBy = "specification", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("sortNo asc")
	private List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

}
