package com.greedy.intro.section01.problem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class A_ProblemOfUsingDirectSQLTests {

    /* <JDBC API를 이용해 직접 SQL을 다룰 때 발생하는 문제점>
    * 1. 데이터 변환, SQL작성, JDBC API 코드 등을 반복적으로 일일히 다 작성해야 한다.
    * 2. SQL에 의존적인 개발을 하게 된다.
    * 3. 패러다임 불일치 문제 (상속, 연관관계, 객체 그래프 탐색)
    * 4. 동일성 보장
    * */

    /* --------------------------------- DB 접속에 관한 준비 -------------------------------- */
    private Connection con;

    /* 모든 @Test 메소드가 동작하기 전에 실행될 내용 */
    @BeforeEach
    public void setConnection() throws SQLException, ClassNotFoundException {

        /* DB 접속 관련 정보 */
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "C##GREEDY";
        String password = "GREEDY";

        /* 사용할 드라이버 동적 로드 */
        Class.forName(driver);

        /* Connection 생성 */
        con = DriverManager.getConnection(url, user, password);
    }

    /* 모든 @Test 메소드가 동작한 이후에 실행될 내용 */
    @AfterEach
    public void closeConnection() throws SQLException {

        con.close();
    }

    /* 1. 애플리케이션과 데이터베이스 중간에서 SQL과 JDBC API 사이에서 데이터를 변환해주는 작업을 개발자가 직접 해주어야 한다.
    *     너무 많은 코드를 작성해야 하고, 반복되는 코드를 계속 사용해야 하는 문제점이 있다.
    *  */
    /* 회원 정보 조회 */
    @Test
    public void 직접_SQL을_작성하여_메뉴_조회하는_기능_테스트() throws SQLException {

        //given
        /* 쿼리문을 직접 작성해야 한다. */
        String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS FROM TBL_MENU";

        //when
        /* DB 연결이 필요한 경우 매번 JDBC API 코드를 반복적으로 사용해야 한다. */
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        /* 조회한 데이터를 자바 객체로 개발자가 직접 변환을 해 주어야 한다. */
        List<Menu> menuList = new ArrayList<>();
        while(rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menu.setMenuPrice(rset.getInt("MENU_PRICE"));
            menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
            menu.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));

            menuList.add(menu);
        }

        rset.close();
        stmt.close();

        //then
        assertNotNull(menuList);
        menuList.forEach(menu -> System.out.println(menu));
    }

    @Test
    public void 직접_SQL을_작성하여_메뉴_추가하는_기능_테스트() throws SQLException {

        //given
        Menu menu = new Menu();
        menu.setMenuName("멸치알쉐이크");
        menu.setMenuPrice(10000);
        menu.setCategoryCode(9);
        menu.setOrderableStatus("Y");

        String query = "INSERT INTO TBL_MENU(MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS) VALUES(SEQ_MENU_CODE.NEXTVAL, ?, ?, ?, ?)";

        //when
        /* insert 역시 반복되는 JDBC API 코드와 데이터 변환 작업등을 개발자가 직접 해 주어야 한다. */
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, menu.getMenuName());
        pstmt.setInt(2, menu.getMenuPrice());
        pstmt.setInt(3, menu.getCategoryCode());
        pstmt.setString(4, menu.getOrderableStatus());

        int result = pstmt.executeUpdate();

        pstmt.close();

        //then
        assertEquals(1, result);
    }

    /* 만약 데이터베이스가 아닌 자바 컬렉션에 데이터를 보관하거나 꺼내오는 방식이면 어떨까?
    * list.add(menu);
    * list.get(1);
    *
    * 컬렉션 API에서 제공하는 컬렉션에 값을 추가하거나 꺼내오는 기능을 이용하면 매우 간단해진다.
    * JPA는 DAO 계층에서 지루하고 반복되는 JDBC API를 획기적으로 줄여주며 이러한 문제를 해결했다.
    * */

    /* ------------------------------------------------------------------------------------ */

    /* 2. SQL에 의존적인 개발을 하게 된다.
    *     이는 요구사항의 변경에 따라 애플리케이션의 수정이 SQL의 수정으로도 이어져야 하며,
    *     이러한 수정 영향을 미치는 현상은 오류를 발생시킬 가능성도 있지만 유지보수성에도 악영향을 미친다.
    *     또한 객체를 사용할 때 SQL에 의존하게 되면 객체에 값이 무엇이 들어있는지 확인해보기 위해 SQL을 매번 살펴봐야 한다.
    * */

    /* 2-1. 애플리케이션과 SQL의 의존성 확인 */
    @Test
    public void 조회_항목_변경에_따른_의존성_확인_테스트() throws SQLException {

        //given
        /* 최초 요구사항은 메뉴코드와 메뉴이름을 조회하는 것이다. */
        String query = "SELECT MENU_CODE, MENU_NAME FROM TBL_MENU";

        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menuList = new ArrayList<>();
        while(rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));

            menuList.add(menu);
        }

        rset.close();
        stmt.close();

        //then
        assertNotNull(menuList);
        menuList.forEach(menu -> System.out.println(menu));

        /* 현재 요구를 만족하는 프로그램을 개발 완료했다.
        * 하지만 메뉴의 가격까지 함께 조회를 해달라는 요구사항 변경이 있었고,
        * 이를 반영하기 위해 애플리케이션에서의 수정만으로 해결을 할 수 없는 상황이다.
        * 즉 SQL에 MENU_PRICE를 조회하는 부분도 함께 수정이 되어야 한다.
        * 이것을 SQL에 의존적으로 개발한다고 한다.
        * */
    }

    /* 2-2. 연관된 객체 문제 */
    /* 메뉴에 해당하는 카테고리의 이름을 함께 조회해야 한다는 요구사항이 변경되었다.
    *  해당 요구사항을 반영하기 위해 Menu 클래스를 수정해야 하지만 MenuAndCategory라는 클래스를 추가하여 확인해보자
    * */
    @Test
    public void 연관된_객체_문제_테스트() throws SQLException {

        //given
        String query =
                "SELECT A.MENU_CODE, A.MENU_NAME, A.MENU_PRICE, B.CATEGORY_CODE, B.CATEGORY_NAME, A.ORDERABLE_STATUS " +
                "FROM TBL_MENU A " +
                "JOIN TBL_CATEGORY B ON(A.CATEGORY_CODE = B.CATEGORY_CODE)";

        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<MenuAndCategory> menuAndCategoryList = new ArrayList<>();
        while(rset.next()) {
            MenuAndCategory menuAndCategory = new MenuAndCategory();
            menuAndCategory.setMenuCode(rset.getInt("MENU_CODE"));
            menuAndCategory.setMenuName(rset.getString("MENU_NAME"));
            menuAndCategory.setMenuPrice(rset.getInt("MENU_PRICE"));
//            menuAndCategory.setCategory(new Category(rset.getInt("CATEGORY_CODE"), rset.getString("CATEGORY_NAME")));
            menuAndCategory.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));

            menuAndCategoryList.add(menuAndCategory);
        }

        rset.close();
        stmt.close();

        //then
        assertNotNull(menuAndCategoryList);
        menuAndCategoryList.forEach(menuAndCategory -> System.out.println(menuAndCategory));

        /* 만약 SQL문이 tbl_category 테이블을 조인하지 않았다면??
        * -> category_code와 category_name은 부적합한 열 식별자라는 오류가 난다.
        *
        *  만약 SQL문에서 tbl_category 테이블은 조인했지만 select절에 category_code와 category_name컬럼이 누락되었다면?
        *  -> JDBC 코드에서 category_code와 category_name은 부적합한 열 식별자 라는 오류가 난다.
        *
        * 만약 SQL문은 정상적으로 반영했지만 데이터 변환 시 setCategory()가 누락된다면??
        *  -> category는 null이 되기 때문에 category를 참조하려는 쪽에서 NullPointerException이 발생한다.
        *  (즉 사용측에서 NPE 발생 가능성으로 신뢰할 수 없다.)
        * */
    }

    /* JPA는 데이터베이스에 저장하고 사용할 때 개발자가 직접 SQL문을 작성하지 않는다.
    *  JPA가 제공하는 API를 사용하면 내부에서 SQL을 생성해서 동작을 시킨다.
    *  따라서 SQL에 의존적이지 않게 된다. (의존하는 것은 직접 사용하지 않으면 의존성이 낮아지게 된다.)
    *
    *  또한 JPA를 사용하여 연관관계 객체를 매핑 설정 해두면 항시 연관된 객체도 함께 조회하기 때문에 사용하는 측에서 신뢰하고 사용할 수 있다.
    * (NPE 발생하지 않음을 신뢰할 수 있음)
    *  */

    /* ------------------------------------------------------------------------------------------------------- */

    /* 3. 패러다임 불일치의 문제가 발생할 수 있다.
    *  관계형 데이터 베이스는 데이터 중심으로 구조화 되어 있고, 집합적인 사고를 요구한다.
    *  그리고 객체지향에서 이야기하는 추상화, 상속, 다형성 같은 개념이 존재하지 않는다.
    *  지향하는 목적 자체가 다르기 때문에 이를 표현하는 방법이 다르고, 그렇기 때문에 객체 구조를 테이블 구조에 저장하는데 한계가 있다.
    *  이런 객체과 관계형 데이터베이스 사이에 패러다임 불일치 문제를 해결하는데 너무 많은 시간과 코드가 소비된다.
    * */

    /* 3-1. 상속 문제
    *  객체지향언어의 상속 개념과 유사한 것이 데이터베이스의 서브타입엔터티 이다.
    *  유사한 것 같지만 다른 부분은 데이터베이스의 상속은 상속 개념을 데이터로 추상화 하여 슈퍼타입과 서브타입으로 구분하고,
    *  슈퍼타입의 모든 속성들을 서브타입이 공유하지 못하며 물리적으로도 다른 테이블로 분리가 된 형태이다. (설계에 따라 속성으로 추가되기도 함)
    *  두 개 서로 다른 테이블을 조회하기 위해서는 공유하는(FK) 컬럼을 이용해 JOIN을 해서 사용해야 한다.
    *  하지만 객체지향의 상속은 슈퍼타입의 속성을 공유해서 사용하기 때문에 여기서 패러다임 불일치 현상이 발생하게 된다.
    *  또한 insert 시에는 각 테이블에 insert구문을 따로따로 실행시켜야 한다.
    * */

    /* 법인 회원과 일반 회원이라는 두 가지 회원을 추상화 하여 회원이라는 슈퍼타입 엔터티를 도출하고
    *  서브타입 엔터티로 법인과 일반회원이 존재하는 형태인 경우 (그림으로 설명)
    *
    * JDBC API를 이용하면...
    * INSERT INTO 회원 ...
    * INSERT INTO 법인회원 ...
    *
    * JPA를 이용하여 상속을 구현한 경우
    * entityManager.persist(법인회원);
    *
    * JPA는 상속과 관련된 패러다임의 불일치 문제를 개발자 대신에 해결해준다.
    * 마치 컬렉션에 객체를 저장하듯 JPA에게 객체만 저장하면 된다.
    * */

    /* 3-2. 연관관계
    *  객체지향에서 말하는 가지고 있는(assosication 연관 관계, 혹은 collection 연관 관계)경우 데이터베이스의 저장 구조와는 다른 형태이다.
    *  데이터 베이스 테이블에 맞춘 객체 모델을 보면 아래와 같은 형태이다.
    *
    * public class Menu {
    *     private int menuCode;
    *     private String menuName;
    *     private int menuPrice;
    *     private int categoryCode;
    *     private String orderableStatus;
    * }
    *
    * public class Category {
    *     private int categoryCode;
    *     private String categoryName;
    * }
    *
    * 하지만 객체지향 언어에서는 FK를 이용해 다른 객체를 참조하지 않는다.
    * 객체지향에 어울리는 구조로 생각해보면 아래와 같은 형태가 된다.
    *
    * public class Menu {
    *     private int menuCode;
    *     private String menuName;
    *     private int menuPrice;
    *     private Category category;
    *     private String orderableStatus;
    * }
    *
    * 따라서 이러한 패러다임 불일치 현상을 없애기 위한 개발자의 많은 노력이 필요하게 된다.
    * 즉, 조인한 컬럼값들을 맞는 객체의 속성에 값을 대입하고 연관관계에 있는 객체도 속성으로 넣어주어야 한다.
    * 만약 이러한 관계설정이 누락되는 경우 NPE가 발생할 가능성이 생기게 된다.
    *
    * Menu menu = new Menu();
    * Category category = new Category();
    *
    * menu.setCategory(category);       //메뉴와 카테고리의 관계 설정.
    *
    * 하지만 JPA에서는 정말 간단하게 이 문제가 해결된다.
    * Menu menu = entityManager.find(Menu.class, menuCode);
    * Category category = menu.getCategory();  <- 연관객체를 함께 조인해서 조회하는 것이 보장된다. (NPE 발생하지 않음)
    * */

    /* 3-3. 객체 그래프 탐색
    *  위와 같이 연관관계가 연이어서 여러 개의 객체간의 복잡한 관계가 형성이 된 경우에는 그 문제가 더 발생할 가능성이 커진다.
    *
    *  //이게 NPE 발생하는지 확인하기 위해서는 SQL부터 확인해야 하고 JDBC API 코드의 데이터 변환 부분도 살펴봐야 한다.
    *  Category orderedCategory = order.getMenu().getCategory();
    *
    *  하지만 JPA에서는 같은 방식으로 사용하더라도 category객체가 반드시 있음을 보장한다.
    *  즉, 연관관계의 객체를 신뢰하고 마음껏 객체 그래프를 탐색하며 조회할 수 있다.
    *
    * (그렇다고 모든 테이블을 다 조회해서 persistence context가 가지는 개념이 아닌
    *  실제 그 객체를 사용할 때 조회를 하는 지연 로딩 기법을 사용한다.
    *  지연 로딩은 뒷부분에서 다시 다루게 될 예정이다.)
    *  */

    /* 4. 동일성 보장
    *  객체의 비교는 동일성 비교와 동등성 비교라는 두 가지 방법으로 구분이 된다.
    *  JDBC를 이용하여 조회한 두 개의 Menu객체는 동일한 로우를 조회하더라도 같은 값을 가지는 동등성을 가지지만 동일성을 가지지는 않는다.
    *  반면 JPA를 이용하여 조회한 두 개의 Menu객체는 동일한 로우를 조회하는 경우 동일성을 보장하게 된다. (단순 == 비교 가능해짐)
    *  데이터베이스르 로우는 인스턴스이다. 즉, 인스턴스는 몇 번 조회해도 동일성을 가져야 한다.
    *  동등성을 가지는 객체를 동일성을 가지게 하기 위해 equals()와 hashcode()를 재정의 해서 사용할 수 있다.
    *  */
    @Test
    public void JDBC_API_동등성_확인_테스트() throws SQLException {

        //given
        String query = "SELECT MENU_CODE, MENU_NAME FROM TBL_MENU WHERE MENU_CODE = 12";

        //when
        Statement stmt1 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        Menu menu1 = null;
        while(rset1.next()) {
            menu1 = new Menu();
            menu1.setMenuCode(rset1.getInt("MENU_CODE"));
            menu1.setMenuName(rset1.getString("MENU_NAME"));
        }

        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt1.executeQuery(query);

        Menu menu2 = null;
        while(rset2.next()) {
            menu2 = new Menu();
            menu2.setMenuCode(rset2.getInt("MENU_CODE"));
            menu2.setMenuName(rset2.getString("MENU_NAME"));
        }

        rset1.close();
        rset2.close();
        stmt1.close();
        stmt2.close();

        //then
        /* 같은 값을 가지지만(동등성 o) 서로 다른 주소를 가진다(동일성 x) */
        assertFalse(menu1 == menu2);
        assertEquals(menu1.getMenuName(), menu2.getMenuName());

        System.out.println("menu1 = " + menu1);
        System.out.println("menu2 = " + menu2);
    }

    /* 하지만 JPA로 구현하는 경우 동일 비교가 가능해진다.
    * Menu menu1 = entityManager.find(Menu.class, 12);
    * Menu menu2 = entityManager.find(Menu.class, 12);
    *
    * menu1 == menu2;    //true
    *  */

    /* 객체 모델과 관계형 데이터베이스 모델은 지향하는 패러다임이 서로 다르다.
    * 더 어려운 문제는 객체 지향 애플리케이션 답게 정교한 객체 모델링을 할수록 패러다임의 불일치 문제가 더 커진다는 점이다.
    * 이는 결국 객체 모델링은 힘을 잃고 점점 데이터 중심의 모델로 변해간다.
    * JPA는 패러다임의 불일치 문제를 해결 해주고 정교한 객체 모델링 유지하게 도와준다.
    * */
}
