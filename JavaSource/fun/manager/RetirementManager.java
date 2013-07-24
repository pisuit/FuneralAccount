package fun.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

import com.google.common.collect.Lists;

import fun.controller.MemberController;
import fun.controller.PaymentController;
import fun.customtype.DebtStatus;
import fun.customtype.EmployeeStatus;
import fun.customtype.MemberStatus;
import fun.customtype.TransactionType;
import fun.model.CurrentBalance;
import fun.model.Debt;
import fun.model.Employee;
import fun.model.Member;
import fun.model.Payment;
import fun.model.RetirementPayment;
import fun.model.Warning;

@ManagedBean(name="retirement")
@ViewScoped
public class RetirementManager {
	private MemberController memberController = new MemberController();
	private ArrayList<Member> memberList = new ArrayList<Member>();
	private ArrayList<Member> subMemberList = new ArrayList<Member>();
	private String firstName;
	private String lastName;
	private String memberNumber;
	private Member selectedMember;
	private Member selectedMember2;
	private ArrayList<SelectItem> memberStatusFilterOptions = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> employeeStatusFilterOptions = new ArrayList<SelectItem>();
	private RetirementPayment editPayment;
	private boolean isMainMember = true;
	private boolean isSubMember = false;
	private String subTableHeader = "ตารางแสดงรายชื่อสมาชิกสมทบ";
	private BigDecimal addAmount = new BigDecimal("0.00");
	private PaymentController paymentController = new PaymentController();
	private BigDecimal availableAmount = null;
	private ArrayList<RetirementPayment> paymentList = new ArrayList<RetirementPayment>();
	private ArrayList<Debt> debtList = new ArrayList<Debt>();
	private boolean isFirstTable = true;
	private Date fromDate;
	private Date toDate;
	private Date payDate;
	private Date debtFromDate;
	private Date debtToDate;
	private boolean isShowAll = false;
	private List<Member> debtMemberList = new ArrayList<Member>();
	private String name;
	private boolean includedDead = false;
	
