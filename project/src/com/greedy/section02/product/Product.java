package com.greedy.section02.product;

import java.sql.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity(name="produt_test")
@Table(name="TBL_PRODUCT")
@TableGenerator(
		name="PRODUCT_SEQ_TABLE_GENERATOR",
		table="TBL_MY_PRODUCT_SEQUENCES",
		pkColumnValue="SEQ_PRODUCT_NO",
		allocationSize=1
)
public class Product {
	
	@Id
	@Column(name="PRODUCT_NO")
	@GeneratedValue(strategy = GenerationType.TABLE, generator="PRODUCT_SEQ_TABLE_GENERATOR")
	private int productNo;
	
	@Column(name="PRODUCT_NAME", nullable=false)
	private String productName;
	
	@Column(name="PRODUCT_CATEGORY", nullable=false)
	@Enumerated(EnumType.STRING)
	private Category productCategory;
	
	private String productPrice;
	
	@Column(name="RELEASE_DATE", nullable=false)
	private java.sql.Date releaseDate;
	
	public Product() {}

	public Product(int productNo, String productName, Category productCategory, String productPrice, Date releaseDate) {
		super();
		this.productNo = productNo;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productPrice = productPrice;
		this.releaseDate = releaseDate;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Category getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Category productCategory) {
		this.productCategory = productCategory;
	}

	@Access(AccessType.PROPERTY)
	@Column(name="PRODUCT_PRICE", nullable=false)
	public String getProductPrice() {
		return productPrice + "원";
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public java.sql.Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(java.sql.Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Product [productNo=" + productNo + ", productName=" + productName + ", productCategory="
				+ productCategory + ", productPrice=" + productPrice + ", releaseDate=" + releaseDate + "]";
	}
	
	

}