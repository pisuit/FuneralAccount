package fun.datamodel;

import java.util.Date;

public class DeadMemberDataModel {
	private String memberName;
	private int deadMemberNumber;
	private int memberNumber;
	private String deadMemberName;
	private String deadCause;
	private String relation;
	private Date reportDate;
	private String memberType;
	private String memberStatus;
	private String mainMemberName;
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getDeadMemberNumber() {
		return deadMemberNumber;
	}
	public void setDeadMemberNumber(int deadMemberNumber) {
		this.deadMemberNumber = deadMemberNumber;
	}
	public int getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getDeadMemberName() {
		return deadMemberName;
	}
	public void setDeadMemberName(String deadMemberName) {
		this.deadMemberName = deadMemberName;
	}
	public String getDeadCause() {
		return deadCause;
	}
	public void setDeadCause(String deadCause) {
		this.deadCause = deadCause;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getMainMemberName() {
		return mainMemberName;
	}
	public void setMainMemberName(String mainMemberName) {
		this.mainMemberName = mainMemberName;
	}
}
