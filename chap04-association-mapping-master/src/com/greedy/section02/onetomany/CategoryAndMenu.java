package com.greedy.section02.onetomany;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="one_to_many_category_and_menu")
@Table(name="TBL_CATEGORY")
public class CategoryAndMenu {
	
	@Id
	@Column(name="CATEGORY_CODE")
	private int categoryCode;
	@Column(name="CATEGORY_NAME")
	private String categoryName;
	@Column(name="REF_CATEGORY_CODE")
	private Integer refCategoryCode;
	@JoinColumn(name="CATEGORY_CODE")
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Menu> menuList;
	
	public CategoryAndMenu() {}

	public CategoryAndMenu(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
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
		return "CategoryAndMenu [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
				+ refCategoryCode + ", menuList=" + menuList + "]";
	}

	
	
	

}
