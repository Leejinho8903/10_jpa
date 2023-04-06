package com.greedy.section02.crud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SECTION02_MENU")	//엔티티 객체로 만들기 위한 어노테이션. 다른 패키지에 동일 이름의 클래스가 존재하면 name 지정 필요.
@Table(name="TBL_MENU")			//데이터베이스에 매핑 될 테이블 이름 설정.
//@SequenceGenerator(
//		name="SEQ_MENU_CODE_GENERATOR",		//해당 시퀀스 설정에 대한 이름
//		sequenceName="SEQ_MENU_CODE",		//사용할 시퀀스 이름
//		initialValue=100,					//초기값인데 아무 값이라도 지정은 해주어야 한다. (DB의 설정을 따름)
//		allocationSize=1					//증가값
//)
public class Menu {
	
	@Id									//PK에 해당하는 속성에 지정
	@Column(name="MENU_CODE")			//데이터베이스에 대응되는 컬럼명 지정
//	@GeneratedValue(
//			strategy=GenerationType.SEQUENCE,		//값 생성 시 시퀀스 전략을 이용하겠다는 설정
//			generator="SEQ_MENU_CODE_GENERATOR"		//사용할 시퀀스 설정 이름
//	)
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
