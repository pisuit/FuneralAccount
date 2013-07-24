package fun.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="FUNPAYROLLPAYMENT")
public class PayrollPayment {
	
	@Id
	@Column(name="ID")
	private Long ID;
	
	@Column(name="YEAR")
	private int year;
	
	@Column(name="MONTH")
	private int month;
	
	@Column(name="PAIDAMOUNT")
	private BigDecimal paidAmount = new BigDecimal("0.00");
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_EMPLOYEEID", referencedColumnName="EMPLOYEEID")
	private Employee employee;
	
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
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
