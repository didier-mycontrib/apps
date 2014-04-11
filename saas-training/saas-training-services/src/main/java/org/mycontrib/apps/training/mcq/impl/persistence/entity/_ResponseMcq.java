
package org.mycontrib.apps.training.mcq.impl.persistence.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
 // No @Table(name="ResponseMcq") here 
 //but  @CollectionTable(name="ResponseMcq", joinColumns={@JoinColumn(name="idQuestion")}) 
//over private List<_ResponseMcq> responseList; in _QuestionMcq.java
public  class _ResponseMcq  {
	//NB: dans cette classe , on ne fait pas apparaître la propriété idQuestion pourtant
	// présente dans la table "ResponseMcq" , les mécanismes JPA s'en chargeront automatiquement !!!

	private String responseNum;

	private Boolean ok;

	private String text;
	
	private Boolean toInput;//false or null is proposed , true if invisible to write in inputText

	public _ResponseMcq(){
		super(); 
	}      
	public String toString(){
		return "_ResponseMcq("+ "responseNum=" + responseNum+","+ "ok=" + ok+","+ "text=" + text + ",toInput=" + toInput + ")";
	}
 
	
	public String getResponseNum() {
		return this.responseNum; 
	}
	public void setResponseNum(String responseNum){
		this.responseNum=responseNum;
	}
	public Boolean getOk() {
		return this.ok; 
	}
	public void setOk(Boolean ok){
		this.ok=ok;
	}
	public String getText() {
		return this.text; 
	}
	public void setText(String text){
		this.text=text;
	}

        
} 
