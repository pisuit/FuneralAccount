package fun.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import fun.customtype.TransactionType;

@Entity
@Table(name="FUNRETIREMENTPAYMENT")
@GenericGenerator(strategy="fun.utils.HibernateCurrentTimeIDGenerator", name="IDGENERATOR")
public class RetirementPayment {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="IDGENERATOR")
	private Long ID;
	
	@Column(name="TRANSACTIONDATE")
	private Date transactionDate;
	
	@Column(name="TRANSACTIONTYPE")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(name="TRANSACTIONAMOUNT")
	private BigDecimal transactionAmount = new BigDecimal("0.00");
	
	@Column(name="AVAILABLEAMOUNT")
	private BigDecimal availableAmount = new BigDecimal("0.00");
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="PAIDFORMEMBER" ,referencedColumnName="ID")
	private Member paidForMember;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Member getPaidForMember() {
		return paidForMember;
	}
	public void setPaidForMember(Member paidForMember) {
		this.paidForMember = paidForMember;
	}
}
