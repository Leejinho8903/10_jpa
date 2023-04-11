package com.greedy.section03.projection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectionTests {
	
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    /* 프로젝션(projection)
     * SELECT 절에 조회할 대상을 지정하는 것을 프로젝션이라고 한다.
     * (SELECT {프로젝션 대상} FROM)
     *
     * 프로젝션 대상은 4가지 방식이 있다.
     * 1. 엔티티 프로젝션
     *    원하는 객체를 바로 조회할 수 있다.
     *    조회된 엔티티는 영속성 컨텍스트가 관리한다.
     *
     * 2. 임베디드 타입 프로젝션(임베디드 타입에 대한 설명은 MenuInfo 클래스에서 설명)
     *    엔티티와 거의 비슷하게 사용되며 조회의 시작점이 될 수 없다. -> from 절에 사용 불가
     *    임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.
     *
     * 3. 스칼라 타입 프로젝션
     *    숫자, 문자, 날짜 같은 기본 데이터 타입이다.
     *    스칼라 타입은 영속성 컨텍스트에서 관리되지 않는다.
     *
     * 4. new 명령어를 활용한 프로젝션
     *    다양한 종류의 단순 값들을 DTO로 바로 조회하는 방식으로 new 패키지명.DTO명을 쓰면 해당 DTO로 바로 반환받을 수 있다.
     *    new 명령어를 사용한 클래스의 객체는 엔티티가 아니므로 영속성 컨텍스트에서 관리되지 않는다.
     */
    
    /* 1. 엔티티 프로젝션 */
    @Test
    public void 단일_엔티티_프로젝션_테스트() {
    	
    	//when
    	String jpql = "SELECT m FROM section03_menu m";
    	List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
    	
    	//then
    	assertNotNull(menuList);
    	/* 엔티티 프로젝션은 영속성 컨텍스트에서 관리하는 객체가 된다. */
    	EntityTransaction entityTransaction = entityManager.getTransaction();
    	entityTransaction.begin();
    	menuList.get(1).setMenuName("test");
    	entityTransaction.commit();
    	
    }
    
    @Test
    public void 양방향_연관관계_엔티티_프로젝션_테스트 () {
    	
    	//given
    	int menuCodeParameter = 3;
    	
    	//when
    	String jpql = "SELECT m.category FROM section03_bidirection_menu m WHERE m.menuCode = :menuCode";
    	BiDirectionCategory categoryOfMenu = entityManager.createQuery(jpql, BiDirectionCategory.class)
    			.setParameter("menuCode", menuCodeParameter)
    			.getSingleResult();
    	
    	//then
    	assertNotNull(categoryOfMenu);
    	System.out.println(categoryOfMenu);
    	/* 양방향 연관관계에 있는 엔티티의 경우 역방향 객체 그래프 탐색이 가능하다. */
    	assertNotNull(categoryOfMenu.getMenuList());
    	categoryOfMenu.getMenuList().forEach(System.out::println);
    	
    }
    
    /* 2. 임베디드 타입 프로젝션 */
    @Test
    public void 임베디드_타입_프로젝션_테스트() {
    	
    	//when
    	String jpql = "SELECT m.menuInfo FROM section03_embedded_menu m";
    	List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();
    	
    	//then
    	assertNotNull(menuInfoList);
    	menuInfoList.forEach(System.out::println);
    	
    }
    
    /* 3. 스칼라 타입 프로젝션 */
    @Test
    public void TypedQuery를_이용한_스칼라_타입_프로젝션_테스트() {
    	
    	//when
    	String jpql = "SELECT c.categoryName FROM section03_category c";
    	List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();
    	
    	//then
    	assertNotNull(categoryNameList);
    	categoryNameList.forEach(System.out::println);
    	
    }
    
    /* 조회하려는 컬럼 값이 1개인 경우 TypedQuery로 반환 타입을 단일 값에 대해 지정할 수 있지만 다중 열 컬럼을 조회하는 경우 타입을 지정하지 못한다.
     * 그때는 TypedQuery 대신 Query를 사용하여 Object[]로 행의 정보를 반환 받아 사용한다.
     * */
    
    @Test
    public void Query를_이용한_스칼라_타입_프로젝션_테스트() {
    	
    	//when
    	String jpql = "SELECT c.categoryCode, c.categoryName FROM section03_category c";
    	List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
    	
    	//then
    	assertNotNull(categoryList);
    	categoryList.forEach(row -> {
    		for(Object column : row) System.out.print(column + " ");
    	});
    	
    }
    
    /* Object를 일일히 인덱스별로 처리하거나 다운캐스팅 하는 것은 번거롭기 때문에 new 명령어를 활용한 프로젝션을 더 많이 활용하게 된다. */
    
    /* 4. new 명령어를 활용한 프로젝션 */
    @Test
    public void new_명령어를_활용한_프로젝션_테스트() {
    	
    	//when
    	String jpql = "SELECT new com.greedy.section03.projection.CategoryInfo(c.categoryCode, c.categoryName) FROM section03_category c";
    	List<CategoryInfo> categoryInfoList = entityManager.createQuery(jpql, CategoryInfo.class).getResultList();
    	
    	//then
    	assertNotNull(categoryInfoList);
    	categoryInfoList.forEach(System.out::println);
    }
    
    
    
    
    
    
    
    
    
    
    

}
