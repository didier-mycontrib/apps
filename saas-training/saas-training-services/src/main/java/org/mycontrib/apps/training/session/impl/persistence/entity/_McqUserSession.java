
package org.mycontrib.apps.training.session.impl.persistence.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasUser;

@Entity
  @Table(name="McqUserSession")
public  class _McqUserSession  {

	private Integer nbGoodResponses;

	private String submitDateTime;

	private String pctGoodResponses;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long mcqUserSessionId;
	
	@ElementCollection
    @CollectionTable(name="QuestionResponseChoice", joinColumns={@JoinColumn(name="mcqUserSessionId")}) 
	private  List<_QuestionResponseChoice> choices;
    
    //Start of user code relationship_users_officialSession
     @ManyToOne
     @JoinColumn(name="ref_officialSession")
    //End of user code
	private _McqOfficialSession officialSession;
    //Start of user code relationship_mcqSession_user
     @ManyToOne
     @JoinColumn(name="ref_user")
    //End of user code
     
	private _SaasUser user;
    //Start of user code relationship_mcqSession_mcq
     @ManyToOne
     @JoinColumn(name="ref_mcq")
    //End of user code
     
	private _Mcq mcq;

	public _McqUserSession(){
		super(); 
	}      
	public String toString(){
		return "McqUserSession("+ "nbGoodResponses=" + nbGoodResponses+","+ "submitDateTime=" + submitDateTime+","+ "mcq=" + mcq+","+ "officialSession=" + officialSession+","+ "pctGoodResponses=" + pctGoodResponses+","+ "user=" + user+","+ "mcqUserSessionId=" + mcqUserSessionId + ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return mcqUserSessionId;
 		//End of user code
 	}
	public Integer getNbGoodResponses() {
		return this.nbGoodResponses; 
	}
	public void setNbGoodResponses(Integer nbGoodResponses){
		this.nbGoodResponses=nbGoodResponses;
	}
	public String getSubmitDateTime() {
		return this.submitDateTime; 
	}
	public void setSubmitDateTime(String submitDateTime){
		this.submitDateTime=submitDateTime;
	}
	
	public String getPctGoodResponses() {
		return this.pctGoodResponses; 
	}
	public void setPctGoodResponses(String pctGoodResponses){
		this.pctGoodResponses=pctGoodResponses;
	}
	
	public Long getMcqUserSessionId() {
		return this.mcqUserSessionId; 
	}
	public void setMcqUserSessionId(Long mcqUserSessionId){
		this.mcqUserSessionId=mcqUserSessionId;
	}
	public List<_QuestionResponseChoice> getChoices() {
		return choices;
	}
	public void setChoices(List<_QuestionResponseChoice> choices) {
		this.choices = choices;
	}
	public _McqOfficialSession getOfficialSession() {
		return this.officialSession;
	}
	public void setOfficialSession(_McqOfficialSession officialSession){
		this.officialSession=officialSession;
	}
	public _SaasUser getUser() {
		return this.user;
	}
	public void setUser(_SaasUser user){
		this.user=user;
	}
	public _Mcq getMcq() {
		return this.mcq;
	}
	public void setMcq(_Mcq mcq){
		this.mcq=mcq;
	}

        
} 
