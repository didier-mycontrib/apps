package org.mycontrib.apps.training.web.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;

import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasOrg;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;

/* SaasMBean :  JSF ManagedBean for Saas login */

@ManagedBean
@SessionScoped
public class SaasMBean {
	
	private String saasOrgName; //a saisir (pour recherche SaasOrg de nom proche)
	private List<SaasOrg> existingSaasOrgList;
	private SaasOrg saasOrg; //selon choix et login
	private boolean genericUserRoleAccount=true;  //true by default
	private String username;//a verifier
	private String password;//a verifier
	private SaasRoleAccount validSaasRoleAccount; //avec sous parties "userName", "password" , "saasRole" 
	private List<SaasRole> roleList;
	private SaasRole saasRole;//a verifier
	
	private String userNameStyle="visibility:hidden";//by default
	
	private void adjustUserNameStyle(){
		if(genericUserRoleAccount==true)
			userNameStyle="visibility:hidden";
		else userNameStyle="visibility:visible";
	}
	
	public SaasRole getSaasRole() {
		return saasRole;
	}

	public String getUserNameStyle() {
		return userNameStyle;
	}

	public void setUserNameStyle(String userNameStyle) {
		this.userNameStyle = userNameStyle;
	}

	public void setSaasRole(SaasRole saasRole) {
		this.saasRole = saasRole;
	}

	@ManagedProperty(value="#{serviceSaasOrgManager}")
		private SaasOrgManager serviceSaasOrgManager;
	
	
	
	public void initSaasBean(ComponentSystemEvent event){
		//System.out.println("postBack:"+FacesContext.getCurrentInstance().isPostback());
		//System.out.println("isAjaxRequest:"+FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest());
		//FacesContext.getCurrentInstance().isPostback()
		if(!FacesContext.getCurrentInstance().isPostback()){
			if(existingSaasOrgList==null){
				existingSaasOrgList = serviceSaasOrgManager.findAllSaasOrg();
				System.out.println("initSaasBean: nb org=" + existingSaasOrgList.size());
			}
			if(roleList==null){
				roleList= new ArrayList<SaasRole>();
				roleList.add(SaasRole.USER_OF_ORG);roleList.add(SaasRole.AUTHOR_OF_ORG);
				roleList.add(SaasRole.ADMIN_OF_ORG);roleList.add(SaasRole.ADMIN_OF_SAAS);
			}
		}
	}
	
	public String doFindOrgName(){
		String suite=null;
		saasOrg=null;
		if(saasOrgName!=null && saasOrgName.length()>0){
		  for(SaasOrg org :existingSaasOrgList ){
			if(org.getName().toLowerCase().equals(saasOrgName.toLowerCase())){
				saasOrg=org;
				saasOrgName = org.getName(); //exact name
				if(saasOrg.getInfo()==null){
					saasOrg.setInfo("org="+org.getName());
				}
				break;
			}
		  }
		}
		if(saasOrg==null){
			saasOrgName="???";
		}
		//System.out.println("saasOrg:" + saasOrg);
		adjustUserNameStyle();
		return suite;
	}
	
	public String disconnect(){
		String suite="welcome";//.xhtml
		this.saasOrg=null;
		this.password=null;
		this.username=null;
		this.validSaasRoleAccount=null;
		this.saasOrgName=null;
		return suite;
	}
	
	public String toWelcomeOrMainMenu(){
		String suite=null;
		if(validSaasRoleAccount==null)
			suite="welcome";//.xhtml
		else 
			suite="mainMenu";//.xhtml
		return suite;
	}
	
	public String doLogin(){
		String suite=null;
	
		SaasRoleAccount validRoleAccount = null;
		updateUsernameForGenericRoleAccount();
		//System.out.println("doLogin() with username="+username +" and password="+password);
		if(saasOrg==null){
			doFindOrgName();
		}
		Long idSaasOrg=null;//may stay null for ADMIN_OF_SAAS (without org)
		if(saasOrg!=null){
			idSaasOrg=saasOrg.getIdOrg();
		}
		validRoleAccount = 
				serviceSaasOrgManager.findSaasRoleAccountByUsernameAndPassword(idSaasOrg,this.username, this.password);
		if(validRoleAccount!=null){
			System.out.println("validSaasRoleAccount:"+validSaasRoleAccount);
			this.validSaasRoleAccount=validRoleAccount;
			saasRole=validSaasRoleAccount.getSaasRole();  //???
			suite="mainMenu"; //.xhtml
		}
		else{
		adjustUserNameStyle();
		}
		return suite;
	}
	
	public boolean getHasAdminOfOrgRole(){
		if(validSaasRoleAccount!=null && validSaasRoleAccount.getSaasRole()==SaasRole.ADMIN_OF_ORG)
			return true;
		else return false;
	}
	
	public boolean getHasAuthorOfOrgRole(){
		if(validSaasRoleAccount!=null && (validSaasRoleAccount.getSaasRole()==SaasRole.AUTHOR_OF_ORG
				                      || validSaasRoleAccount.getSaasRole()==SaasRole.ADMIN_OF_ORG))
			return true;
		else return false;
	}
	
	public boolean getHasUserOfOrgRole(){
		if(validSaasRoleAccount!=null && (validSaasRoleAccount.getSaasRole()==SaasRole.USER_OF_ORG
									|| validSaasRoleAccount.getSaasRole()==SaasRole.AUTHOR_OF_ORG
				                      || validSaasRoleAccount.getSaasRole()==SaasRole.ADMIN_OF_ORG))
			return true;
		else return false;
	}
	
	public boolean getHasAdminOfSaasRole(){
		if(validSaasRoleAccount!=null && validSaasRoleAccount.getSaasRole()==SaasRole.ADMIN_OF_SAAS)
			return true;
		else return false;
	}
	
	private void updateUsernameForGenericRoleAccount(){
		if(this.genericUserRoleAccount){
			username=serviceSaasOrgManager.getGenericUserName(saasRole, saasOrgName);
		}
		//System.out.println("generic:" + genericUserRoleAccount + ", username=" + username);
	}
	

	
	public String getSaasOrgName() {
		return saasOrgName;
	}
	public void setSaasOrgName(String saasOrgName) {
		this.saasOrgName = saasOrgName;
	}
	public SaasOrg getSaasOrg() {
		return saasOrg;
	}
	public void setSaasOrg(SaasOrg saasOrg) {
		this.saasOrg = saasOrg;
	}
	
	public SaasOrgManager getServiceSaasOrgManager() {
		return serviceSaasOrgManager;
	}

	public void setServiceSaasOrgManager(SaasOrgManager serviceSaasOrgManager) {
		this.serviceSaasOrgManager = serviceSaasOrgManager;
	}

	public List<SaasRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SaasRole> roleList) {
		this.roleList = roleList;
	}

	public boolean isGenericUserRoleAccount() {
		return genericUserRoleAccount;
	}

	public void setGenericUserRoleAccount(boolean genericUserRoleAccount) {
		this.genericUserRoleAccount = genericUserRoleAccount;
	}

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

	public SaasRoleAccount getValidSaasRoleAccount() {
		return validSaasRoleAccount;
	}

	public void setValidSaasRoleAccount(SaasRoleAccount validSaasRoleAccount) {
		this.validSaasRoleAccount = validSaasRoleAccount;
	}


	
	

	
	
}
