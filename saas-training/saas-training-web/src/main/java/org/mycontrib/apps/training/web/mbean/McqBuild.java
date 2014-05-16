package org.mycontrib.apps.training.web.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.ResponseMcq;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager;
import org.mycontrib.apps.training.web.mbean.common.McqAbstractMBean;
import org.mycontrib.generic.exception.GenericException;

/* McqBuild : JSF2 ManagedBean for create or Update a mcq / authoring  */

@ManagedBean
@SessionScoped
public class McqBuild extends McqAbstractMBean{
			
	@ManagedProperty(value="#{serviceMcqManager}")
	private McqManager serviceMcqManager;
	
	@ManagedProperty(value="#{serviceQuestionMcqManager}")
	private QuestionMcqManager serviceQuestionMcqManager;
	
	private List<String> oneLineSpeudoList = null;
	private List<String> exclusiveResponseNumList = new ArrayList<String>();
	private String exclusiseResponseNum; //choosed
	
    private boolean exclusiveMode=true;
    
       
    private boolean questionUpdated=false;
	
	private String nonExclusiveCssClass="nodisplay";
	private String exclusiveCssClass="responseNumber";//"display";

	
	
	
	private void prepareNewQuestion(){
		this.currentQuestion=new QuestionMcq();
		currentQuestion.setText("derniere question ???");
		currentQuestion.setResponseList(new ArrayList<ResponseMcq>());
		currentQuestion.getResponseList().add(new ResponseMcq("A","a ???"));
		currentQuestion.getResponseList().add(new ResponseMcq("B","b ???"));
		currentQuestion.getResponseList().add(new ResponseMcq("C","c ???"));
		currentQuestion.getResponseList().add(new ResponseMcq("D","d ???"));
		
		int randomIndex = (int)(Math.random() * 4);
		currentQuestion.getResponseList().get(randomIndex).setOk(true);
		
		currentQuestion.setExclusiveResponse(true);//par defaut
		currentQuestion.setQuestionNumber(nbQuestions+1);
		Long newQuestionId= this.serviceQuestionMcqManager.createQuestion(currentQuestion);
		this.currentQuestion.setIdQuestion(newQuestionId);
		serviceMcqManager.attachQuestion(this.mcqId, newQuestionId);
		//this.questionList=serviceMcqManager.getQuestionList(this.mcqId);  ???
		//ou bien :
		   if(questionList==null) 
			 this.questionList=new ArrayList<QuestionMcq>(); 
		   this.questionList.add(this.currentQuestion);
		this.nbQuestions++;
	}
	
	private void prepareQuestion(){
		questionUpdated=false;
		exclusiveMode = currentQuestion.getExclusiveResponse() == null ? false : currentQuestion.getExclusiveResponse().booleanValue();
		if(exclusiveMode){
			nonExclusiveCssClass="nodisplay";
			exclusiveCssClass="responseNumber";//"display";
		}
		else{
			nonExclusiveCssClass="responseNumber";//"display";
			exclusiveCssClass="nodisplay";
		}
		initExclusiveResponseNumList();
	}
	
	public String updateMcq(){
		String suite=null;
		//System.out.println("vers mcqOptions");
		suite="mcqOptions";
		return suite;
	}
	
	
	
	public String updateMcqOptions(){
		String suite=null;
		try {
			serviceMcqManager.updateMcq(mcq);
		} catch (GenericException e) {
			e.printStackTrace();
		}
		suite=updateMcq();
		return suite;
	}
	
	public String returnToMcqOptions(){
		String suite="mcqOptions";
		//rendre tout cohérent avant le retour à mcqOptions:
		this.mcq.setQuestionList(this.questionList);
		this.mcq.setNbQuestions(this.nbQuestions);
		return suite;
	}
	
	public String updateMcqQuestions(){
		String suite=null;
		if(this.mcqId==null){
			return suite=null;
		}
		else{
			this.questionList=serviceMcqManager.getQuestionList(this.mcqId);
		}
		if(this.questionList!=null && !this.questionList.isEmpty()){
			//update existing qcm with at least one question
			this.nbQuestions=this.questionList.size();
			this.currentQuestionIndex=0;
			this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
		    suite = "editMcq";//.xhtml
		}
		else{
			//first new question (of new mcq)
			System.out.println("first new question of new mcq with mcqId="+this.mcqId);
			this.nbQuestions=0;
			prepareNewQuestion();//with this.nbQuestions++; inside		
			this.currentQuestionIndex=0;
			suite = "editMcq";//.xhtml
		}
		prepareQuestion();
		return suite;
	}
	
	
	public String lastQuestion(){
		String suite=null;
		currentQuestionIndex = nbQuestions-1;
		this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
		prepareQuestion();
		return suite;
	}
	
	private String moveToNextQuestion(){
		String suite=null;
		if(currentQuestionIndex < (nbQuestions-1) ){
			this.currentQuestionIndex++;
			this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
		}else{
			//new question
			prepareNewQuestion();
			this.currentQuestionIndex++;
		}
		prepareQuestion();
		return suite;
	}
	
