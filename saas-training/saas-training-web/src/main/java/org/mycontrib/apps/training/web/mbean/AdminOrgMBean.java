package org.mycontrib.apps.training.web.mbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasOrg;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;

//JSF ManagedBean for admin/config of SaasOrg
@ManagedBean
@SessionScoped
public class AdminOrgMBean {
	
	@ManagedProperty(value="#{serviceSaasOrgManager}")
	private SaasOrgManager serviceSaasOrgManager;
	
	private List<SaasOrg> orgList;
	private Long orgId; //to select
	
	
	private String newOrgName;
	
	
	public String getNewOrgName() {
		return newOrgName;
	}
	public void setNewOrgName(String newOrgName) {
		this.newOrgName = newOrgName;
	}
	private SaasOrg saasOrg; //current org to update/...
	
	public SaasOrgManager getServiceSaasOrgManager() {
		return serviceSaasOrgManager;
	}
	public void setServiceSaasOrgManager(SaasOrgManager serviceSaasOrgManager) {
		this.serviceSaasOrgManager = serviceSaasOrgManager;
	}
	public SaasOrg getSaasOrg() {
		return saasOrg;
	}
	public void setSaasOrg(SaasOrg saasOrg) {
		this.saasOrg = saasOrg;
	}
	
	public void onOrgChange(ValueChangeEvent event){
		this.orgId=(Long)event.getNewValue();
		System.out.println("onOrgChange , new orgId="+orgId);
		if(orgId!=null){
			if(orgId>0){
				for(SaasOrg org : orgList){
					if(org.getIdOrg().equals(orgId)){
						this.saasOrg=org;
						break;
					}
				}
			}
			else{
				this.saasOrg=null;
			}
		}
	}
	
	
	public void initSaasBeanWithAllOrg(ComponentSystemEvent event){
	//System.out.println("AdminOrgMBean.initSaasBean() was called with event:"+event);
	//System.out.println("postBack:"+FacesContext.getCurrentInstance().isPostback());
	//System.out.println("isAjaxRequest:"+FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest());
	//NB: ajaxRequest is a (partial) postBack
	if(!FacesContext.getCurrentInstance().isPostback()){
		if(orgList==null){
			orgList = serviceSaasOrgManager.findAllSaasOrg();
			System.out.println("initSaasBean: nb org=" + orgList.size());
		}
	 }
	}
	
	public void initSaasBeanWithOneOrg(ComponentSystemEvent event){
		//System.out.println("AdminOrgMBean.initSaasBean() was called with event:"+event);
		//System.out.println("postBack:"+FacesContext.getCurrentInstance().isPostback());
		//System.out.println("isAjaxRequest:"+FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest());
		//NB: ajaxRequest is a (partial) postBack
		if(!FacesContext.getCurrentInstance().isPostback()){
			this.saasOrg = serviceSaasOrgManager.getSaasOrgById(this.orgId);
		 }
		}
	
	public void onAddOrg(ActionEvent event){
		System.out.println("adding newOrg:" + this.newOrgName);
		
		if(newOrgName!=null && newOrgName.length()>0){
			//+ ajout en base:
			Long newOrgId=serviceSaasOrgManager.addSaasOrg(newOrgName);
			serviceSaasOrgManager.buildGenericUsersForNewSaasOrg(newOrgId);
			//initialisation locale:
			this.saasOrg=serviceSaasOrgManager.getSaasOrgById(newOrgId);
			//ajout liste locale
			orgList.add(this.saasOrg);
			//selection
			this.orgId=saasOrg.getIdOrg();
			//for next adding:
			this.newOrgName=null;
		}
	}
	
	public void onDeleteOrg(ActionEvent event){
			if(orgId!=null){
			   //suppression	
			   serviceSaasOrgManager.deleteSaasOrg(this.orgId);
			   //reactualisation liste:
			   orgList = serviceSaasOrgManager.findAllSaasOrg();
			   this.orgId=null;
			   this.saasOrg=null;
			}
	}
	
	public String updateOrg(){
		String suite=null;
		if(saasOrg!=null){
		   serviceSaasOrgManager.updateSaasOrg(this.saasOrg);
		}
		return suite;
	}
	public List<SaasOrg> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<SaasOrg> orgList) {
		this.orgList = orgList;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	
	

}
