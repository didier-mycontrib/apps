
package org.mycontrib.apps.training.session.impl.persistence.entity;
//Start of user code java_imports
	import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasGroup;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
    
//End of user code
@Entity
  @Table(name="McqOfficialSession")
public  class _McqOfficialSession  {

	private String duration;

	private String startDateTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idSession;

	
    //Start of user code relationship_officialSession_mcq
     @ManyToOne
     @JoinColumn(name="ref_mcq")
    //End of user code
	private _Mcq mcq;
     
    //Start of user code relationship_mcqOfficialSession_assignedGroup
     @ManyToOne
     @JoinColumn(name="ref_assignedGroup")
    //End of user code
	private _SaasGroup assignedGroup;
     
    //Start of user code relationship_mcqOfficialSession_saasOrg
     @ManyToOne
     @JoinColumn(name="ref_saasOrg")
    //End of user code
	private _SaasOrg saasOrg;

	public _McqOfficialSession(){
		super(); 
	}      
	public String toString(){
		return "McqOfficialSession("+ "duration=" + duration+","+ "saasOrg=" + saasOrg+","+ "mcq=" + mcq+","+ "idSession=" + idSession+","+ "startDateTime=" + startDateTime+","+ "assignedGroup=" + assignedGroup + ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return idSession;
 		//End of user code
 	}
	public String getDuration() {
		return this.duration; 
	}
	public void setDuration(String duration){
		this.duration=duration;
	}
	
	public String getStartDateTime() {
		return this.startDateTime; 
	}
	public void setStartDateTime(String startDateTime){
		this.startDateTime=startDateTime;
	}
	public Long getIdSession() {
		return this.idSession; 
	}
	public void setIdSession(Long idSession){
		this.idSession=idSession;
	}
	
	public _Mcq getMcq() {
		return this.mcq;
	}
	public void setMcq(_Mcq mcq){
		this.mcq=mcq;
	}
	public _SaasGroup getAssignedGroup() {
		return this.assignedGroup;
	}
	public void setAssignedGroup(_SaasGroup assignedGroup){
		this.assignedGroup=assignedGroup;
	}
	public _SaasOrg getSaasOrg() {
		return this.saasOrg;
	}
	public void setSaasOrg(_SaasOrg saasOrg){
		this.saasOrg=saasOrg;
	}

        
} 
