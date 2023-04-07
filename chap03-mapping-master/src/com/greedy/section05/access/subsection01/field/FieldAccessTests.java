package com.greedy.section05.access.subsection01.field;

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

public class FieldAccessTests {
	
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
	public void 필드_접근_테스트() {
		
		//given
		Member member = new Member();
		member.setMemberNo(1);
		member.setMemberId("user01");
		member.setMemberPwd("pass01");
		member.setNickname("홍길동");
		
		//when
		entityManager.persist(member);
		
		//then
		Member foundMember = entityManager.find(Member.class, 1);
		assertEquals(member, foundMember);
		System.out.println(foundMember);
		
		/* 기본적으로 JPA에서는 필드 접근을 사용한다는 것을 확인할 수 있다. (작성한 메소드가 호출되지 않았기 때문에)
		 * 따라서 @Access(AccessType.FIELD) 속성은 생략 가능하다.
		 * 하지만 다른 로직을 처리하거나 값을 검증하는 추가 로직을 수행해야하는 경우에는 프로퍼티 접근 방식을 혼용해서 사용하기도 한다.
		 * */
	}
	
	
	
	
	
	
	
	
	
	
	

}
