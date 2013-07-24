package fun.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="FUNCURRENTBALANCE")
@GenericGenerator(strategy="fun.utils.HibernateCurrentTimeIDGenerator", name="IDGENERATOR")
public class CurrentBalance {
	
	@Id
	@GeneratedValue(generator="IDGENERATOR")
	@Column(name="ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")
	private Member member;
	
	@Column(name="CURRENTBALANCE")
	private BigDecimal currentBalance = new BigDecimal("0.00");
	
	@Column(name="RECEIVEDAMOUNT")
	private BigDecimal receivedAmount = new BigDecimal("0.00");
	
	@Column(name="DEBTAMOUNT")
	private BigDecimal debtAmount = new BigDecimal("0.00");
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public BigDecimal getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}
}
