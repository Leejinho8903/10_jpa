package com.greedy.section03.projection;

import javax.persistence.*;

@Entity(name="section03_bidirection_menu")
@Table(name="TBL_MENU")
public class BiDirectionMenu {

    @Id
    @Column(name="MENU_CODE")
    private int menuCode;

    @Column(name="MENU_NAME")
    private String menuName;

    @Column(name="MENU_PRICE")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name="CATEGORY_CODE")
    private BiDirectionCategory category;

    @Column(name="ORDERABLE_STATUS")
    private String orderableStatus;

    public BiDirectionMenu() {}

    public BiDirectionMenu(int menuCode, String menuName, int menuPrice, BiDirectionCategory category, String orderableStatus) {
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

    public BiDirectionCategory getCategory() {
        return category;
    }

    public void setCategory(BiDirectionCategory category) {
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
        return "BiDirectionMenu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
