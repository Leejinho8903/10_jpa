package com.greedy.section05.access.subsection02.property;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropertyAccessTests {
	
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
	
	/* @Access
	 * JPA가 엔티티 데이터에 접근하는 방식을 지정할 수 있으며 두 가지 방식이 있다.
	 * 1. 필드 접근 : 필드에 직접 접근하며 필드 접근 권한이 private여도 접근할 수 있다.
	 * 2. 프로퍼티 접근 : 접근자(Getter)를 이용하여 접근하는 방식이다.
	 * 만약 @Access를 설정하지 않으면 @Id의 위치를 기준으로 접근 방식이 설정된다.
	 * */
	
	@Test
	public void 프로퍼티_접근_테스트() {
		
		//given
		Member member = new Member();
		member.setMemberNo(1);
		member.setMemberId("user01");
		member.setMemberPwd("pass01");
		member.setNickname("홍길동");
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(member);
		entityTransaction.commit();
		
		//then
		String jpql = "SELECT a.nickname FROM property_access_member a WHERE a.memberNo = 1";
		String registedNickname = entityManager.createQuery(jpql, String.class).getSingleResult();
		assertEquals("홍길동님", registedNickname);
	}
	
	/* @Id의 위치에 따라 @Access 방식이 결정되며 이는 전역적인 설정이다.
	 * @Access(AccessType.PROPERTY) 설정을 getter에 지정하게 되면 특정 필드만 Getter 메소드로 접근할 수 있으며,
	 * 추가 로직이 필요한 경우 설정한다.
	 * */
	
	
	
	
	
	
	
	
	

}
