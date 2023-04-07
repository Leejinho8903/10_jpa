package com.greedy.section05.access.subsection01.field;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="field_access_member")
@Table(name="TBL_MEMBER_SECTION05_SUBSECTION01")
/* 1. 클래스 레벨 : 모든 필드에 대해서 적용 */
//@Access(AccessType.FIELD)
public class Member {

	/* 2. 필드 레벨 : 해당 필드의 접근 방식을 필드 접근으로 바꿀 수 있다. 
	 * @Id 어노테이션이 필드 레벨에 존재하는 경우 해당 필드는 @Acess(AccessType.FIELD)이다. 따라서 어노테이션을 생략해도 무방하다. */
	@Id
	@Column(name="MEMBER_NO")
	//@Access(AccessType.FIELD)
	private int memberNo;
	
	@Column(name="MEMBER_ID")
	//@Access(AccessType.FIELD)
	private String memberId;
	
	@Column(name="MEMBER_PWD")
	//@Access(AccessType.FIELD)
	private String memberPwd;
	
	@Column(name="NICKNAME")
	//@Access(AccessType.FIELD)
	private String nickname;
	
	public Member() {}

	public Member(int memberNo, String memberId, String memberPwd, String nickname) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.nickname = nickname;
	}

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

	public String getNickname() {
		
		System.out.println("getNickname()를 이용한 access 확인...");
		
		return nickname;
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
