package fun.reportmodel;

import java.math.BigDecimal;
import java.util.Date;

public class MonthlyModel {
	private String name;
	private String number;
	private String deadName;
	private String deadInfo;
	private String deadDate;
	private String currentBalance;
	private String mainMemberName;
	
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
	public String getDeadName() {
		return deadName;
	}
	public void setDeadName(String deadName) {
		this.deadName = deadName;
	}
	public String getDeadInfo() {
		return deadInfo;
	}
	public void setDeadInfo(String deadInfo) {
		this.deadInfo = deadInfo;
	}
	public String getDeadDate() {
		return deadDate;
	}
	public void setDeadDate(String deadDate) {
		this.deadDate = deadDate;
	}
	public String getMainMemberName() {
		return mainMemberName;
	}
	public void setMainMemberName(String mainMemberName) {
		this.mainMemberName = mainMemberName;
	}
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
}
