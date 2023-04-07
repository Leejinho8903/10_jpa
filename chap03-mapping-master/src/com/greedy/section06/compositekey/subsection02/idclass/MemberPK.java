package com.greedy.section06.compositekey.subsection02.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class MemberPK implements Serializable {

	private int memberNo;
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
