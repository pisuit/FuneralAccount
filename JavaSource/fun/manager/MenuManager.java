package fun.manager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import fun.customtype.Role;
import fun.model.Authorization;
import fun.model.User;
import fun.utils.Constants;

@ManagedBean(name="menu")
@RequestScoped
public class MenuManager {
	private User userSession;

	public MenuManager() {
		userSession = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(Constants.AUTH_KEY);
	}

	public boolean isRetire() {
		if (userSession != null && userSession.getAuthorizations() != null) {
			for(Authorization authorization : userSession.getAuthorizations()){
				if(authorization.getRole().equals(Role.RETIRE)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCalculate() {
		if (userSession != null && userSession.getAuthorizations() != null) {
			for(Authorization authorization : userSession.getAuthorizations()){
				if(authorization.getRole().equals(Role.CALCULATE)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isWarnLetter() {
		if (userSession != null && userSession.getAuthorizations() != null) {
			for(Authorization authorization : userSession.getAuthorizations()){
				if(authorization.getRole().equals(Role.WARN_LETTER)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isBalanceReport() {
		if (userSession != null && userSession.getAuthorizations() != null) {
			for(Authorization authorization : userSession.getAuthorizations()){
				if(authorization.getRole().equals(Role.BALANCE_REPORT)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isMonthlyReport() {
		if (userSession != null && userSession.getAuthorizations() != null) {
			for(Authorization authorization : userSession.getAuthorizations()){
				if(authorization.getRole().equals(Role.MONTHLY_REPORT)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isAdmin() {
		if (userSession != null && userSession.getAuthorizations() != null) {
			for(Authorization authorization : userSession.getAuthorizations()){
				if(authorization.getRole().equals(Role.ADMIN)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isLoggedIn(){
		return userSession != null;
	}
}