	public ArrayList<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}
	public String getSubTableHeader() {
		return subTableHeader;
	}
	public void setSubTableHeader(String subTableHeader) {
		this.subTableHeader = subTableHeader;
	}
	public boolean isIncludedDead() {
		return includedDead;
	}
	public void setIncludedDead(boolean includedDead) {
		this.includedDead = includedDead;
	}
	public ArrayList<SelectItem> getEmployeeStatusFilterOptions() {
		return employeeStatusFilterOptions;
	}
	public void setEmployeeStatusFilterOptions(
			ArrayList<SelectItem> employeeStatusFilterOptions) {
		this.employeeStatusFilterOptions = employeeStatusFilterOptions;
	}
	public Member getSelectedMember2() {
		return selectedMember2;
	}
	public void setSelectedMember2(Member selectedMember2) {
		this.selectedMember2 = selectedMember2;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;	
	}
	public boolean isFirstTable() {
		return isFirstTable;
	}
	public void setFirstTable(boolean isFirstTable) {
		this.isFirstTable = isFirstTable;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;	
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public boolean isMainMember() {
		return isMainMember;
	}
	public void setMainMember(boolean isMainMember) {
		this.isMainMember = isMainMember;
	}
	public boolean isShowAll() {
		return isShowAll;
	}
	public void setShowAll(boolean isShowAll) {
		this.isShowAll = isShowAll;
	}
	public Date getDebtFromDate() {
		return debtFromDate;
	}
	public void setDebtFromDate(Date debtFromDate) {
		this.debtFromDate = debtFromDate;
	}
	public Date getDebtToDate() {
		return debtToDate;
	}
	public void setDebtToDate(Date debtToDate) {
		this.debtToDate = debtToDate;
	}
	public boolean isSubMember() {
		return isSubMember;
	}
	public void setSubMember(boolean isSubMember) {
		this.isSubMember = isSubMember;
	}
	public ArrayList<Member> getSubMemberList() {
		return subMemberList;
	}
	public void setSubMemberList(ArrayList<Member> subMemberList) {
		this.subMemberList = subMemberList;
	}
	public BigDecimal getAddAmount() {
		return addAmount;
	}
	public void setAddAmount(BigDecimal addAmount) {
		this.addAmount = addAmount;
	}
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	public List<Member> getDebtMemberList() {
		return debtMemberList;
	}
	public void setDebtMemberList(List<Member> debtMemberList) {
		this.debtMemberList = debtMemberList;
	}
	public ArrayList<Debt> getDebtList() {
		return debtList;
	}
	public void setDebtList(ArrayList<Debt> debtList) {
		this.debtList = debtList;
	}
	public ArrayList<RetirementPayment> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(ArrayList<RetirementPayment> paymentList) {
		this.paymentList = paymentList;
	}
	public RetirementPayment getEditPayment() {
		return editPayment;
	}
	public void setEditPayment(RetirementPayment editPayment) {
		this.editPayment = editPayment;
	}
	public ArrayList<SelectItem> getMemberStatusFilterOptions() {
		return memberStatusFilterOptions;
	}
	public void setMemberStatusFilterOptions(
			ArrayList<SelectItem> memberStatusFilterOptions) {
		this.memberStatusFilterOptions = memberStatusFilterOptions;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Member getSelectedMember() {
		return selectedMember;
	}
	public void setSelectedMember(Member selectedMember) {
		this.selectedMember = selectedMember;
	}
	public RetirementManager(){
		fromDate = new DateTime().now().dayOfMonth().withMinimumValue().toDateMidnight().toDate();
		toDate = new DateTime().now().dayOfMonth().withMaximumValue().toDateMidnight().toDate();
		debtFromDate = new DateTime().now().dayOfMonth().withMinimumValue().toDateMidnight().toDate();
		debtToDate = new DateTime().now().dayOfMonth().withMaximumValue().toDateMidnight().toDate();
		payDate = new DateTime().now().toDateMidnight().toDate();
		
		createMemberStatusFilterOptions();
		createEmployeeStatusFilterOptions();
	}
	
	public void searchMember(){
		int number = 0;
		if(memberList != null) memberList.clear();
		
		if(memberNumber != ""){
			number = Integer.parseInt(memberNumber);
		}
			
//		List<Member> list =  memberController.searchRetirementMember(firstName, lastName, number, isRetire, isAlive);
//		for(Member member : list){
//			RetirementPayment latest = paymentController.getLatestPayment(member);
//			if(latest != null){
//				member.setAvailableAmonut(latest.getAvailableAmount());
//			}
//			memberList.add(member);
//		}
		memberList.addAll(memberController.searchRetirementMember(firstName, lastName, number, includedDead));
		clearData();
	}
	
	public void createMemberList(){
		int number = 0;	
		if(memberNumber != ""){
			number = Integer.parseInt(memberNumber);
		}
		
		if(isFirstTable == true){			
			if(memberList != null) memberList.clear();
			memberList.addAll(memberController.searchRetirementMember(firstName, lastName, number, includedDead));
		} else {			
			if(subMemberList != null) subMemberList.clear();
			if(memberList != null) memberList.clear();
			if(selectedMember.getEmployee() != null){
				subMemberList.addAll(memberController.getSubMember(selectedMember));
			} else {
				subMemberList.addAll(memberController.getMainMember(selectedMember));
			}
			memberList.addAll(memberController.searchRetirementMember(firstName, lastName, number, includedDead));
		}
	}
	
	private void createMemberStatusFilterOptions(){
		if(memberStatusFilterOptions != null) memberStatusFilterOptions.clear();
		
		memberStatusFilterOptions.add(new SelectItem("", "All"));
		memberStatusFilterOptions.add(new SelectItem(MemberStatus.M.getID(), MemberStatus.M.getID()));
		memberStatusFilterOptions.add(new SelectItem(MemberStatus.D.getID(), MemberStatus.D.getID()));
//		for(MemberStatus status : MemberStatus.values()){
//			memberStatusFilterOptions.add(new SelectItem(status.getID(), status.getID()));
//		}
	}
	
	private void createEmployeeStatusFilterOptions(){
		if(employeeStatusFilterOptions != null) employeeStatusFilterOptions.clear();
		
		employeeStatusFilterOptions.add(new SelectItem("", "All"));
		employeeStatusFilterOptions.add(new SelectItem("null", "NON-EMPLOYEE"));
		employeeStatusFilterOptions.add(new SelectItem(EmployeeStatus.RETIREMENT.getID(), EmployeeStatus.RETIREMENT.getID()));
		employeeStatusFilterOptions.add(new SelectItem(EmployeeStatus.RESIGNATION.getID(), EmployeeStatus.RESIGNATION.getID()));
//		for(EmployeeStatus status : EmployeeStatus.values()){
//			employeeStatusFilterOptions.add(new SelectItem(status.getID(), status.getID()));
//		}
	}
	
	public void createSubmember(){
		name = selectedMember.getName();
		isFirstTable = true;
		if(subMemberList != null) subMemberList.clear();
		
		if(selectedMember.getMainMember() == null){			
			subTableHeader = "ตารางแสดงรายชื่อสมาชิกสมทบ";
			subMemberList.addAll(memberController.getSubMember(selectedMember));
//			List<Member> list = memberController.getSubMember(selectedMember);
//					for(Member member : list){
//						RetirementPayment latest = paymentController.getLatestPayment(member);
//						if(latest != null){
//							member.setAvailableAmonut(latest.getAvailableAmount());
//						}
//						subMemberList.add(member);
//					}
		} else {
			subTableHeader = "ตารางแสดงรายชื่อสมาชิกสามัญ";
			subMemberList.addAll(memberController.getMainMember(selectedMember));
//			List<Member> list = memberController.getMainMember(selectedMember);
//					for(Member member : list){
//						RetirementPayment latest = paymentController.getLatestPayment(member);
//						if(latest != null){
//							member.setAvailableAmonut(latest.getAvailableAmount());
//						}
//						subMemberList.add(member);
//					}
		}
		
		findLatestPayment();
		createBalanceMoveMentList();
		createDebtList();
	}
	
	public void findLatestPayment(){
//		RetirementPayment latest = paymentController.getLatestPayment(selectedMember);
//		
//		if(latest != null){
//			availableAmount = latest.getAvailableAmount();
//		} else {
//			availableAmount = new BigDecimal("0.00");
//			System.out.println("latest = null");
//		}
		
		if(selectedMember.getBalance() != null){
			availableAmount = selectedMember.getBalance().getCurrentBalance();
		} else {
			CurrentBalance balance =  paymentController.getCurrentBalance(selectedMember);
			if(balance != null){
				availableAmount = balance.getCurrentBalance();
			} else {
				availableAmount = null;
			}		
		}
	}
	
	public void findLatestPayment2(){
		name = selectedMember2.getName();
		isFirstTable = false;
//		RetirementPayment latest = paymentController.getLatestPayment(selectedMember2);
//		
//		if(latest != null){
//			availableAmount = latest.getAvailableAmount();
//		} else {
//			availableAmount = new BigDecimal("0.00");
//			System.out.println("latest = null");
//		}
		
		if(selectedMember2.getBalance() != null){
			availableAmount = selectedMember2.getBalance().getCurrentBalance();
		} else {
			CurrentBalance balance =  paymentController.getCurrentBalance(selectedMember2);
			if(balance != null){
				availableAmount = balance.getCurrentBalance();
			} else {
				availableAmount = null;
			}
		}
		
		createBalanceMoveMentList2();
		createDebtList2();
	}
	
	public void createBalanceMoveMentList2(){
		if(paymentList != null) paymentList.clear();
		
		paymentList.addAll(paymentController.getPaymentList(selectedMember2, fromDate, toDate));
	}
	
	public void createBalanceMoveMentList(){
		if(paymentList != null) paymentList.clear();
		
		paymentList.addAll(paymentController.getPaymentList(selectedMember, fromDate, toDate));
	}
	
	public void subBalance(){
		if(isFirstTable == true){
			if(selectedMember.getBalance() != null){
				selectedMember.getBalance().setCurrentBalance(new BigDecimal("0.00"));
				paymentController.saveCurrentBalance(selectedMember.getBalance());
			}
		} else {
			if(selectedMember2.getBalance() != null){
				selectedMember2.getBalance().setCurrentBalance(new BigDecimal("0.00"));
				paymentController.saveCurrentBalance(selectedMember2.getBalance());
			}
		}
		
	}
	
	public void addBalance(){
//		RetirementPayment latest;
		editPayment = new RetirementPayment();
		CurrentBalance balance;
		if(debtMemberList != null) debtMemberList.clear();
		
		if(isFirstTable == true){
			editPayment.setMember(selectedMember);
//			latest = paymentController.getLatestPayment(selectedMember);
			if(selectedMember.getBalance() != null){
				editPayment.setAvailableAmount(selectedMember.getBalance().getCurrentBalance().add(addAmount));
				editPayment.setTransactionType(TransactionType.ADD);
				
				selectedMember.getBalance().setCurrentBalance(selectedMember.getBalance().getCurrentBalance().add(addAmount));
				paymentController.saveCurrentBalance(selectedMember.getBalance());
				
			} else {
				editPayment.setTransactionType(TransactionType.INITIAL);
				editPayment.setAvailableAmount(addAmount);
				
				balance = new CurrentBalance();
				balance.setMember(selectedMember);
				balance.setCurrentBalance(addAmount);
				
				paymentController.saveCurrentBalance(balance);
			}
			
			List<Debt> debtList = paymentController.getActiveDebtList(selectedMember);
			BigDecimal sumDebt = new BigDecimal("0.00");
			if(debtList != null){
				for(Debt debt : Lists.reverse(debtList)){
					sumDebt = sumDebt.add(debt.getDebtAmount());
					if(sumDebt.compareTo(addAmount) <= 0){
						debt.setDebtStatus(DebtStatus.PAID);
						paymentController.addDebt(debt);
						
						CurrentBalance paidForDebt = paymentController.getCurrentBalance(debt.getPaidForMember());
						paidForDebt.setDebtAmount(paidForDebt.getDebtAmount().subtract(debt.getDebtAmount()));
						paidForDebt.setReceivedAmount(paidForDebt.getReceivedAmount().add(debt.getDebtAmount()));
						
						paymentController.saveCurrentBalance(paidForDebt);
						
						debt.getPaidForMember().setBalance(paidForDebt);
						debtMemberList.add(debt.getPaidForMember());
					}
				}
			}
			
			Warning warning = paymentController.getWarning(selectedMember);
			if(warning != null){
				warning.setWarningCount(0);
				warning.setWarnDate1(null);
				warning.setWarnDate2(null);
				warning.setWarnDate3(null);
				
				paymentController.saveWarning(warning);
			}
			
			findLatestPayment();
		} else {
			editPayment.setMember(selectedMember2);
//			latest = paymentController.getLatestPayment(selectedMember2);
			if(selectedMember2.getBalance() != null){
				editPayment.setAvailableAmount(selectedMember2.getBalance().getCurrentBalance().add(addAmount));
				editPayment.setTransactionType(TransactionType.ADD);
				
				selectedMember2.getBalance().setCurrentBalance(selectedMember2.getBalance().getCurrentBalance().add(addAmount));
				paymentController.saveCurrentBalance(selectedMember2.getBalance());
			} else {
				editPayment.setAvailableAmount(addAmount);
				editPayment.setTransactionType(TransactionType.INITIAL);
				
				balance = new CurrentBalance();
				balance.setMember(selectedMember2);
				balance.setCurrentBalance(addAmount);
				
				paymentController.saveCurrentBalance(balance);
			}
			
			List<Debt> debtList = paymentController.getActiveDebtList(selectedMember2);
			BigDecimal sumDebt = new BigDecimal("0.00");
			if(debtList != null){
				for(Debt debt : Lists.reverse(debtList)){
					sumDebt = sumDebt.add(debt.getDebtAmount());
					if(sumDebt.compareTo(addAmount) <= 0){
						debt.setDebtStatus(DebtStatus.PAID);
						paymentController.addDebt(debt);
						
						CurrentBalance paidForDebt = paymentController.getCurrentBalance(debt.getPaidForMember());
						paidForDebt.setDebtAmount(paidForDebt.getDebtAmount().subtract(debt.getDebtAmount()));
						paidForDebt.setReceivedAmount(paidForDebt.getReceivedAmount().add(debt.getDebtAmount()));
						
						paymentController.saveCurrentBalance(paidForDebt);
						
						debt.getPaidForMember().setBalance(paidForDebt);
						debtMemberList.add(debt.getPaidForMember());
					}
				}
			}
			
			Warning warning = paymentController.getWarning(selectedMember2);
			if(warning != null){
				warning.setWarningCount(0);
				warning.setWarnDate1(null);
				warning.setWarnDate2(null);
				warning.setWarnDate3(null);
				
				paymentController.saveWarning(warning);
			}	
			findLatestPayment2();
		}
		
		editPayment.setTransactionAmount(addAmount);
		editPayment.setTransactionDate(payDate);
		
//		if(latest != null){
//			editPayment.setAvailableAmount(latest.getAvailableAmount().add(addAmount));
//			editPayment.setTransactionType(TransactionType.ADD);
//		} else {
//			editPayment.setAvailableAmount(addAmount);
//			editPayment.setTransactionType(TransactionType.INITIAL);
//		}
		
		paymentController.addBalance(editPayment);
		createMemberList();
		addAmount = new BigDecimal("0.00");
		
		if(debtMemberList.size() != 0){
			RequestContext context = RequestContext.getCurrentInstance(); 
			context.execute("nameListDialog.show(); document.getElementById(\"center\").style.zIndex= \"\";");
		}
	}
	
	public void clearData(){
		isFirstTable = true;
		selectedMember = null;
		selectedMember2 = null;
		subTableHeader = "ตารางแสดงรายชื่อสมาชิกสมทบ";
		if(subMemberList != null) subMemberList.clear();
		if(paymentList != null) paymentList.clear();
		availableAmount = null;
		if(debtList != null) debtList.clear();
		name = null;
	}
	
	public void refreshData(){
		firstName = null;
		lastName = null;
		memberNumber = null;
		isFirstTable = true;
	}
	
	public void calendarSelected(){
		if(isFirstTable == true){
			createBalanceMoveMentList();
		} else {
			createBalanceMoveMentList2();
		}
	}
	
	public void calendarSelected2(){
		if(isFirstTable == true){
			createDebtList();
		} else {
			createDebtList2();
		}
	}
	
	public void createDebtList(){
		if(debtList != null) debtList.clear();
		
		debtList.addAll(paymentController.getDebtList(selectedMember, debtFromDate, debtToDate, isShowAll));
	}
	
	public void createDebtList2(){
		if(debtList != null) debtList.clear();
		
		debtList.addAll(paymentController.getDebtList(selectedMember2, debtFromDate, debtToDate, isShowAll));
	}
			
//	public void getEmpQuery(){
//		List<Employee> list = memberController.getEmpQuery();
//		queryList.clear();
//		for(Employee emp : list){
//			String prefix = null;
//			String status = null;
//			if(emp.getNamePrefix() != null){
//				prefix = emp.getNamePrefix().getID().toString();
//			}
//			if(emp.getStatus() != null && !emp.getStatus().trim().equals("") && !emp.getStatus().equals("null")){
//				status = emp.getStatus().trim();
//				queryList.add("INSERT INTO TEMPFUNEMPLOYEE (EMPLOYEEID,ENGFIRSTNAME,ENGLASTNAME,THAIFIRSTNAME,THAILASTNAME,SEX,EMPLOYEECODE,STATUS,NAMEPREFIX_ID) VALUES ("+emp.getEmployeeID()+",'"+emp.getEngFirstName()+"','"+emp.getEngLastName()+"','"+emp.getThaiFirstName()+"','"+emp.getThaiLastName()+"','"+emp.getSex()+"','"+emp.getEmployeeCode()+"','"+status+"',"+prefix+")");
//			}
//			System.out.println("INSERT INTO TEMPFUNEMPLOYEE (EMPLOYEEID,ENGFIRSTNAME,ENGLASTNAME,THAIFIRSTNAME,THAILASTNAME,SEX,EMPLOYEECODE,STATUS,NAMEPREFIX_ID) VALUES ("+emp.getEmployeeID()+",'"+emp.getEngFirstName()+"','"+emp.getEngLastName()+"','"+emp.getThaiFirstName()+"','"+emp.getThaiLastName()+"','"+emp.getSex()+"','"+emp.getEmployeeCode()+"','"+status+"',"+prefix+")");
//		}
//		System.out.println("result = "+queryList.size());
//	}
}
