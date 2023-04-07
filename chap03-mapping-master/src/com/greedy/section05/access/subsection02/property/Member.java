package com.greedy.section05.access.subsection02.property;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="property_access_member")
@Table(name="TBL_MEMBER_SECTION05_SUBSECTION02")
//@Access(AccessType.PROPERTY)
/* 클래스 레벨에 @Access(AccessType.PROPERTY) 를 작성할 때 주의할 점은
 * @Id 어노테이션이 필드에 있다면 엔티티 매니저 자체를 생성하지 못하기 때문에 @Id 어노테이션을 
 * getter 메소드 위로 옮겨야 한다. */
public class Member {

	@Id
	@Column(name="MEMBER_NO")
	private int memberNo;
	
	@Column(name="MEMBER_ID")
	private String memberId;
	
	@Column(name="MEMBER_PWD")
	private String memberPwd;
	
	@Column(name="NICKNAME")
	private String nickname;
	
	public Member() {}

	public Member(int memberNo, String memberId, String memberPwd, String nickname) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.nickname = nickname;
	}

	//@Id
	public int getMemberNo() {
		
		System.out.println("getMemberNo()를 이용한 access 확인...");
		
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		
		System.out.println("getMemberId()를 이용한 access 확인...");
		
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPwd() {
		
		System.out.println("getMemberPwd()를 이용한 access 확인...");
		
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	/* 3. 메소드 레벨 - 해당 메소드의 접근 방식만 변경한다. */
	@Access(AccessType.PROPERTY)
	public String getNickname() {
		
		System.out.println("getNickname()를 이용한 access 확인...");
		
		return nickname + "님";
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd + ", nickname="
				+ nickname + "]";
	}
	
	

	
}
