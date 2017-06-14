package com.whenling.shop.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.domain.Disabledable;
import com.whenling.castle.repo.jpa.SortEntity;

@Entity
@Table(name = "tbl_index_slider")
public class IndexSlider extends SortEntity<Admin, Long> implements Disabledable {

	private static final long serialVersionUID = 1525593983642483869L;

	@NotNull
	@Size(min = 2, max = 200)
	private String title;

	private String image;

	private boolean disabled = false;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public void markDisabled() {
		this.disabled = true;
	}

}
