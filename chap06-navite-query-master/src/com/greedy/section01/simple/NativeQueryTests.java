package com.greedy.section01.simple;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NativeQueryTests {
	
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

    /* 네이티브 SQL
     * : JPQL은 데이터베이스들이 따로 지원하는 것들에 있어 모든 것을 SQL로 자동으로 바꿔주지 않는다.
     *   (인라인 뷰, UNION, INTERSECT 등등)
     *   네이티브 SQL은 SQL을 개발자가 직접 정의해서 사용할 수 있도록 해주는 수동모드이다.
     *   쉽게 말해, 어떠한 다양한 이유로 JPQL을 사용할 수 없는 경우나 SQL쿼리를 최적화 해서 
     *   데이터 베이스의 성능을 향상시킬 때 JPA는 Native SQL을 통해 SQL을 직접 사용할 수 있는 기능을 제공해 준다.
     *
     * JDBC API와의 차이점
     * : 직접 SQL을 작성하는 JDBC API와는 달리 네이티브 SQL은 JPA의 영속성 컨텍스트의 기능을 그대로 사용할 수 있다.
     *
     * 네이티브 쿼리 API는 다음의 3가지가 있다.
     *   1. 결과 타입 정의
     *   public Query createNativeQuery(String sqlString, Class resultClass);
     *
     *   2. 결과 타입을 정의할 수 없을 때
     *   public Query createNativeQuery(String sqlString);
     *
     *   3. 결과 매핑 사용
     *   public Query createNativeQuery(String sqlString, String resultSetMapping);
     */
    
    /* 1. 결과 타입 정의 */
    @Test
    public void 결과_타입을_정의한_네이티브_쿼리_사용_테스트() {
    	
    	//given
    	int menuCodeParameter = 15;
    	
    	//when
    	/* 우리가 사용하는 DBMS의 고유한 SQL문법을 작성한다. 위치 기반 파라미터로만 사용이 가능하다. */
    	String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS FROM TBL_MENU WHERE MENU_CODE = ?";
    	//String query = "SELECT * FROM TBL_MENU WHERE MENU_CODE = ?";
    	
    	/* 일부 컬럼만 조회하는 것은 불가능하다. */
    	//String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE FROM TBL_MENU WHERE MENU_CODE = ?";
    	
    	/* 주의
    	 * 모든 컬럼값을 매핑하는 경우에만 타입을 특정할 수 있다. 일부 컬럼만 조회하고 싶은 경우는 Object[] 또는 스칼라 값을 별도로 담을 클래스를 정의해서 사용해야 한다. */
    	Query nativeQuery = entityManager.createNativeQuery(query, Menu.class).setParameter(1, menuCodeParameter);
    	Menu foundMenu = (Menu) nativeQuery.getSingleResult();
    	
    	//then
    	assertNotNull(foundMenu);
    	assertTrue(entityManager.contains(foundMenu));	//영속성 컨텍스트에서 관리하는 객체이다.
    	System.out.println(foundMenu);
    	
    }
    
    @Test
    public void 결과_타입을_정의할_수_없는_경우_조회_테스트() {
    	
    	//when
    	String query = "SELECT MENU_NAME, MENU_PRICE FROM TBL_MENU";
    	List<Object[]> menuList = entityManager.createNativeQuery(query).getResultList();
    	
    	/* 결과 타입을 특정하는 것 자체가 불가능하다. 엔티티로 매핑 시킬 경우에만 클래스 타입을 입력한다. */
    	//List<Object[]> menuList = entityManager.createNativeQuery(query, Object[].class).getResultList();
    	
    	//then
    	assertNotNull(menuList);
    	menuList.forEach(row -> {
    		Stream.of(row).forEach(col -> System.out.print(col + " "));
    		System.out.println();
    	});
    	
    }
    
    @Test
    public void 자동_결과_매핑을_사용한_조회_테스트() {
    	
        //when
        String query = "SELECT\n"
                    + "       A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT\n"
                    + "  FROM TBL_CATEGORY A\n"
                    + "  LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE \n"
                    + "               FROM TBL_MENU B\n"
                    + "              GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)\n"
                    + " ORDER BY 1";
    	
    	Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountAutoMapping");
    	List<Object[]> categoryList = nativeQuery.getResultList();
    	
    	//then
    	assertTrue(entityManager.contains(categoryList.get(0)[0]));
    	assertNotNull(categoryList);
    	categoryList.forEach(row -> {
    		Stream.of(row).forEach(col -> System.out.print(col + " "));
    		System.out.println();
    	});
    	
    }
    
    @Test
    public void 수동_결과_매핑을_사용한_조회_테스트() {
    	
        //when
        String query = "SELECT\n"
                    + "       A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT\n"
                    + "  FROM TBL_CATEGORY A\n"
                    + "  LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE \n"
                    + "               FROM TBL_MENU B\n"
                    + "              GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)\n"
                    + " ORDER BY 1";
    	
    	Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountManualMapping");
    	List<Object[]> categoryList = nativeQuery.getResultList();
    	
    	//then
    	assertTrue(entityManager.contains(categoryList.get(0)[0]));
    	assertNotNull(categoryList);
    	categoryList.forEach(row -> {
    		Stream.of(row).forEach(col -> System.out.print(col + " "));
    		System.out.println();
    	});
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
