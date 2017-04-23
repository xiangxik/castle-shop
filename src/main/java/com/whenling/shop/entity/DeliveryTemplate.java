package com.whenling.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.domain.Defaultable;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_delivery_template")
public class DeliveryTemplate extends DataEntity<Admin, Long> implements Defaultable {

	private static final long serialVersionUID = -8495374111555971726L;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	/** 内容 */
	@NotNull
	@Lob
	@Column(nullable = false)
	private String content;

	/** 宽度 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer width;

	/** 高度 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer height;

	/** 偏移量X */
	@NotNull
	@Column(nullable = false)
	private Integer offsetX;

	/** 偏移量Y */
	@NotNull
	@Column(nullable = false)
	private Integer offsetY;

	/** 背景图 */
	@Size(max = 200)
	private String background;

	/** 是否默认 */
	@Column(nullable = false)
	private boolean defaulted = false;

	/** 备注 */
	@Size(max = 200)
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
	}

	public Integer getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

}
