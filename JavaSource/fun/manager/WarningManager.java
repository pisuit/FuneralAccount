package fun.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

import fun.controller.MemberController;
import fun.controller.PaymentController;
import fun.customtype.MemberStatus;
import fun.model.Debt;
import fun.model.Member;
import fun.model.Warning;

@ManagedBean(name="warning")
@ViewScoped
public class WarningManager {
	private List<Member> debtMemberList = new ArrayList<Member>();
	private MemberController memberController = new MemberController();
	private PaymentController paymentController = new PaymentController();
	private Member selectedMember;
	private List<Debt> debtList = new ArrayList<Debt>();
	private Date warnDate;
	private int nextCount;

	public List<Member> getDebtMemberList() {
		return debtMemberList;
	}

	public void setDebtMemberList(List<Member> debtMemberList) {
		this.debtMemberList = debtMemberList;
	}
	
	public Member getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(Member selectedMember) {
		this.selectedMember = selectedMember;
	}

	public Date getWarnDate() {
		return warnDate;
	}

	public void setWarnDate(Date warnDate) {
		this.warnDate = warnDate;
	}

	public int getNextCount() {
		return nextCount;
	}

	public void setNextCount(int nextCount) {
		this.nextCount = nextCount;
	}

	public List<Debt> getDebtList() {
		return debtList;
	}

	public void setDebtList(List<Debt> debtList) {
		this.debtList = debtList;
	}

	public WarningManager(){
		warnDate = DateTime.now().toDateMidnight().toDate();
		createDebtMemberList();
	}
	
	private void createDebtMemberList(){
		if(debtMemberList != null) debtMemberList.clear();
		
		debtMemberList.addAll(memberController.getDebtMemberList());
	}
	
	public void createDebtList(){
		if(debtList != null) debtList.clear();
		
		debtList.addAll(paymentController.getDebtList(selectedMember));
		
		if(selectedMember.getWarning() != null){
			nextCount = selectedMember.getWarning().getWarningCount()+1;
		} else {
			nextCount = 1;
		}
	}
	
	public void saveWarning(){
		if(selectedMember.getWarning() != null){
			selectedMember.getWarning().setWarningCount(nextCount);
			if(nextCount == 1) selectedMember.getWarning().setWarnDate1(warnDate);
			if(nextCount == 2) selectedMember.getWarning().setWarnDate2(warnDate);
			if(nextCount == 3) selectedMember.getWarning().setWarnDate3(warnDate);
			
			paymentController.saveWarning(selectedMember.getWarning());
		} else {
			Warning warning = new Warning();
			warning.setMember(selectedMember);
			warning.setWarningCount(nextCount);
			if(nextCount == 1) warning.setWarnDate1(warnDate);
			if(nextCount == 2) warning.setWarnDate2(warnDate);
			if(nextCount == 3) warning.setWarnDate3(warnDate);
			
			paymentController.saveWarning(warning);
		}
		
		createDebtMemberList();
	}
	
	public void firedMember(){
		selectedMember.setStatus(MemberStatus.F);
		selectedMember.setResignDate(DateTime.now().toDateMidnight().toDate());
		memberController.fireMember(selectedMember, debtList);
		debtList.clear();
		selectedMember = new Member();
		createDebtMemberList();
		
	}
	
	public void showDialog(){
		RequestContext context = RequestContext.getCurrentInstance(); 
		context.execute("confirmDialog.show(); document.getElementById(\"center\").style.zIndex= \"\";");
	}
}
