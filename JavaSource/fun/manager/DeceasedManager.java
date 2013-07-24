package fun.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import fun.controller.MemberController;
import fun.controller.PaymentController;
import fun.customtype.EmployeeStatus;
import fun.customtype.TransactionType;
import fun.model.CurrentBalance;
import fun.model.Debt;
import fun.model.Employee;
import fun.model.Member;
import fun.model.Payment;
import fun.model.RetirementPayment;

@ManagedBean(name="decease")
@ViewScoped
public class DeceasedManager {
	private Date fromDate;
	private Date toDate;
	private PaymentController paymentController = new PaymentController();
	private MemberController memberController = new MemberController();
	private Member selectedDeadmember;
	private ArrayList<Member> deadMemberList = new ArrayList<Member>();
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Member getSelectedDeadmember() {
		return selectedDeadmember;
	}

	public void setSelectedDeadmember(Member selectedDeadmember) {
		this.selectedDeadmember = selectedDeadmember;
	}

	public ArrayList<Member> getDeadMemberList() {
		return deadMemberList;
	}

	public void setDeadMemberList(ArrayList<Member> deadMemberList) {
		this.deadMemberList = deadMemberList;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public DeceasedManager(){
//		fromDate = new DateTime("2004-12-13").toDate();
		fromDate = new DateTime().now().dayOfMonth().withMinimumValue().toDateMidnight().toDate();
		toDate = new DateTime().now().dayOfMonth().withMaximumValue().toDateMidnight().toDate();
		createDeadMemberList();
	}
	
	public void createDeadMemberList(){
		if(deadMemberList != null) deadMemberList.clear();
		
		deadMemberList.addAll(memberController.getDeadMember(fromDate, toDate));
	}
	
	public void calculate(){
		System.out.println("Start: "+DateTime.now().toDate());
		List<Member> calculatedMemberList = new ArrayList<Member>();
		List<Member> retireMemberList = new ArrayList<Member>();
		List<RetirementPayment> paymentList = new ArrayList<RetirementPayment>();
		List<Debt> debtList = new ArrayList<Debt>();
		HashMap memberMap = new HashMap();
		
		//เรียกสมาชิกเกษียณอายุที่ยังมีชีวิต
		retireMemberList.addAll(memberController.getRetireNotDeadMember());
		
		for(Member member : retireMemberList){
			if(member.getBalance() == null){
				CurrentBalance balance = new CurrentBalance();
				balance.setMember(member);
				memberMap.put(member.getMemberNumber(), balance);
			} else {
				memberMap.put(member.getMemberNumber(), member.getBalance());
			}
		}
		
		//คัดเฉพาะสมาชิกที่จะถูกคำนวณซึ่งสถานะเป็นเสียชีวิตแล้ว
		for(Member member : Lists.reverse(deadMemberList)){
//			if(member.getReceivedAmountFromRetire().equals(new BigDecimal("0.00")) && member.getReceivedDebtAmount().equals(new BigDecimal("0.00"))){
//				calculatedMemberList.add(member);
//			}
			
			if(member.getBalance() == null){
				CurrentBalance balance = new CurrentBalance();
				balance.setMember(member);
				memberMap.put(member.getMemberNumber(), balance);
			} else {
				memberMap.put(member.getMemberNumber(), member.getBalance());
			}
			
			if(member.getBalance() == null || (member.getBalance() != null && member.getBalance().getDebtAmount().equals(new BigDecimal("0.00")) && member.getBalance().getReceivedAmount().equals(new BigDecimal("0.00")))){
				calculatedMemberList.add(member);
			}
		}
		
		//รวมสมาชิกที่มีชีวิตกับสมาชิกที่จะถูกคำนวณ
//		retireMemberList.addAll(calculatedMemberList);
		
		//หักเงินออกจากสมาชิกที่เสียชีวิตด้วยกัน
		RetirementPayment payment;	
		Debt debt;
		int loopCount = 1;
		System.out.println("Initiate calculation process....");	
		//หักเงินออกจากสมาชิกที่ยังมีชีวิต
		for(Member calMember : calculatedMemberList){	
			BigDecimal sumReceivedAmount = new BigDecimal("0.00");
			BigDecimal sumDebtAmount = new BigDecimal("0.00");
			for(Member member : retireMemberList){
				payment = new RetirementPayment();
				payment.setMember(member);
				payment.setTransactionAmount(new BigDecimal("20.00"));
				payment.setTransactionDate(DateTime.now().toDateMidnight().toDate());
				payment.setTransactionType(TransactionType.DEDUCT);
				payment.setPaidForMember(calMember);
				
//				RetirementPayment latest = paymentController.getLatestPayment(member);
//		
//				if(latest == null){
//					payment.setAvailableAmount(new BigDecimal("-20.00"));
//				} else {	
//					payment.setAvailableAmount(latest.getAvailableAmount().subtract(new BigDecimal("20.00")));
//				}
				
//				CurrentBalance currentBalance = paymentController.getCurrentBalance(member);
//				
//				if(currentBalance == null){
//					payment.setAvailableAmount(new BigDecimal("-20.00").multiply(new BigDecimal(loopCount)));
//				} else {
//					payment.setAvailableAmount(currentBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(loopCount))));
//				}
				
				CurrentBalance currentBalance = (CurrentBalance) memberMap.get(member.getMemberNumber());
				payment.setAvailableAmount(currentBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(loopCount))));				
				
							
				paymentList.add(payment);
				
				if(loopCount == calculatedMemberList.size()){
//					if(currentBalance == null){
//						CurrentBalance balance = new CurrentBalance();
//						balance.setMember(member);
//						balance.setCurrentBalance(new BigDecimal("-20.00").multiply(new BigDecimal(loopCount)));
//						
//						paymentController.saveCurrentBalance(balance);
//					} else {
//						currentBalance.setCurrentBalance(currentBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(loopCount))));
//						
//						paymentController.saveCurrentBalance(currentBalance);
//					}
					currentBalance.setCurrentBalance(currentBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(loopCount))));
					memberMap.put(member.getMemberNumber(), currentBalance);
				}
				
