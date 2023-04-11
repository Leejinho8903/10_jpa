package com.greedy.section03.projection;

import javax.persistence.*;
import java.util.List;

@Entity(name="section03_bidirection_category")
@Table(name="TBL_CATEGORY")
public class BiDirectionCategory {

    @Id
    @Column(name="CATEGORY_CODE")
    private int categoryCode;

    @Column(name="CATEGORY_NAME")
    private String categoryName;

    @Column(name="REF_CATEGORY_CODE")
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")
    private List<BiDirectionMenu> menuList;

    public BiDirectionCategory() {}

    public BiDirectionCategory(int categoryCode, String categoryName, Integer refCategoryCode, List<BiDirectionMenu> menuList) {
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

    public List<BiDirectionMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BiDirectionMenu> menuList) {
        this.menuList = menuList;
    }

    /* 반드시 menuList 출력 부분은 제거할 것 */
    @Override
    public String toString() {
        return "BiDirectionCategory{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
