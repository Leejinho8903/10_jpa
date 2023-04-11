package com.greedy.section03.bidirection;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="bidirection_category")
@Table(name="TBL_CATEGORY")
public class Category {
	
	@Id
	@Column(name="CATEGORY_CODE")
	private int categoryCode;
	@Column(name="CATEGORY_NAME")
	private String categoryName;
	@Column(name="REF_CATEGORY_CODE")
	private Integer refCategoryCode;
	
	/* mappedBy : 연관 관계의 주인을 정하기 위해서 연관 관계의 주인이 아닌 객체에 mappedBy를 써서 연관 관계 주인 객체의
	 * 필드명을 매핑 시켜 놓으면 로직으로 양방향 관계를 적용할 수 있다. */
	@OneToMany(mappedBy="category")
	private List<Menu> menuList;
	
	public Category() {}

	public Category(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
		super();
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.refCategoryCode = refCategoryCode;
		this.menuList = menuList;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getRefCategoryCode() {
		return refCategoryCode;
	}

	public void setRefCategoryCode(Integer refCategoryCode) {
		this.refCategoryCode = refCategoryCode;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	@Override
	public String toString() {
		return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
				+ refCategoryCode + "]";
	}

	
	

}
