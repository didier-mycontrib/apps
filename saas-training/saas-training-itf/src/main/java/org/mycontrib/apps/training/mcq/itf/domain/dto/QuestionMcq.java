package org.mycontrib.apps.training.mcq.itf.domain.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public  class QuestionMcq  {
	private String text;
	@XmlAttribute
	private Boolean exclusiveResponse;
	@XmlAttribute
	private String imageOrVideoFileName;
	@XmlAttribute
	private Long idQuestion;
	@XmlTransient
	private Integer tempQuestionOrder;
	@XmlAttribute
	private Integer questionNumber;
	//@XmlElementWrapper(name="responses")en plus possible mais pas utile
	@XmlElement(name="response")
	private List<ResponseMcq> responseList;
	

	public QuestionMcq(){
		super(); 
	}      
	public String toString(){
		return "QuestionMcq("+ "text=" + text+","+ "imageOrVideoFileName=" + imageOrVideoFileName+","+ "exclusiveResponse=" + exclusiveResponse+","+ "idQuestion=" + idQuestion+","+ "questionNumber=" + questionNumber+","+ "tempQuestionOrder=" + tempQuestionOrder + ")";
	}
    
	public List<ResponseMcq> getResponseList() {
		return this.responseList;
	}
	
	
	public Integer getTempQuestionOrder() {
		return this.tempQuestionOrder;
	}
	
	public String getText() {
		return this.text;
	}
	public void setText(String text){
		this.text=text;
	}
	public Boolean getExclusiveResponse() {
		return this.exclusiveResponse;
	}
	public void setExclusiveResponse(Boolean exclusiveResponse){
		this.exclusiveResponse=exclusiveResponse;
	}
	public String getImageOrVideoFileName() {
		return this.imageOrVideoFileName;
	}
	public void setImageOrVideoFileName(String imageOrVideoFileName){
		this.imageOrVideoFileName=imageOrVideoFileName;
	}
	public Long getIdQuestion() {
		return this.idQuestion;
	}
	public void setIdQuestion(Long idQuestion){
		this.idQuestion=idQuestion;
	}
	
	public void setTempQuestionOrder(Integer tempQuestionOrder){
		this.tempQuestionOrder=tempQuestionOrder;
	}
	public Integer getQuestionNumber() {
		return this.questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber){
		this.questionNumber=questionNumber;
	}
	
	public void setResponseList(List<ResponseMcq> responseList){
		this.responseList=responseList;
	}
	

        
}
  