	public String moveToPreviousQuestion(){
		String suite=null;
		if(currentQuestionIndex > 0 ){
			this.currentQuestionIndex--;
			this.currentQuestion=this.questionList.get(this.currentQuestionIndex);
			prepareQuestion();
		}
		return suite;
	}
	
	
	public String updateQuestionAndNextOrNew(){
		String suite=null;
		updateCurrentQuestionIfChanged();
		suite=moveToNextQuestion();
		return suite;
	}
	public String updateQuestionAndPreviousIfAny(){
		String suite=null;
		updateCurrentQuestionIfChanged();
		suite=moveToPreviousQuestion();
		return suite;
	}
	
	public void onRemoveThisQuestion(ActionEvent event){
		//en v1 : remove only last question (disable on first questions)
		this.serviceQuestionMcqManager.deleteQuestion(this.currentQuestion.getIdQuestion());
		this.nbQuestions--;
		moveToPreviousQuestion();
	}
	
	
	public String updateQuestion(){
		String suite=null;//no move to previous or next
		updateCurrentQuestionIfChanged();
		prepareQuestion();
		return suite;
	}
	
	private void updateCurrentQuestionIfChanged(){
	if(questionUpdated){
		exclusiveMode = currentQuestion.getExclusiveResponse() == null ? false : currentQuestion.getExclusiveResponse().booleanValue();
		if(this.exclusiveMode==true){
			for(ResponseMcq r : currentQuestion.getResponseList()){
				if(this.exclusiseResponseNum.equals(r.getResponseNum())){
					r.setOk(true);
				 }
				else {
					r.setOk(false);
				 }
				}
			}
		this.serviceQuestionMcqManager.updateQuestion(this.currentQuestion);
		questionUpdated=false;
		}
	}

	
	public McqManager getServiceMcqManager() {
		return serviceMcqManager;
	}

	public void setServiceMcqManager(McqManager serviceMcqManager) {
		this.serviceMcqManager = serviceMcqManager;
	}

	public QuestionMcqManager getServiceQuestionMcqManager() {
		return serviceQuestionMcqManager;
	}


	public void setServiceQuestionMcqManager(
			QuestionMcqManager serviceQuestionMcqManager) {
		this.serviceQuestionMcqManager = serviceQuestionMcqManager;
	}

	@Override
	public void setMcqId(Long mcqId) {
		super.setMcqId(mcqId);
		if(mcqId!=null){
		 this.mcq=serviceMcqManager.getMcqById(mcqId);
		}
	}
	
	
	
	public boolean isExclusiveMode() {
		return exclusiveMode;
	}

	public void setExclusiveMode(boolean exclusiveMode) {
		this.exclusiveMode = exclusiveMode;
	}
	

	private void initExclusiveResponseNumList(){
		if(oneLineSpeudoList==null){
			 oneLineSpeudoList = new ArrayList<String>();
			 oneLineSpeudoList.add("speudo_tab_line");
			 //for dataTable with one line with first column = selectOneRadio , second column = subtable of inputText
		}
		exclusiveResponseNumList.clear(); exclusiseResponseNum=null;
		for(ResponseMcq r : currentQuestion.getResponseList()){
				exclusiveResponseNumList.add(r.getResponseNum());
				if(r.getOk()!=null && r.getOk().booleanValue()==true){
					this.exclusiseResponseNum=r.getResponseNum();
				}
			}
	}

	public List<String> getOneLineSpeudoList() {
		return oneLineSpeudoList;
	}

	public String getExclusiseResponseNum() {
		return exclusiseResponseNum;
	}

	public void setExclusiseResponseNum(String exclusiseResponseNum) {
		this.exclusiseResponseNum = exclusiseResponseNum;
	}

	public List<String> getExclusiveResponseNumList() {
		return exclusiveResponseNumList;
	}

	public void setExclusiveResponseNumList(List<String> exclusiveResponseNumList) {
		this.exclusiveResponseNumList = exclusiveResponseNumList;
	}

	public String getNonExclusiveCssClass() {
		return nonExclusiveCssClass;
	}

	public void setNonExclusiveCssClass(String nonExclusiveCssClass) {
		this.nonExclusiveCssClass = nonExclusiveCssClass;
	}

	public String getExclusiveCssClass() {
		return exclusiveCssClass;
	}

	public void setExclusiveCssClass(String exclusiveCssClass) {
		this.exclusiveCssClass = exclusiveCssClass;
	}

	public boolean isQuestionUpdated() {
		return questionUpdated;
	}

	public void setQuestionUpdated(boolean questionUpdated) {
		this.questionUpdated = questionUpdated;
	}

	
	public String getNextOrNewLabel(){
		if(this.getIsLastQuestion())
			return "new";
		else return "next";
	}

	
}
