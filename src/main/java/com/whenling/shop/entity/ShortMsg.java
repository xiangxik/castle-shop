package com.whenling.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_short_msg")
public class ShortMsg extends BaseEntity<Long> {

	private static final long serialVersionUID = 7267246349467653652L;

	@NotNull
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String mobile;

	@Lob
	private String content;

	private Date sendDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Admin operator;

	private boolean success = false;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
