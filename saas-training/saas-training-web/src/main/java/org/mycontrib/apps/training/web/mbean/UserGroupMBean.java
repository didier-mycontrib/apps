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
import org.springframework.beans.BeanUtils;

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
	private SaasGroup speudoGroup = new SaasGroup();//????
	private SaasUser speudoUser = new SaasUser();//????
	
	private List<SaasGroup> groupList=new ArrayList<SaasGroup>();//always not null
	
	private String newGroupName;
	private String newGroupInfo;
	
	private Long userId; //to select
	private SaasUser user; //current (selected) user
	private SaasRole saasRole; //current (selected) user.saasAccount.saasRole
	
	
	
	private List<SaasUser> userList = new ArrayList<SaasUser>();//always not null
	
	
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
		//System.out.println("onUserChange , new userId="+userId);
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
						userList.add(0, speudoUser);
						if(userList.size()==1){
							this.user=speudoUser;
							this.userId=0L;
						}
						break;
					}
				}
			}
			else{
				this.userList=new ArrayList<SaasUser>();
				userList.add(0, speudoUser);
				this.group=null;
				initSpeudoUser();
				this.user=speudoUser;
				this.userId=0L;
			}
		}
	}
	
	private void initSpeudoUser(){
		speudoUser.setUserId(0L);
		speudoUser.setFirstName("?");
		speudoUser.setLastName("new_user");
		speudoUser.setInfo("?");
		SaasRoleAccount newSaasRoleAccount = new SaasRoleAccount();
		newSaasRoleAccount.setGeneric(false);
		newSaasRoleAccount.setUserName("?");
		newSaasRoleAccount.setPassword("?");
		newSaasRoleAccount.setSaasOrg(saasOrg);
		newSaasRoleAccount.setSaasRole(saasRole.USER_OF_ORG);
		speudoUser.setSaasAccount(newSaasRoleAccount);
        this.saasRole=newSaasRoleAccount.getSaasRole();
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
		speudoGroup.setIdGroup(0L);
		speudoGroup.setUserList(new ArrayList<SaasUser>());
		speudoGroup.setInfo("...");
		speudoGroup.setName("???");
		groupList.add(0, speudoGroup);
		this.group=speudoGroup;
		this.groupId=0L;
		
		initSpeudoUser();
		userList.add(0, speudoUser);
		this.user=speudoUser;
		this.userId=0L;
	}
	}
	
	public void onAddGroup(ActionEvent event){
		System.out.println("adding newGroup , newGroupName=" + newGroupName);
	
		if(newGroupName!=null && newGroupName.length()>0){
			//+ ajout en base
			Long newGroupId=serviceSaasUserGroupManager.addSaasGroup(orgId, newGroupName, newGroupInfo);
			//ajout liste locale
			SaasGroup newGroup= new SaasGroup();
			newGroup.setIdGroup(newGroupId);newGroup.setName(newGroupName);newGroup.setInfo(newGroupInfo);
			groupList.add(newGroup);
			//selection
			this.groupId=newGroup.getIdGroup();
			
			this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(newGroupId);
			this.userList.add(speudoUser);
			this.user=speudoUser;
			this.userId=0L;
			//for next adding:
			this.newGroupName=null;
		}
	}
	
	public void onDeleteGroup(ActionEvent event){
		
			/*if(orgId!=null){
			   //suppression	
			   serviceSaasOrgManager.deleteSaasOrg(this.orgId);
			   //reactualisation liste:
			   orgList = serviceSaasOrgManager.findAllSaasOrg();
			}
			this.confirmDelete =false; //for next deletion*/
		
	}
	
	public String updateUser(){
		String suite=null;
		if(this.user!=null && this.user.getUserId() != 0L){
			//update of user:
			this.user.getSaasAccount().setSaasRole(this.saasRole);
			serviceSaasUserGroupManager.updateSaasUser(this.user);
			//reactualisation valeurs au sein de la liste:
			if(this.groupId!=null){
			    this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
			    this.userList.add(0,speudoUser);
			}
		}
		return suite;
	}
	
	
	
	public void onAddNewUser(ActionEvent event){
		System.out.println("onAddNewUser with user=" + user);
		try {
			if(this.user!=null  && this.user.getUserId() == 0L){
			   //adding of new user:
				
				//1.cloner d'abord (new) user=speudoUser ???
				SaasUser newUser = this.user;/*new SaasUser();
				SaasRoleAccount newSaasRoleAccount = new SaasRoleAccount();
				newUser.setSaasAccount(newSaasRoleAccount);
				BeanUtils.copyProperties(this.user, newUser);
				BeanUtils.copyProperties(this.user.getSaasAccount(), newUser.getSaasAccount());*/
				newUser.setUserId(null);//for new insertion / auto_incrementation
				newUser.getSaasAccount().setSaasRole(this.saasRole);
				
				this.userId=serviceSaasUserGroupManager.addSaasUser(groupId, newUser);
				this.user=serviceSaasUserGroupManager.getSaasUserById(userId);
				
				System.out.println("in onAddNewUser : newUserId="+this.userId);
				initSpeudoUser();//reinit for next add
				//reactualisation valeurs au sein de la liste:
				if(this.groupId!=null){
				    this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
				    this.userList.add(0,speudoUser);				   
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String onDeleteUser(ActionEvent event){
		String suite=null;
		if(this.user!=null ){
			//delete of user:
			serviceSaasUserGroupManager.deleteSaasUser(this.user.getUserId());
			//reactualisation valeurs au sein de la liste:
			if(this.groupId!=null){
			    this.userList=serviceSaasUserGroupManager.findSaasUsersOfGroup(groupId);
			    initSpeudoUser();
				userList.add(0, speudoUser);
				this.user=speudoUser;
				this.userId=0L;
			}
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
	public String getNewGroupInfo() {
		return newGroupInfo;
	}
	public void setNewGroupInfo(String newGroupInfo) {
		this.newGroupInfo = newGroupInfo;
	}
	
	

}
