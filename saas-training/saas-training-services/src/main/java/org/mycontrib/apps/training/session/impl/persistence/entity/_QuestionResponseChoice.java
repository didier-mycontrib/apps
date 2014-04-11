
package org.mycontrib.apps.training.session.impl.persistence.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
  //@Table(name="QuestionResponseChoice")
public  class _QuestionResponseChoice  {


	private String choiceString;

	private Integer questionNumber;

	public _QuestionResponseChoice(){
		super(); 
	}      
	public String toString(){
		return "QuestionResponseChoice("+ "choiceString=" + choiceString+","+ "questionNumber=" + questionNumber + ")";
	}
 
	
	public String getChoiceString() {
		return this.choiceString; 
	}
	public void setChoiceString(String choiceString){
		this.choiceString=choiceString;
	}
	public Integer getQuestionNumber() {
		return this.questionNumber; 
	}
	public void setQuestionNumber(Integer questionNumber){
		this.questionNumber=questionNumber;
	}

        
} 
