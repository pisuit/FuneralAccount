package fun.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.joda.time.DateTime;

import fun.controller.MemberController;
import fun.model.Member;
import fun.model.RetirementPayment;
import fun.reportmodel.PaymentModel;
import fun.utils.ReportUtils;

@ManagedBean(name="balance")
@ViewScoped
public class BalanceSummaryManager {
	private List<Member> memberList = new ArrayList<Member>();
	private MemberController memberController = new MemberController();
	private Date fromDate;
	private Date toDate;
	private BigDecimal sumTotalBalance = new BigDecimal("0.00");
	
	public List<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	public MemberController getMemberController() {
		return memberController;
	}
	public void setMemberController(MemberController memberController) {
		this.memberController = memberController;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public BalanceSummaryManager(){
		fromDate = new DateTime().now().dayOfMonth().withMinimumValue().toDateMidnight().toDate();
		toDate = new DateTime().now().dayOfMonth().withMaximumValue().toDateMidnight().toDate();
	}
	
	public void createMemberList(){
		if(memberList != null) memberList.clear();
		
		List<Member> list = memberController.getMemeberListWithPayment(fromDate, toDate);
		Collections.sort(list, MEMBER_COMPARATOR);
		sortMember(list);
		list.removeAll(memberList);
		memberList.addAll(list);
	}
	
	private void sortMember(List<Member> list){
		for(Member member : list){
			if(member.getMainMember() == null){
				memberList.add(member);
				addChild(member,list);
			}
		}
	}
	
	private void addChild(Member mainMember,List<Member> list){
		for(Member member : list){
			if(member.getMainMember() != null){
				if(member.getMainMember().equals(mainMember)){
					memberList.add(member);
				}
			}
		}
	}
	
	public void printReport(){
		
		try {
			JasperReport report = null;
			PaymentModel model;
			List<PaymentModel> modelList = new ArrayList<PaymentModel>();
			
			report = (JasperReport)JRLoader.loadObject(this.getClass().getResource("/fun/reports/summary.jasper"));			
			
			for(Member member : memberList){
				for(RetirementPayment payment : member.getPayments()){
					model = new PaymentModel();
					model.setName(member.getName());
					model.setNumber(Integer.toString(member.getMemberNumber()));
					if(payment.getPaidForMember() != null){
						model.setPaidForName(payment.getPaidForMember().getName());
						model.setPaidForNumber(Integer.toString(payment.getPaidForMember().getMemberNumber()));
					} else {
						model.setPaidForName(null);
					}
					model.setTransactionAmount(payment.getTransactionAmount());
					model.setTransactionDate(payment.getTransactionDate());
					model.setTransactionType(payment.getTransactionType().getValue());
					model.setTransactionRemain(payment.getAvailableAmount());
					model.setCurrentBalance(member.getPayments().get(member.getPayments().size()-1).getAvailableAmount());
					
					modelList.add(model);
				}
			}
			
			HashMap parameters = new HashMap();
			parameters.put("fromDate", fromDate);
			parameters.put("toDate", toDate);
			parameters.put("sumTotal", sumTotalBalance);
			parameters.put("sumMember", memberList.size());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(modelList));
						
//			JasperExportManager.exportReportToPdfFile(jasperPrint,"C:/summary.pdf");
			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			ReportUtils.displayPdfReport(bytes);	
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BigDecimal getSumTotalBalance() {
		sumTotalBalance = new BigDecimal("0.00");
		if(memberList.size() != 0){
			for(Member member : memberList){
//				if(member.getBalance() != null){
//					sumTotalBalance = sumTotalBalance.add(member.getBalance().getCurrentBalance());
//				}
				sumTotalBalance = sumTotalBalance.add(member.getPayments().get(member.getPayments().size()-1).getAvailableAmount());
			}
		}
		return sumTotalBalance;
	}
	
	private static Comparator<Member> MEMBER_COMPARATOR = new Comparator<Member>() {
		
		@Override
		public int compare(Member o1, Member o2) {
			// TODO Auto-generated method stub
			return getRidOfNamePrefix(o1.getName()).compareTo(getRidOfNamePrefix(o2.getName()));
		}
	};
	
	private static String getRidOfNamePrefix(String name){
		if(name.length() > 3){
			//3 ตัว
			if(name.substring(0, 3).equals("นาง") || name.substring(0, 3).equals("นาย")){
				return name.substring(3, name.length());
			}
			//6 ตัว
			if(name.substring(0, 6).equals("พ.อ.อ.") || name.substring(0, 6).equals("พ.อ.ท.")){
				return name.substring(6, name.length());
			}
			//4 ตัว
			if(name.substring(0, 4).equals("ร.อ.") || name.substring(0, 4).equals("ร.ท.") || 
					name.substring(0, 4).equals("น.ส.") || name.substring(0, 4).equals("น.ท.") || 
					name.substring(0, 4).equals("ร.ต.") || name.substring(0, 4).equals("พ.อ.") || 
					name.substring(0, 4).equals("น.ต.") || name.substring(0, 4).equals("พ.ท.")){
				return name.substring(4, name.length());
			}
		}
		return name;
	}
}
