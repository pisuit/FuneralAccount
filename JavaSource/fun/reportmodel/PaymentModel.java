package fun.reportmodel;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentModel {
	private String name;
	private String number;
	private Date transactionDate;
	private String transactionType;
	private BigDecimal transactionAmount;
	private BigDecimal transactionRemain;
	private String paidForName;
	private String paidForNumber;
	private BigDecimal currentBalance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public BigDecimal getTransactionRemain() {
		return transactionRemain;
	}
	public void setTransactionRemain(BigDecimal transactionRemain) {
		this.transactionRemain = transactionRemain;
	}
	public String getPaidForName() {
		return paidForName;
	}
	public void setPaidForName(String paidForName) {
		this.paidForName = paidForName;
	}
	public String getPaidForNumber() {
		return paidForNumber;
	}
	public void setPaidForNumber(String paidForNumber) {
		this.paidForNumber = paidForNumber;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
}
