package com.greedy.section06.compositekey.subsection02.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity(name="idclass_member")
@Table(name="TBL_MEMBER_SECTION06_SUBSECTION02")
@IdClass(MemberPK.class)
public class Member {
	
	@Id
	@Column(name="MEMBER_NO")
	private int memberNo;
	
	@Id
	@Column(name="MEMBER_ID")
	private String memberId;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="ADDRESS")
	private String address;
	
	public Member() {}

	public Member(int memberNo, String memberId, String phone, String address) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.phone = phone;
		this.address = address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", phone=" + phone + ", address=" + address
				+ "]";
	}

	
	

}
