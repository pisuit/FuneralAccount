package fun.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import fun.customtype.EmployeeStatus;

@Entity
@Table(name="FUNEMPLOYEE")
public class Employee {
	
	@Id
	@Column(name="EMPLOYEEID")
	private Long employeeID;
	
	@Column(name="EMPLOYEECODE")
	private String employeeCode;
	
	@Column(name="ENGFIRSTNAME")
	private String engFirstName;
	
	@Column(name="ENGLASTNAME")
	private String engLastName;
	
	@Column(name="THAIFIRSTNAME")
	private String thaiFirstName;
	
	@Column(name="THAILASTNAME")
	private String thaiLastName;
	
	@Column(name="SEX")
	private String sex;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private EmployeeStatus status;
	
	@ManyToOne
	@JoinColumn(name="NAMEPREFIX_ID", referencedColumnName="ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private NamePrefix namePrefix;
	
	public Long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEngFirstName() {
		return engFirstName;
	}
	public void setEngFirstName(String engFirstName) {
		this.engFirstName = engFirstName;
	}
	public String getEngLastName() {
		return engLastName;
	}
	public void setEngLastName(String engLastName) {
		this.engLastName = engLastName;
	}
	public String getThaiFirstName() {
		return thaiFirstName;
	}
	public void setThaiFirstName(String thaiFirstName) {
		this.thaiFirstName = thaiFirstName;
	}
	public String getThaiLastName() {
		return thaiLastName;
	}
	public void setThaiLastName(String thaiLastName) {
		this.thaiLastName = thaiLastName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public NamePrefix getNamePrefix() {
		return namePrefix;
	}
	public void setNamePrefix(NamePrefix namePrefix) {
		this.namePrefix = namePrefix;
	}
	public EmployeeStatus getStatus() {
		return status;
	}
	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}
}
