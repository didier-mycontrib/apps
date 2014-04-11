package org.mycontrib.apps.training.saasOrg.itf.domain.dto;

import java.util.List;

public  class SaasGroup  {
	private Long idGroup;
	private List<SaasUser> userList;
	private String info;
	private String name;
	//private SaasOrg saasOrg;


	public SaasGroup(){
		super(); 
	}      
	public String toString(){
		return "SaasGroup("+ "idGroup=" + idGroup+","+ "userList=" + userList+","+ "info=" + info+","+ "name=" + name + ")";
	}
           
	public Long getIdGroup() {
		return this.idGroup;
	}
	public void setIdGroup(Long idGroup){
		this.idGroup=idGroup;
	}
	public List<SaasUser> getUserList() {
		return this.userList;
	}
	public void setUserList(List<SaasUser> userList){
		this.userList=userList;
	}
	public String getInfo() {
		return this.info;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	/*
	public SaasOrg getSaasOrg() {
		return this.saasOrg;
	}
	public void setSaasOrg(SaasOrg saasOrg){
		this.saasOrg=saasOrg;
	}*/

        
}
  
