
package org.mycontrib.apps.training.saasOrg.impl.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
  @Table(name="SaasUser")
public  class _SaasUser  {


	private String info;

	private String lastName;

	
	private String firstName;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)

	private Long userId;
    //Start of user code relationship_user_saasAccount
    @OneToOne(cascade=CascadeType.PERSIST) //1-1 unidirectionnel
    @JoinColumn(name="ref_saasAccount")
    //End of user code
     
	private _SaasRoleAccount saasAccount;//specific, with generic=false

	public _SaasUser(){
		super(); 
	}      
	public String toString(){
		return "SaasUser("+ "info=" + info+","+ "lastName=" + lastName+","+ "firstName=" + firstName+","+ "userId=" + userId + ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return userId;
 		//End of user code
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
	public Long getUserId() {
		return this.userId; 
	}
	public void setUserId(Long userId){
		this.userId=userId;
	}
	public _SaasRoleAccount getSaasAccount() {
		return this.saasAccount;
	}
	public void setSaasAccount(_SaasRoleAccount saasAccount){
		this.saasAccount=saasAccount;
	}

        
} 
