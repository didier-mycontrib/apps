package org.mycontrib.apps.training.web.mbean.common;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.web.mbean.SaasMBean;
import org.springframework.context.annotation.Bean;

public abstract class McqChooseCommon  {
	protected List<McqSubject> subjectList;
	protected Long subjectId; //to choose/select
	
	protected List<Mcq> mcqList;
	protected Long mcqId; //to choose/select
	protected McqSubject currentSubject=null;
	
	public abstract McqChooser getServiceMcqChooser() ;
	
	public SaasMBean getSaasMBean(){
		SaasMBean bean = null;
		bean = (SaasMBean)FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("saasMBean");
		return bean;
	}
	
	
	
	public void initSujectList(ComponentSystemEvent event){
		//NB: ajaxRequest is a (partial) postBack
		if(!FacesContext.getCurrentInstance().isPostback()){
			Long ownerSaasOrgId = getSaasMBean().getSaasOrg().getIdOrg();
			subjectList=getServiceMcqChooser().getSubjectList(ownerSaasOrgId);
			this.mcqList=null;//reset 
			this.subjectId=null;
			this.mcqId=null;
			this.currentSubject=null;
		}
	}
	
	public List<McqSubject> getSubjectList() {
		if(subjectList==null){
			Long ownerSaasOrgId = getSaasMBean().getSaasOrg().getIdOrg();
			subjectList=getServiceMcqChooser().getSubjectList(ownerSaasOrgId);
		}
		return subjectList;
	}
	
	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public List<Mcq> getMcqList() {
		return mcqList;
	}

	public void setMcqList(List<Mcq> mcqList) {
		this.mcqList = mcqList;
	}

	public Long getMcqId() {
		return mcqId;
	}

	public void setMcqId(Long mcqId) {
		this.mcqId = mcqId;
	}
	
	public void setSubjectList(List<McqSubject> subjectList) {
		this.subjectList = subjectList;
	}
	

	public McqSubject getCurrentSubject() {
		return currentSubject;
	}


	public void setCurrentSubject(McqSubject currentSubject) {
		this.currentSubject = currentSubject;
	}

	

}
