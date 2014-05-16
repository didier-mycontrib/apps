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
	
	protected String filtrage="private_only"; //or "private_and_public" or "..."
	
	protected List<McqSubject> subjectList;
	protected Long subjectId; //to choose/select
	
	protected List<Mcq> mcqList;
	protected Long mcqId; //to choose/select
	protected McqSubject currentSubject=null;
	protected McqSubject speudoSubject = new McqSubject();
	protected boolean noReset=false;
	
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
			if(noReset==false)
			   {//noReset=false by default ---> need to reset:
				initSubjectListSelonFiltrage();
			
				this.mcqList=null;//reset 
				this.subjectId=0L;
				this.mcqId=null;
				this.currentSubject=speudoSubject;
			   }
		}
	}
	
	protected void initSubjectListSelonFiltrage(){
		Long ownerSaasOrgId = getSaasMBean().getSaasOrg().getIdOrg();
		subjectList=getServiceMcqChooser().getSubjectList(ownerSaasOrgId);
		
		if(this.filtrage.equals("private_and_public")){
			//ajouter aussi les sujets publics des autres organisations:
			List<McqSubject> allPublicSubjectList = getServiceMcqChooser().getSubjectList(null);
			//System.out.println("allPublicSubject:" + allPublicSubjectList);
			for(McqSubject publicSubject : allPublicSubjectList){
				if(!publicSubject.getOwnerOrgId().equals(ownerSaasOrgId)){
					publicSubject.setTitle(publicSubject.getTitle() + " (public)");
					subjectList.add(publicSubject);
				}
			}
		}
		
		speudoSubject.setOwnerOrgId(ownerSaasOrgId);
		speudoSubject.setSubjectId(0L);
		speudoSubject.setTitle("???");
		subjectList.add(0, speudoSubject);
	}
	
	public List<McqSubject> getSubjectList() {
		if(subjectList==null){
			initSubjectListSelonFiltrage();
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

	public boolean isNoReset() {
		return noReset;
	}

	public void setNoReset(boolean noReset) {
		this.noReset = noReset;
	}

	public String getFiltrage() {
		return filtrage;
	}

	public void setFiltrage(String filtrage) {
		this.filtrage = filtrage;
	}

}
