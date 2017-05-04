package com.whenling.shop.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_order")
public class Order extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = -5896090021660813322L;

	/** 订单编号 */
	@NotNull
	@Size(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	private String sn;

	/** 订单状态 pending、inhand、confirmed、followup、canceled、completed */
	@Column(nullable = false)
	private String orderStatus = "pending";

	/** 配送状态 unshipped、shipped、received、returned */
	@Column(nullable = false)
	private String shippingStatus = "unshipped";

	/** 商品编号 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductSku productSku;

	@Size(max = 200)
	private String specification;

	@Size(max = 100)
	private String consignee;

	/** 地址 */
	@Size(max = 200)
	private String address;

	/** 电话 */
	@Size(max = 100)
	private String phone;

	/** 下单时间 */
	private Date orderedDate;

	/** 订单金额 */
	@Min(0)
	private BigDecimal amount;

	/** 备注 */
	@Size(max = 500)
	@Column(length = 1000)
	private String memo;

	/** 备注 */
	@Lob
	private String remark;

	/** 销售员 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Admin operator;

	/** 发货员 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Admin shipper;

	@Size(max = 100)
	private String shippingSn;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	public Admin getShipper() {
		return shipper;
	}

	public void setShipper(Admin shipper) {
		this.shipper = shipper;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShippingSn() {
		return shippingSn;
	}

	public void setShippingSn(String shippingSn) {
		this.shippingSn = shippingSn;
	}
}
