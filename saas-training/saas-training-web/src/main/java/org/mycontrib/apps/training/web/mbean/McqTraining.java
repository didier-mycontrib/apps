package org.mycontrib.apps.training.web.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.ResponseMcq;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasUserGroupManager;
import org.mycontrib.apps.training.session.itf.domain.dto.McqUserSession;
import org.mycontrib.apps.training.session.itf.domain.service.McqUserSessionManager;
import org.mycontrib.apps.training.web.data.ResponseDetail;
import org.mycontrib.apps.training.web.mbean.common.McqAbstractMBean;


/* McqTraining : JSF2 ManagedBean for training (choose responses for questions of existing mcq)*/
@ManagedBean
@SessionScoped
public class McqTraining extends McqAbstractMBean {
	
	private String exclusiveResponseChoice; 
	private List<String> nonExclusiveResponseChoices =new ArrayList<String>();
	private McqUserSession mcqUserSession;//pour resultats
	
	@ManagedProperty(value="#{serviceMcqManager}")
	private McqManager serviceMcqManager;
	
	@ManagedProperty(value="#{serviceMcqUserSessionManager}")
	private McqUserSessionManager serviceMcqUserSessionManager;
	

	@ManagedProperty(value="#{serviceSaasUserGroupManager}")
	private SaasUserGroupManager serviceSaasUserGroupManager;
	
	private Map<Integer,String> mapNumQuestionChoice; //String = "A", "B" , ... en mode exclusif ou "A,B" , "B,D" en mode non exclusif
	
	private List<ResponseDetail> listeResponseDetails;
	
	public void initMcqTrainingForAnonymousGuest(ComponentSystemEvent event){
		if(!FacesContext.getCurrentInstance().isPostback()){
				//verifier que qcmId est bien l'id d'un qcm public
				if(serviceMcqManager.getMcqById(this.mcqId).getShared()){
					//ok
					performMcq();
				}
				else {
					//not public mcq , anonymous access forbidden !!
				}
		}
	}
	
	public String performMcq(){
		String suite=null;
		if(this.mcqId!=null){
			this.questionList=serviceMcqManager.getRandomQuestionSubList(this.mcqId);
			//this.questionList=serviceMcqManager.getQuestionList(this.mcqId);
		}
		//System.out.println("performMcq() ,mcqId= " + mcqId + ",questionList=" + questionList);
		if(this.questionList!=null && !this.questionList.isEmpty()){
			this.nbQuestions=this.questionList.size();
			this.currentQuestionIndex=0;
			this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
			this.exclusiveResponseChoice=null;
			this.mapNumQuestionChoice=new HashMap<Integer,String>();
			this.mcqUserSession =null;
		    suite = "performMcq";//.xhtml
		}
		return suite;
	}
	
	private void initExclusiveOrNotQuestionChoice(){
		 this.nonExclusiveResponseChoices.clear();
		 this.exclusiveResponseChoice=null;
		 String responseChoice = this.mapNumQuestionChoice.get(this.currentQuestion.getQuestionNumber()); 
		if(currentQuestion.getExclusiveResponse()!=null && currentQuestion.getExclusiveResponse().booleanValue()==true){
			//mode exclusif
		   this.exclusiveResponseChoice= responseChoice;
		}
		else if(responseChoice !=null){
		//mode non exclusif
		StringTokenizer st = new StringTokenizer(responseChoice,","); //pour decouper "A,B" ou "B,D"
		if(st!=null){
			String numResponse=null;
			do {
				    try {
						numResponse = st.nextToken();
					} catch (RuntimeException e) {
						numResponse=null;
						e.printStackTrace();
					}
					if(numResponse!=null)
						nonExclusiveResponseChoices.add(numResponse);
				}while(numResponse!=null);
			}
		}
	}
	
	private String moveToNextQuestion(){
		String suite=null;
		if(currentQuestionIndex < (nbQuestions-1) ){
			this.currentQuestionIndex++;
			this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
			initExclusiveOrNotQuestionChoice();
		}else{
			//end of questionList
			currentQuestionIndex=nbQuestions;//after last question ( currentQuestionIndex-- to return to last)
			suite="completeMcq"; //.xhtml
		}
		return suite;
	}
	
