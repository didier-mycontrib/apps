
package org.mycontrib.apps.training.saasOrg.impl.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
  @Table(name="SaasOrg")
public  class _SaasOrg  {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long idOrg;

	private String info;


	private String name;
    //Start of user code relationship_saasOrg_orgGroupList
     @OneToMany //1-n undirectionnel
     @JoinColumn(name="ref_org")
    //End of user code
	private List<_SaasGroup> orgGroupList;

    @OneToOne //1-1 unidirectionnel
    @JoinColumn(name="ref_genericUserOfOrgAccount")
	private _SaasRoleAccount genericUserOfOrgAccount;
    
    @OneToOne //1-1 unidirectionnel
    @JoinColumn(name="ref_genericAdminOfOrgAccount")
	private _SaasRoleAccount genericAdminOfOrgAccount;
    
    
    @OneToOne //1-1 unidirectionnel
    @JoinColumn(name="ref_genericAuthorOfOrgAccount")
	private _SaasRoleAccount genericAuthorOfOrgAccount;

	public _SaasOrg(){
		super(); 
	}      
	public String toString(){
		return "SaasOrg("+ "idOrg=" + idOrg+","+  "info=" + info+","+ "name=" + name + ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return idOrg;
 		//End of user code
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
	
	public String getName() {
		return this.name; 
	}
	public void setName(String name){
		this.name=name;
	}
	public List<_SaasGroup> getOrgGroupList() {
		return this.orgGroupList;
	}
	public void setOrgGroupList(List<_SaasGroup> orgGroupList){
		this.orgGroupList=orgGroupList;
	}
	public _SaasRoleAccount getGenericUserOfOrgAccount() {
		return genericUserOfOrgAccount;
	}
	public void setGenericUserOfOrgAccount(_SaasRoleAccount genericUserOfOrgAccount) {
		this.genericUserOfOrgAccount = genericUserOfOrgAccount;
	}
	public _SaasRoleAccount getGenericAdminOfOrgAccount() {
		return genericAdminOfOrgAccount;
	}
	public void setGenericAdminOfOrgAccount(
			_SaasRoleAccount genericAdminOfOrgAccount) {
		this.genericAdminOfOrgAccount = genericAdminOfOrgAccount;
	}
	public _SaasRoleAccount getGenericAuthorOfOrgAccount() {
		return genericAuthorOfOrgAccount;
	}
	public void setGenericAuthorOfOrgAccount(
			_SaasRoleAccount genericAuthorOfOrgAccount) {
		this.genericAuthorOfOrgAccount = genericAuthorOfOrgAccount;
	}
	
        
} 
