package com.greedy.section03.primarykey.subsection01.sequence;

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

public class PrimaryKeyMappingTests {
	
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
	
    /* 데이터베이스마다 기본 키를 생성하는 방식이 서로 다르므로 이 문제를 해결하기는 쉽지 않다.
    * JPA는 이런 문제들을 해결하기 위해 3가지 방식의 설정을 제공한다.
    * 1. IDENTITY : 기본 키 생성을 데이터베이스에 위임한다.
    * 2. SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
    * 3. TABLE : 키 생성 테이블을 사용한다.
    *
    * 오라클 데이터베이스는 시퀀스를 제공하지만 MYSQL은 시퀀스 대신 자동으로 숫자를 증가시켜주는 AUTO_INCREMENT기능을 제공한다.
    * AUTO_INCREMENT같은 기능은 IDENTITY 설정으로, SEQUENCE를 이용하는 곳에서는 SEQUENCE 설정으로 기본 키 사용을 매핑할 수 있다.
    * 따라서 SEQUENCE나 IDENTITY전략은 사용하는 데이터베이스에 의존하게 된다.
    *
    * @Id 적용이 가능한 자바 타입
    * 1. 자바 기본형
    * 2. 자바 Wrapper 타입
    * 3. String
    * 4. java.util.Date
    * 5. java.sql.Date
    * 6. java.math.BigDecimal
    * 7. java.math.BigInteger
    * */
	
	/* 1. IDENTITY 전략은 오라클 dbms로 사용할 수 없으므로 생략한다.
	 * 2. SEQUENCE 전략
	 * */
	
	@Test
	public void 식별자_매핑_테스트() {
		
		// given
		Member member = new Member();
		member.setMemberId("user01");
		member.setMemberPwd("pass01");
		member.setNickname("홍길동");
		member.setPhone("010-1234-5678");
		member.setAddress("서울시 종로구");
		member.setEnrollDate(new java.sql.Date(System.currentTimeMillis()));
		member.setMemberRole("ROLE_MEMBER");
		member.setStatus("Y");
		
		Member member2 = new Member();
		member2.setMemberId("user02");
		member2.setMemberPwd("pass02");
		member2.setNickname("유관순");
		member2.setPhone("010-1234-5678");
		member2.setAddress("서울시 종로구");
		member2.setEnrollDate(new java.sql.Date(System.currentTimeMillis()));
		member2.setMemberRole("ROLE_MEMBER");
		member2.setStatus("Y");

		// when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(member);
		entityManager.persist(member2);
		entityTransaction.commit();
		
		//then
		String jpql = "SELECT A.memberNo FROM sequence_member A";
		List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();
		
		memberNoList.forEach(System.out::println);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
