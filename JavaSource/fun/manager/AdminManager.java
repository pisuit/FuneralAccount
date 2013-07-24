package fun.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;

import fun.controller.AdminController;
import fun.customtype.Action;
import fun.customtype.DataStatus;
import fun.customtype.Role;
import fun.model.Authorization;
import fun.model.User;

@ManagedBean(name="admin")
@ViewScoped
public class AdminManager {
	private AdminController adminController = new AdminController();
	private List<User> userList = new ArrayList<User>();
	private List<SelectItem> roleSelectItemList = new ArrayList<SelectItem>();
	private List<String> selectedRole = new ArrayList<String>();
	private User editUser;
	
	public List<SelectItem> getRoleSelectItemList() {
		return roleSelectItemList;
	}
	public void setRoleSelectItemList(List<SelectItem> roleSelectItemList) {
		this.roleSelectItemList = roleSelectItemList;
	}
	public List<String> getSelectedRole() {
		return selectedRole;
	}
	public void setSelectedRole(List<String> selectedRole) {
		this.selectedRole = selectedRole;
	}
	public User getEditUser() {
		return editUser;
	}
	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public AdminManager(){
		createUserList();
		createRoleSelectItemList();
		editUser = new User();
	}
	
	private void createUserList(){
		if(userList != null) userList.clear();
		
		userList.addAll(adminController.getUserList());
	}
	
	private void createRoleSelectItemList(){
		if(roleSelectItemList != null) roleSelectItemList.clear();
		
		for(Role role : Role.values()){
			roleSelectItemList.add(new SelectItem(role.getID(), role.getValue()));
		}
	}
	
	public void saveUser(){		
		User duplicatedUser = adminController.getUserByUsername(editUser.getUsername());
		if(editUser.getId() == null && duplicatedUser != null){
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"มีชื่อผู้ใช้นี้ในระบบแล้ว",""));
		} else {
			Authorization authorization;
			List<Authorization> authorizations = new ArrayList<Authorization>();
			for(String string : selectedRole){
				authorization = new Authorization();
				authorization.setAction(Action.EDIT);
				authorization.setRole(Role.find(string));
				authorizations.add(authorization);
			}
			
			adminController.saveUser(editUser, authorizations);
			createUserList();
			clearData();
		}	
	}
	
	public void deleteUser(){
		editUser.setStatus(DataStatus.DELETED);
		adminController.saveUser(editUser, null);
		createUserList();
		clearData();
	}
	
	public void setCheckBoxValue(){
		if(selectedRole != null) selectedRole.clear();
		
		if(editUser.getAuthorizations() != null){
			for(Authorization authorization : editUser.getAuthorizations()){
				selectedRole.add(authorization.getRole().getID());
			}
		}
	}
	
	public void clearData(){
		editUser = new User();
		if(selectedRole != null) selectedRole.clear();
	}
}
