package fun.manager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.bsf.util.Bean;
import org.apache.ws.scout.model.uddi.v2.GetServiceDetail;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import fun.application.AppMemberList;
import fun.controller.MemberController;
import fun.customtype.EmployeeStatus;
import fun.datamodel.DeadMemberDataModel;
import fun.model.Member;
import fun.reportmodel.MonthlyModel;
import fun.utils.ReportUtils;
import fun.utils.ThaiNumberConverter;

@ManagedBean(name="monthly")
@ViewScoped
public class MonthlyManager {
	private List<Member> memberList = new ArrayList<Member>();
	private List<DeadMemberDataModel> deadMemberModelList = new ArrayList<DeadMemberDataModel>();
	private MemberController memberController  = new MemberController();
	private List<SelectItem> monthList = new ArrayList<SelectItem>();
	private Date fromDate;
	private Date toDate;
	private int selectedMonth;
	private Date printDate;
	private List<MonthlyModel> monthlyReportList = new ArrayList<MonthlyModel>();
	private SimpleDateFormat df;
	private SimpleDateFormat mf;
	private String docNumber;
	private DecimalFormat moneyFormat = new DecimalFormat("###,###");
	private DecimalFormat numberFormat = new DecimalFormat("0000");
	private Member[] selectedMembers;
	
