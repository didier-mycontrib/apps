
package org.mycontrib.apps.training.mcq.impl.persistence.entity;

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

@Entity
  @Table(name="QuestionMcq")
public  class _QuestionMcq  {

	private Boolean exclusiveResponse;

	private String imageOrVideoFileName;

	private Integer questionNumber;

	private String text;
	
	@ManyToOne
	@JoinColumn(name="ref_mcq")
	private _Mcq mcq;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long idQuestion;


	//private Integer tempQuestionOrder; //not persisted , no in entity , just in dto
  
     @ElementCollection
     @CollectionTable(name="ResponseMcq", joinColumns={@JoinColumn(name="idQuestion")}) 
	private List<_ResponseMcq> responseList;//ou une map

	public _QuestionMcq(){
		super(); 
	}      
	public String toString(){
		return "QuestionMcq("+ "exclusiveResponse=" + exclusiveResponse+","+ "imageOrVideoFileName=" + imageOrVideoFileName+","+ "questionNumber=" + questionNumber+","+ "idQuestion=" + idQuestion+","+ "text=" + text+ ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return idQuestion;
 		//End of user code
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
	public Integer getQuestionNumber() {
		return this.questionNumber; 
	}
	public void setQuestionNumber(Integer questionNumber){
		this.questionNumber=questionNumber;
	}
	public String getText() {
		return this.text; 
	}
	public void setText(String text){
		this.text=text;
	}
	public Long getIdQuestion() {
		return this.idQuestion; 
	}
	public void setIdQuestion(Long idQuestion){
		this.idQuestion=idQuestion;
	}
	
	public List<_ResponseMcq> getResponseList() {
		return this.responseList;
	}
	public void setResponseList(List<_ResponseMcq> responseList){
		this.responseList=responseList;
	}
	public _Mcq getMcq() {
		return mcq;
	}
	public void setMcq(_Mcq mcq) {
		this.mcq = mcq;
	}
	
	

        
} 
