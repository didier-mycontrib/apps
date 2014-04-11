package org.mycontrib.apps.training.mcq.itf.domain.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public  class Mcq  {
	@XmlAttribute
	private String keyWords;
	private String title;
	//@XmlElementWrapper(name="questions") en plus possible mais pas utile
	@XmlElement(name="question")
	private List<QuestionMcq> questionList;
	@XmlAttribute
	private Long id;
	@XmlAttribute
	private Integer nbQuestions;
	@XmlAttribute
	private Integer questionRandomSubListSize;//null or not (must be < nbQuestions)
	//ex: sur un qcm de 50 questions en tout , on tire aléatoirement une sous liste de 10 questions
	//ceci permet à une personne d'effectuer plusieurs fois le même qcm pour s'entraîner.
	@XmlAttribute
    private Boolean shared;
	@XmlAttribute
    private Long ownerOrgId;
	

	public Mcq(){
		super(); 
	}      
	public String toString(){
		return "Mcq("+ "keyWords=" + keyWords+","+ "title=" + title+","+ "id=" + id+","+ "nbQuestions=" + nbQuestions + ",questionRandomSubListSize:"+questionRandomSubListSize 
				     + ",shared=" + shared + ",ownerOrgId=" + ownerOrgId+")";
	}
    

	public List<QuestionMcq> getQuestionList() {
		return this.questionList;
	}
	
	public String getKeyWords() {
		return this.keyWords;
	}
	public void setKeyWords(String keyWords){
		this.keyWords=keyWords;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	
	public void setQuestionList(List<QuestionMcq> questionList){
		this.questionList=questionList;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id){
		this.id=id;
	}
	public Integer getNbQuestions() {
		return this.nbQuestions;
	}
	public void setNbQuestions(Integer nbQuestions){
		this.nbQuestions=nbQuestions;
	}
	public Integer getQuestionRandomSubListSize() {
		return questionRandomSubListSize;
	}
	public void setQuestionRandomSubListSize(Integer questionRandomSubListSize) {
		this.questionRandomSubListSize = questionRandomSubListSize;
	}
	public Boolean getShared() {
		return shared;
	}
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	public Long getOwnerOrgId() {
		return ownerOrgId;
	}
	public void setOwnerOrgId(Long ownerOrgId) {
		this.ownerOrgId = ownerOrgId;
	}
	
    
        
}
  
