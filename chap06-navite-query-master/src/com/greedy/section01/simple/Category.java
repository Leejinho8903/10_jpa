package com.greedy.section01.simple;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

@Entity(name="section01_category")
@Table(name="TBL_CATEGORY")
@SqlResultSetMappings(
		value= 
	    {
	    		/* 1. @Column으로 매핑 설정이 되어 있는 경우 사용 - 자동 엔티티 매핑 */
				@SqlResultSetMapping(
						name = "categoryCountAutoMapping",							// 결과 매핑 이름
						entities = {@EntityResult(entityClass = Category.class)},   // @EntityResult를 사용해서 엔티티를 결과로 매핑
						columns = {@ColumnResult(name = "MENU_COUNT")}				// @ColumnResult를 사용해서 컬럼을 결과로 매핑
				),
				/* 2. 매핑 설정을 수동으로 해 주는 경우 - @Column은 생략 가능 */
				@SqlResultSetMapping(
						name = "categoryCountManualMapping",
						entities = {
								@EntityResult(entityClass = Category.class, fields = {
										@FieldResult(name = "categoryCode", column = "CATEGORY_CODE"),
										@FieldResult(name = "categoryName", column = "CATEGORY_Name"),
										@FieldResult(name = "refCategoryCode", column = "REF_CATEGORY_CODE")
								})
						},
						columns = {@ColumnResult(name = "MENU_COUNT")}		
				)
		}
)
public class Category {
	
	@Id
	@Column(name="CATEGORY_CODE")
	private int categoryCode;
	@Column(name="CATEGORY_NAME")
	private String categoryName;
	@Column(name="REF_CATEGORY_CODE")
	private Integer refCategoryCode;
	
	public Category() {}

	public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
		super();
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.refCategoryCode = refCategoryCode;
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

	@Override
	public String toString() {
		return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
				+ refCategoryCode + "]";
	}
	
	

}
