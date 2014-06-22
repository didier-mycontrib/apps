package org.mycontrib.apps.training.saasOrg.itf.domain.dto;

import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;

public  class SaasRoleAccount  {
	private String email;
	private SaasRole saasRole;
	private String password;
	private String userName;
	private Long idAccount;
	private SaasOrg saasOrg;
	private Boolean generic;//if false --> specific
	
	public static final String GENERIC_USER_OF_ORG_PREFIX="userOf_";
	public static final String GENERIC_AUTHOR_OF_ORG_PREFIX="authorOf_";
	public static final String GENERIC_ADMIN_OF_ORG_PREFIX="adminOf_";
	
	

	public SaasRoleAccount(){
		super(); this.generic = Boolean.TRUE; //by default (not null by default)
	}      
	public String toString(){
		return "SaasRoleAccount("+ "email=" + email+","+ "saasRole=" + saasRole+","+ "password=" + password+","+ "userName=" + userName+","+ "idAccount=" + idAccount + ",generic=" + generic+ ")";
	}
           
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public SaasRole getSaasRole() {
		return this.saasRole;
	}
	public void setSaasRole(SaasRole saasRole){
		this.saasRole=saasRole;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public Long getIdAccount() {
		return this.idAccount;
	}
	public void setIdAccount(Long idAccount){
		this.idAccount=idAccount;
	}
	
	public SaasOrg getSaasOrg() {
		return this.saasOrg;
	}
	public void setSaasOrg(SaasOrg saasOrg){
		this.saasOrg=saasOrg;
	}
	public Boolean isGeneric() {
		return generic;
	}
	public void setGeneric(Boolean generic) {
		this.generic = generic;
	}
	
	

        
}
  