	public String moveToPreviousQuestion(){
		String suite=null;
		if(currentQuestionIndex==nbQuestions){
			//after last
			suite="performMcq"; //.xhtml
		}
		if(currentQuestionIndex > 0 ){
			this.currentQuestionIndex--;
			this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
			initExclusiveOrNotQuestionChoice();
		}
		return suite;
	}
	private void initListOfResponseDetails(){
		this.listeResponseDetails=new ArrayList<ResponseDetail>();
		for(QuestionMcq q : this.questionList){
			String goodResponseText=null;
			String goodResponseChoice=null;
			boolean isGood=false;
			for(ResponseMcq r: q.getResponseList()){
				if(r.getOk()!=null && r.getOk().booleanValue()==true){
					goodResponseText=(goodResponseText==null)?r.getText():(goodResponseText+" ; "+r.getText());
					goodResponseChoice=(goodResponseChoice==null)?r.getResponseNum():(goodResponseChoice+','+r.getResponseNum());
				}
			}
			String responseChoice=this.mapNumQuestionChoice.get(q.getQuestionNumber());
			if(responseChoice.equals(goodResponseChoice)){
				isGood=true;
			}
			listeResponseDetails.add(new ResponseDetail(q.getText(),q.getQuestionNumber(),
					                 goodResponseText, goodResponseChoice,isGood, responseChoice));
		}
	}
	
	public String completeMcq(){
		String suite=null;
		//System.out.println("completeMcq");
		this.mcqUserSession = serviceMcqUserSessionManager.computeMcqScore(mcqId, mapNumQuestionChoice);//version non persitante
		if(this.getSaasMBean()!=null){
			//no direct anonymous access to mcq , currentSaasRoleAccount exist
			SaasRoleAccount currentSaasRoleAccount  = getSaasMBean().getValidSaasRoleAccount();
			if(currentSaasRoleAccount!=null && !currentSaasRoleAccount.isGeneric()){	
				//rendre persistant si user connecté avec un compte specific (non générique)
				Long userId=serviceSaasUserGroupManager.findSaasUserBySpecificRoleAccountId(currentSaasRoleAccount.getIdAccount()).getUserId();
				serviceMcqUserSessionManager.storeNewComputedMcqUserSession(mcqUserSession,mcqId,userId);
			}
		}
		initListOfResponseDetails();
		return suite;
	}
	
	public String submitAndNext(){
		String suite=null;
		submitResponseForCurrentQuestion();
		suite=moveToNextQuestion();
		return suite;
	}
	public String submitAndPrevious(){
		String suite=null;
		submitResponseForCurrentQuestion();
		suite=moveToPreviousQuestion();
		return suite;
	}
	private void submitResponseForCurrentQuestion(){
		String responseChoice=null;
		if(currentQuestion.getExclusiveResponse()!=null && currentQuestion.getExclusiveResponse().booleanValue()==true){
			//mode exclusif
			responseChoice=this.exclusiveResponseChoice;  //ex : "A" , "B" , ...
		}else{
			//mode non exclusif
			for(String s : this.nonExclusiveResponseChoices){
				if(responseChoice==null){
					responseChoice=s;
				}else{
					responseChoice+=","+s;  //ex: "A,B" , "A,D" , ...
				}
			}
		}
		mapNumQuestionChoice.put(this.currentQuestion.getQuestionNumber(),responseChoice);
		this.exclusiveResponseChoice=null;//for next question
	}

	

	

	public McqManager getServiceMcqManager() {
		return serviceMcqManager;
	}

	public void setServiceMcqManager(McqManager serviceMcqManager) {
		this.serviceMcqManager = serviceMcqManager;
	}

	


	public String getExclusiveResponseChoice() {
		return exclusiveResponseChoice;
	}


	public void setExclusiveResponseChoice(String exclusiveResponseChoice) {
		this.exclusiveResponseChoice = exclusiveResponseChoice;
	}


	public McqUserSessionManager getServiceMcqUserSessionManager() {
		return serviceMcqUserSessionManager;
	}


	public void setServiceMcqUserSessionManager(
			McqUserSessionManager serviceMcqUserSessionManager) {
		this.serviceMcqUserSessionManager = serviceMcqUserSessionManager;
	}


	public McqUserSession getMcqUserSession() {
		return mcqUserSession;
	}

	public List<String> getNonExclusiveResponseChoices() {
		return nonExclusiveResponseChoices;
	}

	public void setNonExclusiveResponseChoices(
			List<String> nonExclusiveResponseChoices) {
		this.nonExclusiveResponseChoices = nonExclusiveResponseChoices;
	}

	public List<ResponseDetail> getListeResponseDetails() {
		return listeResponseDetails;
	}

	public SaasUserGroupManager getServiceSaasUserGroupManager() {
		return serviceSaasUserGroupManager;
	}

	public void setServiceSaasUserGroupManager(
			SaasUserGroupManager serviceSaasUserGroupManager) {
		this.serviceSaasUserGroupManager = serviceSaasUserGroupManager;
	}

	

}
