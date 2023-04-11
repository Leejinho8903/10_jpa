package com.greedy.section02.named;

import javax.persistence.*;

@Entity(name="section02_category")
@Table(name="TBL_CATEGORY")
@SqlResultSetMapping(name = "categoryCountAutoMapping2",			// section1의 매핑 이름과 다른 이름을 주어야 한다.
        entities = {@EntityResult(entityClass = Category.class)},
        columns = {@ColumnResult(name = "MENU_COUNT")}
)
@NamedNativeQueries(
        value = {
            @NamedNativeQuery(
                    name = "Category.menuCountOfCategory",
                    query = "SELECT\n"
                            + "       A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT\n"
                            + "  FROM TBL_CATEGORY A\n"
                            + "  LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE \n"
                            + "               FROM TBL_MENU B\n"
                            + "              GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)\n"
                            + " ORDER BY 1",
                    resultSetMapping = "categoryCountAutoMapping2"
            )
        }
)
public class Category {

    @Id
	@Column(name = "CATEGORY_CODE")
    private int categoryCode;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "REF_CATEGORY_CODE")
    private Integer refCategoryCode;

    public Category() {}

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
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
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
