package org.mycontrib.apps.training.web.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.session.itf.domain.dto.McqUserSession;
import org.mycontrib.apps.training.session.itf.domain.service.McqUserSessionManager;

@ManagedBean
@RequestScoped
public class UserSessionsMBean {
	
	@ManagedProperty(value="#{serviceMcqUserSessionManager}")
	private McqUserSessionManager serviceMcqUserSessionManager;
	
	private Long saasUserId; //userId of user to display sessions
	private List<McqUserSession> userSessions; //à afficher (partiellement)
	private Map<Long,List<McqUserSession>> mapUserSessions ;//rangement par qcmId
	
	public void initUserSessions(ComponentSystemEvent event){
		//NB: ajaxRequest is a (partial) postBack
		if(!FacesContext.getCurrentInstance().isPostback()){
		    userSessions=serviceMcqUserSessionManager.getMcqSessionsOfUser(saasUserId);
		    mapUserSessions=new HashMap<Long,List<McqUserSession>>();
		    for(McqUserSession sess: userSessions){
		    	List<McqUserSession> sessList = mapUserSessions.get(sess.getMcq().getId());
		    	if(sessList==null){
		    		sessList = new ArrayList<McqUserSession>();
		    		mapUserSessions.put(sess.getMcq().getId(), sessList);
		    	}
		    	sessList.add(sess);
		    }
		}
	}
	

	public McqUserSessionManager getServiceMcqUserSessionManager() {
		return serviceMcqUserSessionManager;
	}

	public void setServiceMcqUserSessionManager(
			McqUserSessionManager serviceMcqUserSessionManager) {
		this.serviceMcqUserSessionManager = serviceMcqUserSessionManager;
	}


	public Long getSaasUserId() {
		return saasUserId;
	}


	public void setSaasUserId(Long saasUserId) {
		this.saasUserId = saasUserId;
	}

/*
	public List<McqUserSession> getUserSessions() {
		return userSessions;
	}


	public void setUserSessions(List<McqUserSession> userSessions) {
		this.userSessions = userSessions;
	}


	public Map<Long, List<McqUserSession>> getMapUserSessions() {
		return mapUserSessions;
	}*/

    public List<List<McqUserSession>> getListUserSessions(){
    	return new ArrayList<List<McqUserSession>>(mapUserSessions.values());
    }

		
	
	
	
	

}
