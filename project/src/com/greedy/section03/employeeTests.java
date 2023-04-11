package com.greedy.section03;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class employeeTests {

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
    
    @Test
    public void 사원_아이디_조회_테스트() {
    	
    	String jpql = "SELECT e FROM employee e WHERE e.empId = 200";
    	
    	List<Employee> empList = entityManager.createQuery(jpql, Employee.class).getResultList();
    	
    	System.out.println(empList);
    	
    }
    
    @Test
    public void 사원_등록_테스트() {
    	
    	Employee emp = new Employee();
    	emp.setEmpId(777);
    	emp.setEmpName("아무개");
    	emp.setEmpNo("977777-1421211");
    	emp.setJobCode("J7");
    	emp.setSalLevel("S3");
    	
    	EntityTransaction tr = entityManager.getTransaction();
    	tr.begin();
    	entityManager.persist(emp);
    	tr.commit();
    	
    	Employee foundEmp = entityManager.find(Employee.class, 777);
    	assertEquals(foundEmp.getEmpId(), 777);
    }
       
    @Test
    public void 새로운_사원_부서_등록_테스트() {
    	
    	//given 
    	Departement dp = new Departement();
    	dp.setDeptId("C9");
    	dp.setDeptTitle("부부부");
    	dp.setLocationId("L1");
    	
    	Employee emp = new Employee();
    	emp.setEmpId(678);
    	emp.setEmpName("이진호");
    	emp.setEmpNo("715548-1054219");
    	emp.setEmail("asdkl@naver.com");
    	emp.setPhone("01212345688");
    	emp.setJobCode("J7");
    	emp.setSalLevel("S3");
    	emp.setSalary(4300000);
    	emp.setBonus(1.5);
    	emp.setManagerId(null);
    	emp.setHireDate(new java.sql.Date(System.currentTimeMillis()));
    	emp.setEntDate(null);
    	emp.setEntYn(null);
    	
    	emp.setDepartment(dp);
    	
    	EntityTransaction tr = entityManager.getTransaction();
    	tr.begin();
    	entityManager.persist(emp);
    	tr.commit();
    	
    	Employee foundEmp = entityManager.find(Employee.class, emp.getEmpId());
    	assertEquals(foundEmp.getEmpId(), emp.getEmpId());
    	System.out.println(foundEmp);
    	
    }
    
    @Test
    public void 전체_사원_조회_테스트() {
    	
    	String jpql = "SELECT e FROM employee e";
		
    	List<Employee> empList = entityManager.createQuery(jpql, Employee.class).getResultList();
    	
    	empList.forEach(System.out::println);

    }
    
    @Test
    public void 하씨_성을_가진_사원_조회_테스트() {
    	
    	String jpql = "SELECT e FROM employee e WHERE e.empName LIKE '하%'";
    	List<Employee> empList = entityManager.createQuery(jpql, Employee.class).getResultList();
    	
    	empList.forEach(System.out::println);
    }
    
	
}
