package com.whenling.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_delivery_center")
public class DeliveryCenter extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = 3068020458925742990L;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String name;

	/** 联系人 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String contact;

	/** 地址 */
	@NotNull
	@Size(max = 200)
	@Column(nullable = false)
	private String address;

	/** 邮编 */
	@Size(max = 200)
	private String zipCode;

	/** 电话 */
	@Size(max = 200)
	private String phone;

	/** 手机 */
	@Size(max = 200)
	private String mobile;

	/** 备注 */
	@Size(max = 200)
	private String memo;

	/** 是否默认 */
	@Column(nullable = false)
	private boolean isDefault = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

}
