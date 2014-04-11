package org.mycontrib.apps.training.saasOrg.itf.domain.dto;

public  class SaasUser  {
	private SaasRoleAccount saasAccount;//specific, with generic=false
	private Long userId;
	private String info;
	private String lastName;
	private String firstName;
	
	//private SaasGroup saasGroup;

	public SaasUser(){
		super(); 
	}      
	public String toString(){
		return "SaasUser("+ "userId=" + userId+","+ "saasAccount=" + saasAccount+","+ "info=" + info+","+ "lastName=" + lastName+","+ "firstName=" + firstName + ")";
	}
           
	public SaasRoleAccount getSaasAccount() {
		return this.saasAccount;
	}
	public void setSaasAccount(SaasRoleAccount saasAccount){
		this.saasAccount=saasAccount;
	}
	public Long getUserId() {
		return this.userId;
	}
	public void setUserId(Long userId){
		this.userId=userId;
	}
	public String getInfo() {
		return this.info;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	
        
}
  
