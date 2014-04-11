
package org.mycontrib.apps.training.mcq.impl.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
  @Table(name="McqSubject")
public  class _McqSubject  {


	private String title;  //NB: ce "title" ou "name" ou "text" du sujet poura eventuellement etre
						   //arborescent (ex: java/jee/spring ou java/jee/ejb ) et l'ihm pourra presenter un arbre de sujet
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long subjectId;

	
    //Start of user code relationship_subjectList_mcqList
    @ManyToMany //n-n unidirectionnel
    @JoinTable(name="McqSubjectMcq",
               joinColumns = {@JoinColumn(name = "ref_subject")},
               inverseJoinColumns = {@JoinColumn(name = "ref_mcq")})
    //End of user code
    private List<_Mcq> mcqList;
    
    private Boolean shared;
	
    private Long ownerOrgId;


	public _McqSubject(){
		super(); 
	}      
	public String toString(){
		//return "McqSubject("+ "title=" + title+","+ "subjectId=" + subjectId+","+ "mcqList=" + mcqList + ")";
		return "McqSubject("+ "title=" + title+","+ "subjectId=" + subjectId
				+ ",shared=" + shared + ",ownerOrgId=" + ownerOrgId+")";
	}
 
	public java.io.Serializable getPk(){
	    //Start of user code pk
 		return subjectId;
 		//End of user code
 	}
	public String getTitle() {
		return this.title; 
	}
	public void setTitle(String title){
		this.title=title;
	}
	public Long getSubjectId() {
		return this.subjectId; 
	}
	public void setSubjectId(Long subjectId){
		this.subjectId=subjectId;
	}
	public List<_Mcq> getMcqList() {
		return this.mcqList; 
	}
	public void setMcqList(List<_Mcq> mcqList){
		this.mcqList=mcqList;
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
