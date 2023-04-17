package com.greedy.springjpa.menu.repository;


import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.greedy.springjpa.menu.entity.Category;
import com.greedy.springjpa.menu.entity.Menu;

@Repository
public class MenuRepository {
	
	public Menu findMenuByCode(EntityManager entityManager, int menuCode) {
		
		return entityManager.find(Menu.class, menuCode);
	}
	
	public List<Menu> findAllMenu(EntityManager entityManager) {
		
		String jpql = "SELECT m FROM Menu as m ORDER BY m.menuCode ASC";
		
		return entityManager.createQuery(jpql, Menu.class).getResultList();
	}
	
	public List<Category> findAllCategory(EntityManager entityManager) {
		
		String jpql = "SELECT c FROM Category as c ORDER BY c.categoryCode ASC";
		
		return entityManager.createQuery(jpql, Category.class).getResultList();
	}
	
	public void registNewMenu(EntityManager entityManager, Menu menu) {
		
		entityManager.persist(menu);
	}
	
	public void modifyMenu(EntityManager entityManager, Menu menu) {
		
		/* 전달 받은 메뉴 정보를 통해 해당 엔티티를 먼저 조회한다. */
		Menu selectedMenu = entityManager.find(Menu.class, menu.getMenuCode());
		/* 조회 된 메뉴 객체를 수정한다. */
		selectedMenu.setMenuName(menu.getMenuName());
		
	}
}












