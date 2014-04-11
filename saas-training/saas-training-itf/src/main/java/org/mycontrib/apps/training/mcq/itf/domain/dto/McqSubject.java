package org.mycontrib.apps.training.mcq.itf.domain.dto;

import java.util.List;

public  class McqSubject  {
	private Long subjectId;
	private String title; //NB: ce "title" ou "name" ou "text" du sujet poura eventuellement etre
	   //arborescent (ex: java/jee/spring ou java/jee/ejb ) et l'ihm pourra presenter un arbre de sujet
	private List<Mcq> mcqList;
	
    private Boolean shared;

    private Long ownerOrgId;


	public McqSubject(){
		super(); 
	}      
	public String toString(){
		return "McqSubject("+ "subjectId=" + subjectId+","+ "title=" + title
				 + ",shared=" + shared + ",ownerOrgId=" + ownerOrgId +")";
	}
           
	public Long getSubjectId() {
		return this.subjectId;
	}
	public void setSubjectId(Long subjectId){
		this.subjectId=subjectId;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public List<Mcq> getMcqList() {
		return this.mcqList;
	}
	public void setMcqList(List<Mcq> mcqList){
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
  
