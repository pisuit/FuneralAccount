package fun.listener;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fun.utils.Constants;

public class MyPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {		
//		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		System.out.println(request.getServletPath());	
//		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.AUTH_KEY) == null && !request.getServletPath().equals("/pages/login.jsf")){
//			 try {
//				 FacesContext.getCurrentInstance().getExternalContext().redirect("pages/login.jsf");
//			 } catch (Exception e) {
//				// TODO: handle exception
//			}
//		}	
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		FacesContext facesCtx = arg0.getFacesContext(); 
		ExternalContext extCtx = facesCtx.getExternalContext(); 
		HttpSession session = (HttpSession)extCtx.getSession(false); 
		boolean newSession = (session == null)|| (session.isNew()); 
		boolean postback = !extCtx.getRequestParameterMap().isEmpty(); 
		boolean timedout = postback && newSession; 
		if(timedout) { 
		Application app = facesCtx.getApplication(); 
		ViewHandler viewHandler = app.getViewHandler(); 
		UIViewRoot view = viewHandler.createView(facesCtx,"pages//login.jsf"); 
		facesCtx.setViewRoot(view); 
		facesCtx.renderResponse(); 
		try { 
		viewHandler.renderView(facesCtx, view); 
		facesCtx.responseComplete(); 
		} catch(Throwable t) { 
		throw new FacesException("Session timed out", t); 
		} 
		} 
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}
	
}
