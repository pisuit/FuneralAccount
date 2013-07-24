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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import fun.customtype.DebtStatus;

@Entity
@Table(name="FUNDEBT")
@GenericGenerator(strategy="fun.utils.HibernateCurrentTimeIDGenerator", name="IDGENERATOR")
public class Debt {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="IDGENERATOR")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="PAIDFORMEMBER", referencedColumnName="ID")
	private Member paidForMember;
	
	@ManyToOne
	@JoinColumn(name="INDEBTMEMBER", referencedColumnName="ID")
	private Member inDebtMember;
	
	@Column(name="DEBTAMOUNT")
	private BigDecimal debtAmount = new BigDecimal("0.00");
	
	@Column(name="TRANSACTIONDATE")
	private Date transactionDate;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private DebtStatus debtStatus = DebtStatus.IN_DEBT;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Member getPaidForMember() {
		return paidForMember;
	}
	public void setPaidForMember(Member paidForMember) {
		this.paidForMember = paidForMember;
	}
	public Member getInDebtMember() {
		return inDebtMember;
	}
	public void setInDebtMember(Member inDebtMember) {
		this.inDebtMember = inDebtMember;
	}
	public BigDecimal getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public DebtStatus getDebtStatus() {
		return debtStatus;
	}
	public void setDebtStatus(DebtStatus debtStatus) {
		this.debtStatus = debtStatus;
	}
}