	public List<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	public Member[] getSelectedMembers() {
		return selectedMembers;
	}
	public void setSelectedMembers(Member[] selectedMembers) {
		this.selectedMembers = selectedMembers;
	}
	public List<SelectItem> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<SelectItem> monthList) {
		this.monthList = monthList;
	}
	public List<DeadMemberDataModel> getDeadMemberModelList() {
		return deadMemberModelList;
	}
	public void setDeadMemberModelList(List<DeadMemberDataModel> deadMemberModelList) {
		this.deadMemberModelList = deadMemberModelList;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public int getSelectedMonth() {
		return selectedMonth;
	}
	public void setSelectedMonth(int selectedMonth) {
		this.selectedMonth = selectedMonth;
	}
	public List<MonthlyModel> getMonthlyReportList() {
		return monthlyReportList;
	}
	public void setMonthlyReportList(List<MonthlyModel> monthlyReportList) {
		this.monthlyReportList = monthlyReportList;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getPrintDate() {
		return printDate;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public MonthlyManager(){
		printDate = new DateTime().now().toDateMidnight().toDate();
		selectedMonth = DateTime.now().getMonthOfYear();
		df = new SimpleDateFormat("d MMMM yyyy", new Locale("th", "TH", "TH"));
		mf = new SimpleDateFormat("MMMM", new Locale("th", "TH", "TH"));
		createMonthList();
	}	
	
	public void createDeadList(){
		DateTime date = new DateTime(new DateTime().now().getYear(), selectedMonth, 1, 0, 0, 0);
		fromDate =  new DateTime(date.getYear(), date.getMonthOfYear(), date.dayOfMonth().getMinimumValue(), 0, 0, 0).toDateMidnight().toDate();
		toDate = new DateTime(date.getYear(), date.getMonthOfYear(), date.dayOfMonth().getMaximumValue(), 0, 0, 0).toDateMidnight().toDate();
		System.out.println("from date: "+fromDate);
		System.out.println("to date: "+toDate);
//		fromDate = new DateTime("2010-12-13T21:39:45.618-08:00").toDate();
//		toDate = new DateTime().now().toDate();
		if(deadMemberModelList != null) deadMemberModelList.clear();
		
		DeadMemberDataModel dataModel;
		List<Member> deadList = memberController.getDeadMemberForMonthlyReport(fromDate, toDate);
		if(deadList != null){
			for(Member member : deadList){
				dataModel = new DeadMemberDataModel();
				dataModel.setDeadMemberName(member.getName());
				dataModel.setDeadMemberNumber(member.getMemberNumber());
				dataModel.setReportDate(member.getResignDate());
				if(member.getMainMember() != null){
					dataModel.setMemberType("��Ҫԡ����");
					dataModel.setMemberName(member.getMainMember().getName());
					dataModel.setMemberNumber(member.getMainMember().getMemberNumber());
					dataModel.setMainMemberName(member.getMainMember().getName());
					if(member.getMainMember().getEmployee() != null && member.getMainMember().getEmployee().getStatus().equals(EmployeeStatus.RETIREMENT)){
						dataModel.setMemberStatus("�ͧ������³");
					} else {
						dataModel.setMemberStatus("�ͧ��ѡ�ҹ");
					}
				} else {
					dataModel.setMemberType("��Ҫԡ���ѭ");
				}
				deadMemberModelList.add(dataModel);
			}
		}
		RequestContext context = RequestContext.getCurrentInstance(); 
		context.execute("printDialog.show(); document.getElementById(\"center\").style.zIndex= \"\";");
	}	
	
	private void createMonthList(){
		if(monthList != null) monthList.clear();
		
		monthList.add(new SelectItem(1, "���Ҥ�"));
		monthList.add(new SelectItem(2, "����Ҿѹ��"));
		monthList.add(new SelectItem(3, "�չҤ�"));
		monthList.add(new SelectItem(4, "����¹"));
		monthList.add(new SelectItem(5, "����Ҥ�"));
		monthList.add(new SelectItem(6, "�Զع�¹"));
		monthList.add(new SelectItem(7, "�á�Ҥ�"));
		monthList.add(new SelectItem(8, "�ԧ�Ҥ�"));
		monthList.add(new SelectItem(9, "�ѹ��¹"));
		monthList.add(new SelectItem(10, "���Ҥ�"));
		monthList.add(new SelectItem(11, "��Ȩԡ�¹"));
		monthList.add(new SelectItem(12, "�ѹ�Ҥ�"));
	}
	
	private String getMonth(int month){
		switch (month) {
		case 1:
			return "���Ҥ�";
		case 2:
			return "����Ҿѹ��";
		case 3:
			return "�չҤ�";
		case 4:
			return "����¹";
		case 5:
			return "����Ҥ�";
		case 6:
			return "�Զع�¹";
		case 7:
			return "�á�Ҥ�";
		case 8:
			return "�ԧ�Ҥ�";
		case 9:
			return "�ѹ��¹";
		case 10:
			return "���Ҥ�";
		case 11:
			return "��Ȩԡ�¹";
		case 12:
			return "�ѹ�Ҥ�";
		default:
			return null;
		}
	}
	
	public void printReport(){
		try {
			int count = 1;
			JasperReport report = null;
			MonthlyModel model;
			List<MonthlyModel> modelList = new ArrayList<MonthlyModel>();
			HashMap parameters = new HashMap();
			int memberAmount = memberController.getMemberAmount();
			
			if(memberList != null) memberList.clear();
			if(monthlyReportList != null) monthlyReportList.clear();
			
			report = (JasperReport)JRLoader.loadObject(this.getClass().getResource("/fun/reports/monthly.jasper"));
		
			if(selectedMembers.length > 0){
				for(int i=0;i<selectedMembers.length;i++){
					memberList.add(memberController.getMember(selectedMembers[i].getID()));
				}
			} else {
				memberList.addAll(memberController.getRetireNotDeadMember());
			}
			
			for(Member aliveMember : memberList){
				count = 1;
				for(DeadMemberDataModel deadMember : deadMemberModelList){
					model = new MonthlyModel();
					if(aliveMember.getBalance() != null){
						model.setCurrentBalance(ThaiNumberConverter.arabicToThai(moneyFormat.format(aliveMember.getBalance().getCurrentBalance().setScale(0)))+".-");
					} 
					model.setDeadDate("�֧�����������ѹ��� "+df.format(deadMember.getReportDate()));
					model.setName(aliveMember.getName());
					model.setNumber(Integer.toString(aliveMember.getMemberNumber()));
					model.setDeadName(ThaiNumberConverter.arabicToThai(count+". "+deadMember.getDeadMemberName()));
					if(deadMember.getMemberType().equals("��Ҫԡ����")){
						model.setDeadInfo(deadMember.getMemberType()+" �����Ţ "+ThaiNumberConverter.arabicToThai(numberFormat.format(deadMember.getDeadMemberNumber()))+" "+deadMember.getMemberStatus()+" ("+deadMember.getRelation()+")");				
						model.setMainMemberName(deadMember.getMainMemberName()+"   "+deadMember.getDeadCause());
					} else {
						model.setDeadInfo(deadMember.getMemberType()+" �����Ţ "+ThaiNumberConverter.arabicToThai(numberFormat.format(deadMember.getDeadMemberNumber())));				
						model.setMainMemberName(deadMember.getDeadCause());
					}
					modelList.add(model);
					count++;
				}
			}
			parameters.put("docNumber", "��� ʡ.�� "+ThaiNumberConverter.arabicToThai(docNumber));
			parameters.put("printDate", df.format(printDate));
			parameters.put("deadNumber", ThaiNumberConverter.arabicToThai(Integer.toString(deadMemberModelList.size())));
			parameters.put("sumPaidAmount", ThaiNumberConverter.arabicToThai(moneyFormat.format(deadMemberModelList.size()*20))+".-");
			parameters.put("memberAmount", ThaiNumberConverter.arabicToThai(moneyFormat.format(memberAmount)));
			parameters.put("printMonth", mf.format(printDate));
			parameters.put("sign", this.getClass().getResource("/fun/reports/sign.gif"));
			parameters.put("selectedMonth", getMonth(selectedMonth));
			
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
}
