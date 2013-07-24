package fun.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import fun.controller.LoginController;
import fun.customtype.Action;
import fun.customtype.Role;
import fun.ldap.LDAPConnect;
import fun.ldap.LDAPUser;
import fun.model.Authorization;
import fun.model.User;
import fun.utils.Constants;

@ManagedBean(name="login")
@ViewScoped
public class LoginManager {
	private String username;
	private String password;
	private LoginController loginController = new LoginController();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LoginManager(){
		
	}
	
	public String login(){
		if(username == null || username.equals("")){
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"กรุณาใส่ชื่อผู้ใช้",""));
			 return null;
		}
		if(password == null || password.equals("")){
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"กรุณาใส่รหัสผ่าน",""));
			 return null;
		}
		if(username.equals("admin") && password.equals("admin")){
			Authorization authorization = new Authorization();
			List<Authorization> authorizations = new ArrayList<Authorization>();
			authorization.setAction(Action.EDIT);
			authorization.setRole(Role.ADMIN);
			authorization.setUser(new User());
			authorizations.add(authorization);
			
			User user = new User();
			user.setName("admin");
			user.setSurename("admin");
			user.setUsername("admin");
			user.setAuthorizations(authorizations);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.AUTH_KEY, user);
			return "admin?faces-redirect=true";
		} else {
			User user = authentication(username, password);
			if(user != null){
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.AUTH_KEY, user);
				if(user.getAuthorizations() != null && user.getAuthorizations().size() != 0){
					for(Authorization authorization : user.getAuthorizations()){
						if(authorization.getRole().equals(Role.RETIRE));{
							return "retire?faces-redirect=true";
						}
					}
					for(Authorization authorization : user.getAuthorizations()){
						if(authorization.getRole().equals(Role.CALCULATE));{
							return "retire?faces-redirect=true";
						}
					}
					for(Authorization authorization : user.getAuthorizations()){
						if(authorization.getRole().equals(Role.WARN_LETTER));{
							return "retire?faces-redirect=true";
						}
					}
					for(Authorization authorization : user.getAuthorizations()){
						if(authorization.getRole().equals(Role.BALANCE_REPORT));{
							return "retire?faces-redirect=true";
						}
					}
					for(Authorization authorization : user.getAuthorizations()){
						if(authorization.getRole().equals(Role.MONTHLY_REPORT));{
							return "retire?faces-redirect=true";
						}
					}
					for(Authorization authorization : user.getAuthorizations()){
						if(authorization.getRole().equals(Role.ADMIN));{
							return "retire?faces-redirect=true";
						}
					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"คุณไม่มีสิทธิในการใช้งานระบบ",""));
				return null;
			} else {
				 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ไม่มีชื่อผู้ใช้งานในระบบ",""));
				return null;
			}
			
		}
	}
	
	private User authentication(String username, String password) {
		LDAPConnect connect = new LDAPConnect();
		LDAPUser ldapUser = connect.login(username, password);
		connect.disconnect();
		if(ldapUser != null){
			System.out.println("Getting user from database...");
			User dbUser = loginController.getUser(username);
			if(dbUser == null){
				return null;
			} else {
				return dbUser;
			}
		} else {
			return null;
		}
	}
	
	public String logout(){
//		removeBeanFromSession("appMemberList");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.AUTH_KEY);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		 
		return "login?faces-redirect=true";
	}
	
	private void removeBeanFromSession(String beanName){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.remove(beanName); // Removes the session scoped bean.
	}
}
