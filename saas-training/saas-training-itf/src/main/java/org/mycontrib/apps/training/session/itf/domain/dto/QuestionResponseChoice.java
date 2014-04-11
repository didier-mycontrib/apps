package org.mycontrib.apps.training.session.itf.domain.dto;


public  class QuestionResponseChoice  {
	private Integer questionNumber;
	private String choiceString;

	public QuestionResponseChoice(){
		super(); 
	}      
	public String toString(){
		return "QuestionResponseChoice("+ "questionNumber=" + questionNumber+","+ "choiceString=" + choiceString + ")";
	}
           
	public Integer getQuestionNumber() {
		return this.questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber){
		this.questionNumber=questionNumber;
	}
	public String getChoiceString() {
		return this.choiceString;
	}
	public void setChoiceString(String choiceString){
		this.choiceString=choiceString;
	}
	

        
}
  
