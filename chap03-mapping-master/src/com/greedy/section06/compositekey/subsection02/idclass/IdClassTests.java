package com.greedy.section06.compositekey.subsection02.idclass;

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

public class IdClassTests {
	
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
	
	/* 복합키가 존재하는 테이블 매핑의 경우 별도의 방법이 필요하다. 
	 * 1. @Embeddable : @Embeddable 클래스에 복합 키를 정의하고 엔티티에 @EmbeddedId를 이용해 복합 키 클래스를 매핑한다. 
	 * 2. @IdClass : 복합키를 필드로 정의한 클래스를 이용해 엔티티 클래스에 복합키에 해당하는 필드에 @Id를 매핑한다.
	 * 
	 * 두 방식 모두 복합키 클래스는 영속성 컨텍스트가 관리하지 않는다는 특징이 있으며, 큰 기능적 차이도 존재하지 않는다.
	 * 다만 @Embeddable이 조금 더 객체 지향다운 방법이고, @IdClass는 관계형 데이터 베이스에 가까운 방법이다.
	 * */
	
	@Test
	public void 아이디_클래스_사용한_복합키_테이블_매핑_테스트() {
		
		//given
		Member member = new Member();
		member.setMemberNo(1);
		member.setMemberId("user01");
		member.setPhone("010-1234-5678");
		member.setAddress("서울시 종로구");
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(member);
		entityTransaction.commit();
		
		//then
		/* 제약 조건 딕셔너리뷰를 조회하여 복합키에 대한 제약조건 확인 */
        String nativeSQL = "SELECT UC.CONSTRAINT_NAME, UC.CONSTRAINT_TYPE, UCC.COLUMN_NAME " +
                "FROM USER_CONSTRAINTS UC " +
                "JOIN USER_CONS_COLUMNS UCC ON (UC.CONSTRAINT_NAME = UCC.CONSTRAINT_NAME) " +
                "WHERE UCC.TABLE_NAME = 'TBL_MEMBER_SECTION06_SUBSECTION02'" +
                "AND UC.CONSTRAINT_TYPE = 'P'";
        
        List<Object[]> pkColumnList = entityManager.createNativeQuery(nativeSQL).getResultList();
        
        for(Object[] pkColumn : pkColumnList) {
        	for(Object info : pkColumn) {
        		System.out.print(info + " ");
        	}
        	System.out.println();
        }
	}
	

}
