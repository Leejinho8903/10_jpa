package com.greedy.section01.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class A_EntityManagerLifeCycleTests {
	
    /* PersistenceContext, EntityManagerFactory, EntityManager ?
    * 엔터티 매니저(EntityManager)란?
    * 엔터티 매니저는 엔터티를 저장하는 메모리상의 데이터베이스라고 생각하면 된다.
    * 엔터티를 저장하고 수정하고 삭제하고 조회하는 등의 엔터티와 관련된 모든 일을 한다.
    * 엔터티 매니저는 스레드세이프 하지 않기 때문에 동시성 문제가 발생할 수 있기 때문에 스레드간 공유를 하면 안된다.
    * web의 경우 일반적으로는 request scope와 일치시킨다.
    *
    * 엔터티 매니저 팩토리(EntityManagerFactory)란?
    * 엔터티 매니저를 생성할 수 있는 기능을 제공하는 팩토리 클래스이다.
    * 스레드세이프이기 때문에 여러 스레드가 동시에 접근해도 안전하기 때문에 서로 다른 스레드간 공유해서 재사용한다.
    * 하지만 스레드 세이프 한 기능을 요청 스코프마다 생성하기에는 비용(시간, 메모리)부담이 크기 때문에
    * application 스코프와 동일한 싱글톤으로 생성해서 관리하게 된다.
    * 따라서 데이터베이스를 사용하는 애플리케이션 당 한 개의 EntityManagerFactory를 생성한다.
    *
    * 영속성 컨텍스트(PersistenceContext)란?
    * 영속성 컨텍스트는 엔터티를 영구 저장하는 환경을 말한다.
    * 엔터티 매니저에 엔터티를 저장하거나 조회하면 엔터티 매니저는 영속성 컨텍스트에 엔터티를 보관하고 관리한다.
    * 영속성 엔터티를 key value방식으로 저장하는 저장소 역할을 한다.
    * 영속성 컨텍스트는 엔터티 매니저를 생성할 때 하나 만들어진다.
    * 그리고 엔터티 매니저를 통해서 영속성 컨텍스트에 접근할 수 있고 영속성 컨텍스트를 관리할 수 있다.
    * */
	
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
	
	/* 해쉬 코드를 출력하여 라이프 사이클을 확인한다. */
	@Test
	public void 엔터티_매니저_팩토리와_엔터티_매니저_생명주기_확인1() {
		System.out.println("entityManagerFactory.hashCode : " + entityManagerFactory.hashCode());
		System.out.println("entityManager.hashCode : " + entityManager.hashCode());
	}
	
	@Test
	public void 엔터티_매니저_팩토리와_엔터티_매니저_생명주기_확인2() {
		System.out.println("entityManagerFactory.hashCode : " + entityManagerFactory.hashCode());
		System.out.println("entityManager.hashCode : " + entityManager.hashCode());
	}
	
	@AfterAll
	public static void closeFactory() {
		entityManagerFactory.close();
	}
	
	@AfterEach
	public void closeManager() {
		entityManager.close();
	}
	

}
