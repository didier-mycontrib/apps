
package org.mycontrib.apps.training.mcq.impl.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.mycontrib.apps.training.mcq.itf.domain.enumeration.McqType;



@Entity
  @Table(name="Mcq")
public  class _Mcq  {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String keyWords;

	private Integer nbQuestions; //nombre total de questions attachées au qcm
	
	private Integer questionRandomSubListSize;//null or not (must be < nbQuestions)
	//ex: sur un qcm de 50 questions en tout , on tire aléatoirement une sous liste de 10 questions
	//ceci permet à une personne d'effectuer plusieurs fois le même qcm pour s'entraîner.

	private String title;
	
	@Column(name="mcqType")
	@Enumerated(EnumType.STRING)
	private McqType mcqType;//FREE,VALID,CERTIFICATION

    //Start of user code relationship_mCQ_questionList
     @OneToMany(mappedBy="mcq",cascade=CascadeType.REMOVE) //1-n bidirectionnel
    //End of user code
	private List<_QuestionMcq> questionList;
	
	private Boolean shared;
	
    private Long ownerOrgId;

	public _Mcq(){
		super(); this.mcqType= McqType.FREE; //by default
	}      
	public String toString(){
		return "Mcq("+ "id=" + id+","+ "keyWords=" + keyWords+","+ "nbQuestions=" + nbQuestions+","+ "title=" + title + ",questionRandomSubListSize:"+questionRandomSubListSize 
				+ ",shared=" + shared + ",ownerOrgId=" + ownerOrgId+  ",mcqType=" + mcqType+ ")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return id;
 		//End of user code
 	}
	public Long getId() {
		return this.id; 
	}
	public void setId(Long id){
		this.id=id;
	}
	public String getKeyWords() {
		return this.keyWords; 
	}
	public void setKeyWords(String keyWords){
		this.keyWords=keyWords;
	}
	public Integer getNbQuestions() {
		return this.nbQuestions; 
	}
	public void setNbQuestions(Integer nbQuestions){
		this.nbQuestions=nbQuestions;
	}
	public String getTitle() {
		return this.title; 
	}
	public void setTitle(String title){
		this.title=title;
	}
	public List<_QuestionMcq> getQuestionList() {
		return this.questionList;
	}
	public void setQuestionList(List<_QuestionMcq> questionList){
		this.questionList=questionList;
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
	public McqType getMcqType() {
		return mcqType;
	}
	public void setMcqType(McqType type) {
		this.mcqType = type;
	}
    
        
} 
