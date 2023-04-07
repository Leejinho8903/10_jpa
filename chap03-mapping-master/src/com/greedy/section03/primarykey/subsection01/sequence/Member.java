package com.greedy.section03.primarykey.subsection01.sequence;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name="sequence_member")
@Table(name="TBL_MEMBER_SECTION03_SUBSECTION01")
@SequenceGenerator(
		name="MEMBER_SEQUENCE_GENERATOR", 	//식별자 생성기 이름
		sequenceName="SEQ_MEMBER_NO",		//데이터베이스에 등록 되어있는 시퀀스 이름
		//initialValue=5,						//시퀀스의 시작값 설정
		allocationSize=1					
		//기본 값이 50으로 설정 되어 있고 시퀀스 증가와 별개로 메모리에서 식별자를 할당하여 매번 시퀀스를 호출하지 않도록 하는 성능 최적화를 위해 설정 된 값이다.
		//여러 요청이 동시에 접근해서 데이터를 등록할 때 기본 값이 충돌하지 않는 장점(동시성 문제 해결)이 있지만, 시퀀스가 한 번에 많이 증가하는 점을 염두에 두어야 한다.
)
public class Member {

	@Id
	@Column(name="MEMBER_NO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MEMBER_SEQUENCE_GENERATOR")
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