				//คิดหนี้
				if(payment.getAvailableAmount().compareTo(new BigDecimal("0.00")) <= 0){
					debt = new Debt();
					debt.setDebtAmount(payment.getTransactionAmount());
					debt.setInDebtMember(member);
					debt.setPaidForMember(calMember);
					debt.setTransactionDate(DateTime.now().toDate());
					
					debtList.add(debt);
					sumDebtAmount = sumDebtAmount.add(payment.getTransactionAmount());
				} else {
					sumReceivedAmount = sumReceivedAmount.add(payment.getTransactionAmount());
				}
			}
			loopCount++;
//			CurrentBalance currentbal = paymentController.getCurrentBalance(calMember);
			CurrentBalance currentbal = (CurrentBalance) memberMap.get(calMember.getMemberNumber());
			currentbal.setDebtAmount(sumDebtAmount);
			currentbal.setReceivedAmount(sumReceivedAmount);
			
			memberMap.put(calMember.getMemberNumber(), currentbal);
//			if(currentbal != null){
//				currentbal.setReceivedAmount(sumReceivedAmount);
//				currentbal.setDebtAmount(sumDebtAmount);
//				paymentController.saveCurrentBalance(currentbal);
//			} else {
//				CurrentBalance balance = new CurrentBalance();
//				balance.setMember(calMember);
//				balance.setDebtAmount(sumDebtAmount);
//				balance.setReceivedAmount(sumReceivedAmount);
//				paymentController.saveCurrentBalance(balance);
//			}
		}
		
		loopCount = 1;
		for(Member paidMember : calculatedMemberList){
//			CurrentBalance paidMemberBalance = paymentController.getCurrentBalance(paidMember);
			CurrentBalance paidMemberBalance = (CurrentBalance) memberMap.get(paidMember.getMemberNumber());
			int innerLoop = 1;
			//คิดเฉพาะสมาชิกที่เป็นผู้เกษียณ
			if((paidMember.getMainMember() != null && paidMember.getMainMember().getEmployee() != null && paidMember.getMainMember().getEmployee().getStatus().equals(EmployeeStatus.RETIREMENT)) || (paidMember.getEmployee() != null && paidMember.getEmployee().getStatus().equals(EmployeeStatus.RETIREMENT))){
				for(Member receiveMember : calculatedMemberList.subList(0, loopCount-1)){
					payment = new RetirementPayment();
					payment.setMember(paidMember);
					payment.setPaidForMember(receiveMember);
					payment.setTransactionAmount(new BigDecimal("20.00"));
					payment.setTransactionDate(DateTime.now().toDate());
					payment.setTransactionType(TransactionType.DEDUCT);
					
//					if(paidMemberBalance == null){
//						payment.setAvailableAmount(new BigDecimal("-20.00").multiply(new BigDecimal(innerLoop)));
//					} else {
//						payment.setAvailableAmount(paidMemberBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(innerLoop))));
//					}
					payment.setAvailableAmount(paidMemberBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(innerLoop))));
					//หักเงินจากผู้จ่าย
					paymentList.add(payment);
								
					//ปรับ balance ผู้จ่าย
					if(innerLoop == calculatedMemberList.subList(0, loopCount-1).size()){
//						if(paidMemberBalance == null){
//							CurrentBalance balance = new CurrentBalance();
//							balance.setMember(paidMember);
//							balance.setCurrentBalance(new BigDecimal("-20.00").multiply(new BigDecimal(innerLoop)));
//							
//							paymentController.saveCurrentBalance(balance);
//						} else {
//							paidMemberBalance.setCurrentBalance(paidMemberBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(innerLoop))));
//							
//							paymentController.saveCurrentBalance(paidMemberBalance);
//						}	
						paidMemberBalance.setCurrentBalance(paidMemberBalance.getCurrentBalance().subtract(new BigDecimal("20.00").multiply(new BigDecimal(innerLoop))));
						memberMap.put(paidMember.getMemberNumber(), paidMemberBalance);
					}
					
					if(payment.getAvailableAmount().compareTo(new BigDecimal("0.00")) <= 0){
						debt = new Debt();
						debt.setDebtAmount(payment.getTransactionAmount());
						debt.setInDebtMember(paidMember);
						debt.setPaidForMember(receiveMember);
						debt.setTransactionDate(DateTime.now().toDate());
						
						debtList.add(debt);
						
//						CurrentBalance currentBalance = paymentController.getCurrentBalance(receiveMember);
						CurrentBalance currentBalance = (CurrentBalance) memberMap.get(receiveMember.getMemberNumber());
						currentBalance.setDebtAmount(currentBalance.getDebtAmount().add(new BigDecimal("20.00")));
						memberMap.put(receiveMember.getMemberNumber(), currentBalance);
//						if(currentBalance == null){
//							CurrentBalance balance = new CurrentBalance();
//							balance.setMember(receiveMember);
//							balance.setDebtAmount(new BigDecimal("20.00"));
//							
//							paymentController.saveCurrentBalance(balance);
//						} else {
//							currentBalance.setDebtAmount(currentBalance.getDebtAmount().add(new BigDecimal("20.00")));
//							
//							paymentController.saveCurrentBalance(currentBalance);
//						}
					} else {
						//เพิ่ม receive balance ผู้รับ
//						CurrentBalance currentBal = paymentController.getCurrentBalance(receiveMember);
						CurrentBalance currentBal = (CurrentBalance) memberMap.get(receiveMember.getMemberNumber());
						currentBal.setReceivedAmount(currentBal.getReceivedAmount().add(new BigDecimal("20.00")));
						memberMap.put(receiveMember.getMemberNumber(), currentBal);
//						if(currentBal == null){
//							CurrentBalance balance = new CurrentBalance();
//							balance.setMember(receiveMember);
//							balance.setReceivedAmount(new BigDecimal("20.00"));
//							
//							paymentController.saveCurrentBalance(balance);
//						} else {
//							currentBal.setReceivedAmount(currentBal.getReceivedAmount().add(new BigDecimal("20.00")));
//							
//							paymentController.saveCurrentBalance(currentBal);
//						}
					}
					innerLoop++;
				}			
			}
			loopCount++;
		}
		System.out.println("Saving payment list...");
		paymentController.addBalance(paymentList);
		System.out.println("Saving debt list...");
		paymentController.addDebt(debtList);
		System.out.println("Saving balance list...");
		paymentController.saveCurrentBalance(memberMap);
		System.out.println("All process done !!");
		
		createDeadMemberList();
		System.out.println("Finished: "+DateTime.now().toDate());
	}

}
