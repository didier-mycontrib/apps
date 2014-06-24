
package org.mycontrib.apps.training.saasOrg.impl.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;

@Entity
  @Table(name="SaasRoleAccount")
public  class _SaasRoleAccount  {

@Enumerated(EnumType.STRING)
	private SaasRole saasRole;

	private String email;

	private String userName;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)

	private Long idAccount;

	private String password;
	
	private Boolean generic;//if false --> specific

	public _SaasRoleAccount(){
		super(); 
	}      
	public String toString(){
		return "SaasRoleAccount("+ "saasRole=" + saasRole+","+ "email=" + email+","+ "userName=" + userName+","+ "idAccount=" + idAccount+","+ "password=" + password + ",generic=" + generic+  ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return idAccount;
 		//End of user code
 	}
	public SaasRole getSaasRole() {
		return this.saasRole; 
	}
	public void setSaasRole(SaasRole saasRole){
		this.saasRole=saasRole;
	}
	public String getEmail() {
		return this.email; 
	}
	public void setEmail(String email){
		this.email=email;
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
	public String getPassword() {
		return this.password; 
	}
	public void setPassword(String password){
		this.password=password;
	}
	public Boolean getGeneric() {
		return generic;
	}
	public void setGeneric(Boolean generic) {
		this.generic = generic;
	}
	

        
} 
