package com.greedy.section03.primarykey.subsection02.table;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity(name="sequence_table_member")
@Table(name="TBL_MEMBER_SECTION03_SUBSECTION02")
@TableGenerator(
		name="MEMBER_SEQ_TABLE_GENERATOR",
		table="TBL_MY_SEQUENCES",
		pkColumnValue="MY_SEQ_MEMBER_NO",	// PK 컬럼의 이름 지정
		// pkColumnName : 시퀀스 컬럼명 (기본 값은 SEQUENCE_NAME)
		// valueColumnName : 시퀀스 값 컬럼명 (기본 값은 NEXT_VAL)
		allocationSize=1
)
public class Member {

	@Id
	@Column(name="MEMBER_NO")
	@GeneratedValue(strategy = GenerationType.TABLE, generator="MEMBER_SEQ_TABLE_GENERATOR")
	private int memberNo;
	
	@Column(name="MEMBER_ID")
	private String memberId;
	
	@Column(name="MEMBER_PWD")
	private String memberPwd;
	
	@Column(name="NICKNAME")
	private String nickname;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="ENROLL_DATE")
	private java.sql.Date enrollDate;
	
	@Column(name="MEMBER_ROLE")
	private String memberRole;
	
	@Column(name="STATUS")
	private String status;
	
	public Member() {}

	public Member(int memberNo, String memberId, String memberPwd, String nickname, String phone, String email,
			String address, Date enrollDate, String memberRole, String status) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.nickname = nickname;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.enrollDate = enrollDate;
		this.memberRole = memberRole;
		this.status = status;
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

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public java.sql.Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(java.sql.Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd + ", nickname="
				+ nickname + ", phone=" + phone + ", email=" + email + ", address=" + address + ", enrollDate="
				+ enrollDate + ", memberRole=" + memberRole + ", status=" + status + "]";
	}
	
}
