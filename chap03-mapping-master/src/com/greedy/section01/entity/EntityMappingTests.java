package com.greedy.section01.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityMappingTests {
	
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
	
	/* 엔티티와 테이블을 정확하게 매핑하는 것이 JPA의 핵심이다.
	 * 이를 위해 다양한 매핑 어노테이션이 지원되는데 이 부분에 대해서 학습해보자!
	 * */
	
	@Test
	public void 테이블_만들기_테스트() {
		
		//given
		Member member = new Member();
		member.setMemberNo(1);
		member.setMemberId("user01");
		member.setMemberPwd("pass01");
		member.setNickname("홍길동");
		member.setPhone("010-1234-5678");
		member.setAddress("서울시 종로구");
		member.setEnrollDate(new java.sql.Date(System.currentTimeMillis()));
		member.setMemberRole("ROLE_MEMBER");
		member.setStatus("Y");
		
		//when
		entityManager.persist(member);
		
		//then
		Member foundMember = entityManager.find(Member.class, member.getMemberNo());
		assertEquals(member.getMemberNo(), foundMember.getMemberNo());
		
	}
	
	/* commit 하지 않았기 때문에 DML은 rollback 되어 있지만, DDL은 autocommit 구문이기 때문에 테이블은 생성되어 있다. 
	 * 생성 된 자료형은 java object의 자료형을 따르며 크기를 따로 설정하지 않을 경우 숫자는 number(10, 0), 문자열은 varchar2(255 char)
	 * 로 설정 된다. 생성 되는 컬럼의 순서는 PK가 우선이며, 일반 컬럼은 유니코드 오름차순으로 생성 된다.
	 * */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
