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

import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasGroup;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasOrg;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasUser;
import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasUserGroupManager;
import org.mycontrib.generic.exception.GenericException;

//JSF ManagedBean for admin/config groups and users of SaasOrg
@ManagedBean
@SessionScoped
public class UserGroupMBean {
	
	@ManagedProperty(value="#{serviceSaasUserGroupManager}")
	private SaasUserGroupManager serviceSaasUserGroupManager;
	
	@ManagedProperty(value="#{serviceSaasOrgManager}")
	private SaasOrgManager serviceSaasOrgManager;
	
	private List<SaasRole> roleList;
	
	private Long orgId; //to configure
	
	private Long groupId; //to select
	private SaasGroup group; //current (selected) group
	
	private List<SaasGroup> groupList;
	
	private String newGroupName;
	
	private Long userId; //to select
	private SaasUser user; //current (selected) user
	private SaasRole saasRole; //current (selected) user.saasAccount.saasRole
	
	
	
	private List<SaasUser> userList;
	
	private boolean confirmDelete=false;
	
	
	private SaasOrg saasOrg; //current org to update/...
	
	
	public SaasUserGroupManager getServiceSaasUserGroupManager() {
		return serviceSaasUserGroupManager;
	}
	public void setServiceSaasUserGroupManager(
			SaasUserGroupManager serviceSaasUserGroupManager) {
		this.serviceSaasUserGroupManager = serviceSaasUserGroupManager;
	}
	
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
	
	public void onUserChange(ValueChangeEvent event){
		this.userId=(Long)event.getNewValue();
		System.out.println("onUserChange , new userId="+userId);
		if(userId!=null){
				for(SaasUser u : userList){
					if(u.getUserId().equals(userId)){
						this.user=u;
						if(u.getSaasAccount()!=null)
							this.saasRole=u.getSaasAccount().getSaasRole();
						break;
					}
				}
			}else{
				this.user=null;
				this.saasRole=null;
			}
	}
	
	public void onGroupChange(ValueChangeEvent event){
		this.groupId=(Long)event.getNewValue();
		System.out.println("onGroupChange , new groupId="+groupId);
		if(groupId!=null){
			if(groupId>0){
				for(SaasGroup g : groupList){
					if(g.getIdGroup().equals(groupId)){
						this.group=g;
						this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
						if(userList.size()==0){
							this.user=null;
							this.userId=null;
						}
						break;
					}
				}
			}
			else{
				this.userList=null;
				this.group=null;
				this.user=null;
				this.userId=null;
			}
		}
	}
	
	
	
	public void initSaasBean(ComponentSystemEvent event){
	//System.out.println("AdminOrgMBean.initSaasBean() was called with event:"+event);
	//System.out.println("postBack:"+FacesContext.getCurrentInstance().isPostback());
	//System.out.println("isAjaxRequest:"+FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest());
	//NB: ajaxRequest is a (partial) postBack
	if(!FacesContext.getCurrentInstance().isPostback()){
		if(this.orgId!=null){
			this.saasOrg = serviceSaasOrgManager.getSaasOrgById(orgId);
			this.groupList=serviceSaasUserGroupManager.findSaasGroupsOfOrg(orgId);
		}
		if(roleList==null){
			roleList= new ArrayList<SaasRole>();
			roleList.add(SaasRole.USER_OF_ORG);roleList.add(SaasRole.AUTHOR_OF_ORG);
			roleList.add(SaasRole.ADMIN_OF_ORG);
		}
	}
	}
	
	public void onAddGroup(ActionEvent event){
		System.out.println("adding newGroup , newGroupName=" + newGroupName);
	
		if(newGroupName!=null && newGroupName.length()>0){
			//+ ajout en base
			Long newGroupId=serviceSaasUserGroupManager.addSaasGroup(orgId, newGroupName, "...");
			//ajout liste locale
			SaasGroup newGroup= new SaasGroup();
			newGroup.setIdGroup(newGroupId);newGroup.setName(newGroupName);newGroup.setInfo("...");
			groupList.add(newGroup);
			//selection
			this.groupId=newGroup.getIdGroup();
			//for next adding:
			this.newGroupName=null;
		}
	}
	
	public void onDeleteGroup(ActionEvent event){
		if(this.confirmDelete){
			/*if(orgId!=null){
			   //suppression	
			   serviceSaasOrgManager.deleteSaasOrg(this.orgId);
			   //reactualisation liste:
			   orgList = serviceSaasOrgManager.findAllSaasOrg();
			}
			this.confirmDelete =false; //for next deletion*/
		}
	}
	
	public String updateUser(){
		String suite=null;
		if(this.user!=null ){
			//update of user:
			this.user.getSaasAccount().setSaasRole(this.saasRole);
			serviceSaasUserGroupManager.updateSaasUser(this.user);
			//reactualisation valeurs au sein de la liste:
			if(this.groupId!=null){
			    this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
			}
		}
		return suite;
	}
	
	public void onEditNewUser(ActionEvent event){
		System.out.println("onEditNewUser");
		this.user = new SaasUser();
		user.setFirstName("?");user.setLastName("?");
		SaasRoleAccount newSaasRoleAccount = new SaasRoleAccount();
		newSaasRoleAccount.setGeneric(false);
		newSaasRoleAccount.setUserName("?");
		newSaasRoleAccount.setPassword("?");
		newSaasRoleAccount.setSaasOrg(saasOrg);
		newSaasRoleAccount.setSaasRole(saasRole.USER_OF_ORG);
        user.setSaasAccount(newSaasRoleAccount);
        this.saasRole=newSaasRoleAccount.getSaasRole();
		this.userId=null; //not yet added , not persistent
	}
	
	public void onAddNewUser(ActionEvent event){
		System.out.println("onAddNewUser with user=" + user + " and userId= " + userId);
		try {
			if(this.user!=null ){
				//adding of new user:
				this.user.getSaasAccount().setSaasRole(this.saasRole);
				this.userId= serviceSaasUserGroupManager.addSaasUser(groupId, this.user);
				System.out.println("in onAddNewUser : newUserId="+userId);
				//reactualisation valeurs au sein de la liste:
				if(this.groupId!=null){
				    this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String deleteUser(){
		String suite=null;
		if(this.confirmDelete && this.user!=null ){
			//delete of user:
			serviceSaasUserGroupManager.deleteSaasUser(this.user.getUserId());
			//reactualisation valeurs au sein de la liste:
			if(this.groupId!=null){
			    this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
			}
			this.confirmDelete=false; //for next delete
		}
		//NB: besoin de remonter un message d'erreur si suppression impossible (car contrainte d'intégrité en base !!!) 
		return suite;
	}

	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public boolean isConfirmDelete() {
		return confirmDelete;
	}
	public void setConfirmDelete(boolean confirmDelete) {
		this.confirmDelete = confirmDelete;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public List<SaasGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<SaasGroup> groupList) {
		this.groupList = groupList;
	}
	public String getNewGroupName() {
		return newGroupName;
	}
	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}
	public SaasGroup getGroup() {
		return group;
	}
	public void setGroup(SaasGroup group) {
		this.group = group;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public SaasUser getUser() {
		return user;
	}
	public void setUser(SaasUser user) {
		this.user = user;
	}
	public List<SaasUser> getUserList() {
		return userList;
	}
	public void setUserList(List<SaasUser> userList) {
		this.userList = userList;
	}
	public List<SaasRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<SaasRole> roleList) {
		this.roleList = roleList;
	}
	
	public SaasRole getSaasRole() {
		return saasRole;
	}
	public void setSaasRole(SaasRole saasRole) {
		this.saasRole = saasRole;
	}
	
	

}
