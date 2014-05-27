package org.mycontrib.apps.training.session.itf.domain.dto;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasUser;
/* session de qcm 
 * pour calculer le résultat (et éventuellement enregistrer celui ci)
 * sera non persisté si generic_user
 * sera persisté si non generic user (specific user of saas_org in saas_group)
 *   en mode "persisté" (sera eventuellement rattaché à une session_officielle)
 */
public  class McqUserSession  {
	private QuestionResponseChoice choices;
	private Mcq mcq;
	private McqOfficialSession officialSession;//usually null if no official 
	private String pctGoodResponses;
	private String submitDateTime;
	private Integer mcqUserSessionId;//id/pk
	private Integer nbGoodResponses;
	private SaasUser user;//user (non generic) who performed mcq
	
	public McqUserSession(){
		super(); 
	}      
	public String toString(){
		return "McqUserSession("+ "mcq=" + mcq+","+ "officialSession=" + officialSession+","+ "pctGoodResponses=" + pctGoodResponses+","+ "submitDateTime=" + submitDateTime+","+ "mcqUserSessionId=" + mcqUserSessionId+","+ "nbGoodResponses=" + nbGoodResponses+","+ "user=" + user + ")";
	}
           
	public QuestionResponseChoice getChoices() {
		return this.choices;
	}
	public void setChoices(QuestionResponseChoice choices){
		this.choices=choices;
	}
	public Mcq getMcq() {
		return this.mcq;
	}
	public void setMcq(Mcq mcq){
		this.mcq=mcq;
	}
	public McqOfficialSession getOfficialSession() {
		return this.officialSession;
	}
	public void setOfficialSession(McqOfficialSession officialSession){
		this.officialSession=officialSession;
	}
	public String getPctGoodResponses() {
		return this.pctGoodResponses;
	}
	public void setPctGoodResponses(String pctGoodResponses){
		this.pctGoodResponses=pctGoodResponses;
	}
	public String getSubmitDateTime() {
		return this.submitDateTime;
	}
	public void setSubmitDateTime(String submitDateTime){
		this.submitDateTime=submitDateTime;
	}
	public Integer getMcqUserSessionId() {
		return this.mcqUserSessionId;
	}
	public void setMcqUserSessionId(Integer mcqUserSessionId){
		this.mcqUserSessionId=mcqUserSessionId;
	}
	public Integer getNbGoodResponses() {
		return this.nbGoodResponses;
	}
	public void setNbGoodResponses(Integer nbGoodResponses){
		this.nbGoodResponses=nbGoodResponses;
	}
	public SaasUser getUser() {
		return this.user;
	}
	public void setUser(SaasUser user){
		this.user=user;
	}

        
}
  
