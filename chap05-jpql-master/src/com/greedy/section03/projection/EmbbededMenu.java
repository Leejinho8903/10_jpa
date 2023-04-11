package com.greedy.section03.projection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="section03_embedded_menu")	
@Table(name="TBL_MENU")			
public class EmbbededMenu {
	
	@Id									
	@Column(name="MENU_CODE")			
	private int menuCode;
	
	@Embedded	//값 타입을 사용하는 곳에 적용
	private MenuInfo menuInfo;	
	
	@Column(name="CATEGORY_CODE")
	private int categoryCode;
	@Column(name="ORDERABLE_STATUS")
	private String orderableStatus;
	
	public EmbbededMenu() {}

	public EmbbededMenu(int menuCode, MenuInfo menuInfo, int categoryCode, String orderableStatus) {
		super();
		this.menuCode = menuCode;
		this.menuInfo = menuInfo;
		this.categoryCode = categoryCode;
		this.orderableStatus = orderableStatus;
	}

	public int getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}

	public MenuInfo getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(MenuInfo menuInfo) {
		this.menuInfo = menuInfo;
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
		return "EmbbededMenu [menuCode=" + menuCode + ", menuInfo=" + menuInfo + ", categoryCode=" + categoryCode
				+ ", orderableStatus=" + orderableStatus + "]";
	}

	

}
