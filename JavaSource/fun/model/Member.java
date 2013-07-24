package fun.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Iterables;

import fun.customtype.MemberStatus;

@Entity
@Table(name="FUNMEMBER")
public class Member {
	
	@Id
	@Column(name="ID")
	private Long ID;
	
	@Column(name="MEMBERNUMBER")
	private int memberNumber;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private MemberStatus status;
	
	@Column(name="BIRTHDATE")
	private Date birthDate;
	
	@Column(name="MEMBERDATE")
	private Date memberDate;
	
	@Column(name="RESIGNDATE")
	private Date resignDate;
	
	@Column(name="SPOUSENAME")
	private String spouseName;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="FUNERALMANAGERNAME")
	private String funeralManagerName;
	
	@Column(name="DECEASEREPORTEDDATE")
	private Date deceaseReportDate;
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_EMPLOYEEID", referencedColumnName="EMPLOYEEID")
	@NotFound(action=NotFoundAction.IGNORE)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="ORDINARYMEMBER_ID", referencedColumnName="ID")
	private Member mainMember;
	
	@ManyToOne
	@JoinColumn(name="PAIDBYMEMBER_ID", referencedColumnName="ID")
	private Member paidMember;
	
	@ManyToOne
	@JoinColumn(name="PAIDBYEMPLOYEE_EMPLOYEEID", referencedColumnName="EMPLOYEEID")
	private Employee paidEmployee;
	
	@OneToMany(mappedBy="member")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<RetirementPayment> payments;;
	
	@OneToMany(mappedBy="paidForMember")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<RetirementPayment> paidForMembers;
	
	@OneToMany(mappedBy="inDebtMember")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Debt> debts;
	
	@OneToMany(mappedBy="member")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Payment> normalPayments;
	
	@OneToOne(mappedBy="member")
	private CurrentBalance balance;
	
	@OneToOne(mappedBy="member")
	private Warning warning;
	
	@Transient
	private BigDecimal receivedAmount = new BigDecimal("0.00");
		
	@Transient
	private BigDecimal totalAmount = new BigDecimal("0.00");
	
	@Transient
	private BigDecimal grandSum = new BigDecimal("0.00");
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public int getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getMemberDate() {
		return memberDate;
	}
	public void setMemberDate(Date memberDate) {
		this.memberDate = memberDate;
	}
	public Date getResignDate() {
		return resignDate;
	}
	public void setResignDate(Date resignDate) {
		this.resignDate = resignDate;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFuneralManagerName() {
		return funeralManagerName;
	}
	public void setFuneralManagerName(String funeralManagerName) {
		this.funeralManagerName = funeralManagerName;
	}
	public Date getDeceaseReportDate() {
		return deceaseReportDate;
	}
	public void setDeceaseReportDate(Date deceaseReportDate) {
		this.deceaseReportDate = deceaseReportDate;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Member getMainMember() {
		return mainMember;
	}
	public void setMainMember(Member mainMember) {
		this.mainMember = mainMember;
	}
	public Member getPaidMember() {
		return paidMember;
	}
	public void setPaidMember(Member paidMember) {
		this.paidMember = paidMember;
	}
	public Employee getPaidEmployee() {
		return paidEmployee;
	}
	public void setPaidEmployee(Employee paidEmployee) {
		this.paidEmployee = paidEmployee;
	}
	public MemberStatus getStatus() {
		return status;
	}
	public void setStatus(MemberStatus status) {
		this.status = status;
	}
	public BigDecimal getReceivedAmount() {
		if(normalPayments.size() > 0){
			receivedAmount =  Iterables.get(normalPayments, 0).getReceivedAmount();
			return receivedAmount;
		} else {
			return receivedAmount;
		}
	}
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setGrandSum(BigDecimal grandSum) {
		this.grandSum = grandSum;
	}
	public List<RetirementPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<RetirementPayment> payments) {
		this.payments = payments;
	}
	public List<RetirementPayment> getPaidForMembers() {
		return paidForMembers;
	}
	public void setPaidForMembers(List<RetirementPayment> paidForMembers) {
		this.paidForMembers = paidForMembers;
	}
	public List<Debt> getDebts() {
		return debts;
	}
	public void setDebts(List<Debt> debts) {
		this.debts = debts;
	}
	public List<Payment> getNormalPayments() {
		return normalPayments;
	}
	public void setNormalPayments(List<Payment> normalPayments) {
		this.normalPayments = normalPayments;
	}
	public CurrentBalance getBalance() {
		return balance;
	}
	public void setBalance(CurrentBalance balance) {
		this.balance = balance;
	}
	public BigDecimal getTotalAmount() {
		if(balance != null){
			totalAmount = balance.getReceivedAmount().add(receivedAmount);
			return totalAmount;
		} else {
			return receivedAmount;
		}
		
	}
	public BigDecimal getGrandSum() {
		if(balance != null){
			grandSum = balance.getReceivedAmount().add(receivedAmount).add(balance.getDebtAmount());
			return grandSum;
		} else {
			return receivedAmount;
		}
		
	}
	public Warning getWarning() {
		return warning;
	}
	public void setWarning(Warning warning) {
		this.warning = warning;
	}
}
