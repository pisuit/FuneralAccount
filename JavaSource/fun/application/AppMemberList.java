package fun.application;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import fun.controller.MemberController;
import fun.datamodel.MemberDataModel;
import fun.model.Member;

@ManagedBean(name="appMemberList", eager=true)
@ApplicationScoped
public class AppMemberList {
	private MemberController memberController = new MemberController();
	private MemberDataModel memberDataModel;
	private List<Member> memberList = new ArrayList<Member>();
	
	public MemberDataModel getMemberDataModel() {
		return memberDataModel;
	}

	public void setMemberDataModel(MemberDataModel memberDataModel) {
		this.memberDataModel = memberDataModel;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public AppMemberList(){
		createMemberList();
	}
	
	private void createMemberList(){
		memberList = memberController.getRetireNotDeadMember();
		
		memberDataModel = new MemberDataModel(memberList);
	}
}
