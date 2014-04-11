
package org.mycontrib.apps.training.saasOrg.impl.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
  @Table(name="SaasGroup")
public  class _SaasGroup  {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long idGroup;

	private String info;

	private String name;
	
    //Start of user code relationship_saasGroup_userList
     @OneToMany //1-n undirectionnel
     @JoinColumn(name="ref_group")
    //End of user code
     
	private List<_SaasUser> userList;

	public _SaasGroup(){
		super(); 
	}      
	public String toString(){
		return "SaasGroup("+ "idGroup=" + idGroup+","+ "info=" + info+","+ "name=" + name+ ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return idGroup;
 		//End of user code
 	}
	public Long getIdGroup() {
		return this.idGroup; 
	}
	public void setIdGroup(Long idGroup){
		this.idGroup=idGroup;
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
	public List<_SaasUser> getUserList() {
		return this.userList; 
	}
	public void setUserList(List<_SaasUser> userList){
		this.userList=userList;
	}


        
} 
