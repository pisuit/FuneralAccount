package fun.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="FUNPAYMENT")
public class Payment {
	
	@Id
	@Column(name="ID")
	private Long ID;
	
	@Column(name="YEAR")
	private int year;
	
	@Column(name="MONTH")
	private int month;
	
	@Column(name="REGISTERFEE")
	private BigDecimal registerFee = new BigDecimal("0.00");
	
	@Column(name="PAIDAMOUNT")
	private BigDecimal paidAmount = new BigDecimal("0.00");
	
	@Column(name="RECEIVEDAMOUNT")
	private BigDecimal receivedAmount = new BigDecimal("0.00");
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID" ,referencedColumnName="ID")
	private Member member;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public BigDecimal getRegisterFee() {
		return registerFee;
	}
	public void setRegisterFee(BigDecimal registerFee) {
		this.registerFee = registerFee;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
}
