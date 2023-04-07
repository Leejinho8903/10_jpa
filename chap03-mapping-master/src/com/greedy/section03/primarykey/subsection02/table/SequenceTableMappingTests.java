package com.greedy.section03.primarykey.subsection02.table;

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

public class SequenceTableMappingTests {
	

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
	
	/* 3. TABLE 전략 */
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
		String jpql = "SELECT A.memberNo FROM sequence_table_member A";
		List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();
		
		memberNoList.forEach(System.out::println);
		
	}
	

}
