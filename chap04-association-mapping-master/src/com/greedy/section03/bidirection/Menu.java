package com.greedy.section03.bidirection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="bidirection_menu")
@Table(name="TBL_MENU")
public class Menu {

	@Id									
	@Column(name="MENU_CODE")			
	private int menuCode;
	@Column(name="MENU_NAME")
	private String menuName;
	@Column(name="MENU_PRICE")
	private int menuPrice;
	/* 연관 관계의 주인의 경우 전과 똑같은 방식으로 연관 관계 매핑을 처리하면 된다. */
	@JoinColumn(name="CATEGORY_CODE")
	@ManyToOne
	private Category category;
	@Column(name="ORDERABLE_STATUS")
	private String orderableStatus;
	
	public Menu() {}

	public Menu(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
		super();
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.category = category;
		this.orderableStatus = orderableStatus;
	}

	public int getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(int menuPrice) {
		this.menuPrice = menuPrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getOrderableStatus() {
		return orderableStatus;
	}

	public void setOrderableStatus(String orderableStatus) {
		this.orderableStatus = orderableStatus;
	}

	@Override
	public String toString() {
		return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", category="
				+ category + ", orderableStatus=" + orderableStatus + "]";
	}

	

}
