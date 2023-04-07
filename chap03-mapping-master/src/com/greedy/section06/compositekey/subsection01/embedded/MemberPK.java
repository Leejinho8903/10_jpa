package com.greedy.section06.compositekey.subsection01.embedded;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/* 임베드 될 수 있는 복합 키 타입을 지정할 때 사용하는 어노테이션 */
@Embeddable
public class MemberPK implements Serializable {

	@Column(name="MEMBER_NO")
	private int memberNo;
	
	@Column(name="MEMBER_ID")
	private String memberId;
	
	public MemberPK() {}

	public MemberPK(int memberNo, String memberId) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "MemberPK [memberNo=" + memberNo + ", memberId=" + memberId + "]";
	}
	
	
	
	
}
