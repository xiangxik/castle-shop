package com.whenling.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.SortEntity;

@Entity
@Table(name = "tbl_specification_value")
public class SpecificationValue extends SortEntity<Admin, Long> {

	private static final long serialVersionUID = 4615230439932425376L;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	/** 图片 */
	@Size(max = 200)
	private String image;

	/** 规格 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Specification specification;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

}
