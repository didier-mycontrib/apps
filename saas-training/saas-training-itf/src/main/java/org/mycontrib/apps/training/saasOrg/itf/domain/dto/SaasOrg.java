package org.mycontrib.apps.training.saasOrg.itf.domain.dto;

import java.util.List;

public  class SaasOrg  {
	private String name;
	private Long idOrg;
	private String info;
	private List<SaasGroup> orgGroupList;
	private SaasRoleAccount genericUserOfOrgAccount;
	private SaasRoleAccount genericAdminOfOrgAccount;
	private SaasRoleAccount genericAuthorOfOrgAccount;
	

	public SaasOrg(){
		super(); 
	}      
	public String toString(){
		return "SaasOrg("+ "name=" + name+","+ "idOrg=" + idOrg+","+ "info=" + info
				+","+ "orgGroupList=" + orgGroupList+","+ "genericUserOfOrgAccount=" + genericUserOfOrgAccount
				+","+ "genericAdminOfOrgAccount=" + genericAdminOfOrgAccount 
				+","+ "genericAuthorOfOrgAccount=" + genericAuthorOfOrgAccount 
				+ ")";
	}
           
	public String getName() {
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public Long getIdOrg() {
		return this.idOrg;
	}
	public void setIdOrg(Long idOrg){
		this.idOrg=idOrg;
	}
	public String getInfo() {
		return this.info;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public List<SaasGroup> getOrgGroupList() {
		return this.orgGroupList;
	}
	public void setOrgGroupList(List<SaasGroup> orgGroupList){
		this.orgGroupList=orgGroupList;
	}
	public SaasRoleAccount getGenericUserOfOrgAccount() {
		return genericUserOfOrgAccount;
	}
	public void setGenericUserOfOrgAccount(SaasRoleAccount genericUserOfOrgAccount) {
		this.genericUserOfOrgAccount = genericUserOfOrgAccount;
	}
	public SaasRoleAccount getGenericAdminOfOrgAccount() {
		return genericAdminOfOrgAccount;
	}
	public void setGenericAdminOfOrgAccount(SaasRoleAccount genericAdminOfOrgAccount) {
		this.genericAdminOfOrgAccount = genericAdminOfOrgAccount;
	}
	public SaasRoleAccount getGenericAuthorOfOrgAccount() {
		return genericAuthorOfOrgAccount;
	}
	public void setGenericAuthorOfOrgAccount(
			SaasRoleAccount genericAuthorOfOrgAccount) {
		this.genericAuthorOfOrgAccount = genericAuthorOfOrgAccount;
	}

	
        
}
  
