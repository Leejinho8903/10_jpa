package com.greedy.section06.compositekey.subsection01.embedded;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="embedded_member")
@Table(name="TBL_MEMBER_SECTION06_SUBSECTION01")
public class Member {
	
	@EmbeddedId
	private MemberPK memberPK;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="ADDRESS")
	private String address;
	
	public Member() {}

	public Member(MemberPK memberPK, String phone, String address) {
		super();
		this.memberPK = memberPK;
		this.phone = phone;
		this.address = address;
	}

	public MemberPK getMemberPK() {
		return memberPK;
	}

	public void setMemberPK(MemberPK memberPK) {
		this.memberPK = memberPK;
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
		return "Member [memberPK=" + memberPK + ", phone=" + phone + ", address=" + address + "]";
	}
	
	

}
