package com.whenling.shop.support.setting;

import java.io.Serializable;

public class Setting implements Serializable {

	private static final long serialVersionUID = -8589392256257858575L;

	private static Setting INSTANCE = new Setting();

	public static Setting getInstance() {
		return INSTANCE;
	}

	private Setting() {
	}

	/** 缓存名称 */
	public static final String CACHE_NAME = "setting";

	/** 缓存Key */
	public static final Integer CACHE_KEY = 0;

	/** 网站名称 */
	private String siteName;

	/** 公司名称 */
	private String companyName;

	/** 联系地址 */
	private String address;

	/** 联系电话 */
	private String phone;

	/** 邮政编码 */
	private String zipCode;

	/** E-mail */
	private String email;

	/** 备案编号 */
	private String certtext;

	/** 是否网站开启 */
	private Boolean isSiteEnabled;

	/** 网站关闭消息 */
	private String siteCloseMessage;

	/** baidu统计ID */
	private String baiduSiteId;

	/** 销售获取未处理订单数 */
	private Integer saleOrderCount;

	/** 订单提交提示信息 */
	private String orderSubmitMessage;

	private String seoTitle;

	private String seoKeywords;

	private String seoDescription;

	private String smsUrl;
	private String smsUserId;
	private String smsAccount;
	private String smsPassword;

	private String shippingTemplate;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public Boolean getIsSiteEnabled() {
		return isSiteEnabled;
	}

	public void setIsSiteEnabled(Boolean isSiteEnabled) {
		this.isSiteEnabled = isSiteEnabled;
	}

	public String getSiteCloseMessage() {
		return siteCloseMessage;
	}

	public void setSiteCloseMessage(String siteCloseMessage) {
		this.siteCloseMessage = siteCloseMessage;
	}

	public String getBaiduSiteId() {
		return baiduSiteId;
	}

	public void setBaiduSiteId(String baiduSiteId) {
		this.baiduSiteId = baiduSiteId;
	}

	public Integer getSaleOrderCount() {
		return saleOrderCount;
	}

	public void setSaleOrderCount(Integer saleOrderCount) {
		this.saleOrderCount = saleOrderCount;
	}

	public String getOrderSubmitMessage() {
		return orderSubmitMessage;
	}

	public void setOrderSubmitMessage(String orderSubmitMessage) {
		this.orderSubmitMessage = orderSubmitMessage;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSmsUserId() {
		return smsUserId;
	}

	public void setSmsUserId(String smsUserId) {
		this.smsUserId = smsUserId;
	}

	public String getSmsAccount() {
		return smsAccount;
	}

	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getShippingTemplate() {
		return shippingTemplate;
	}

	public void setShippingTemplate(String shippingTemplate) {
		this.shippingTemplate = shippingTemplate;
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

}
