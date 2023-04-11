package com.greedy.section02.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="one_to_many_menu")
@Table(name="TBL_MENU")
public class Menu {

	@Id									
	@Column(name="MENU_CODE")			
	private int menuCode;
	@Column(name="MENU_NAME")
	private String menuName;
	@Column(name="MENU_PRICE")
	private int menuPrice;
	@Column(name="CATEGORY_CODE")
	private int categoryCode;
	@Column(name="ORDERABLE_STATUS")
	private String orderableStatus;
	
	public Menu() {}

	public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
		super();
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.categoryCode = categoryCode;
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

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getOrderableStatus() {
		return orderableStatus;
	}

	public void setOrderableStatus(String orderableStatus) {
		this.orderableStatus = orderableStatus;
	}

	@Override
	public String toString() {
		return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", categoryCode="
				+ categoryCode + ", orderableStatus=" + orderableStatus + "]";
	}

}
