package com.greedy.section01.manytoone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="many_to_one_menu_and_category")
@Table(name="TBL_MENU")
public class MenuAndCategory {

	@Id									
	@Column(name="MENU_CODE")			
	private int menuCode;
	@Column(name="MENU_NAME")
	private String menuName;
	@Column(name="MENU_PRICE")
	private int menuPrice;
	
	/* @JoinColumn : 외래키를 매핑할 때 사용한다. 
	 * name : 매핑할 외래키의 이름
	 * referencesColumnName : 외래키가 참조하는 대상 테이블의 컬럼명
	 * foreignKey : 외래키 제약 조건을 직접 지정할 수 있으며 테이블 생성시 사용 된다.
	 * unique, nullable, insertable, updatable, columnDefinition, table : @Column의 속성과 동일하다. 
	 * 
	 * @ManyToOne : 다대일 관계에서 사용한다. 
	 * optional : false로 설정하면 연관 된 엔티티가 항상 있어야 한다.
	 * cascade : 영속성 전이 기능을 사용한다. (연관 된 엔티티를 함께 영속성으로 관리한다는 의미) 
	 * orphanRemoval : true로 설정하면 고아 객체 제거
	 * */
	@JoinColumn(name="CATEGORY_CODE")
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Category category;
	
	@Column(name="ORDERABLE_STATUS")
	private String orderableStatus;
	
	public MenuAndCategory() {}

	public MenuAndCategory(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
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
		return "MenuAndCategory [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice
				+ ", category=" + category + ", orderableStatus=" + orderableStatus + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
