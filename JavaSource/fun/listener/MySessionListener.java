package fun.listener;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.http.HttpResponse;
import org.jboss.security.SecurityContext;

import fun.utils.Constants;

public class MySessionListener implements HttpSessionListener {
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("session created : " + arg0.getSession().getId());;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		  HttpSession session = arg0.getSession();
		  System.out.println("session destroyed :" + session.getId() + " Logging out user...");
		  try {
			  prepareLogoutInfoAndLogoutActiveUser();
		  } catch(Exception e) {
			  System.out.println("error while logging out at session destroyed : " + e.getMessage());
		  }	
	}
	
	public void prepareLogoutInfoAndLogoutActiveUser(){
//		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.AUTH_KEY);
//		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
}
