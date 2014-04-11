package org.mycontrib.apps.training.mcq.itf.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public  class ResponseMcq  {
	private String text;//proposed or to input / to guess
	@XmlAttribute
	private Boolean toInput;//false or null is proposed , true if invisible to write in inputText
	@XmlAttribute
	private Boolean ok;
	@XmlAttribute
	private String responseNum;
	

	public ResponseMcq(){
		super(); 
	}  
	
	public ResponseMcq(String responseNum,String text){
		super(); 
		this.responseNum=responseNum;
		this.text=text;
	}
	public String toString(){
		return "ResponseMcq("+ "text=" + text+","+ "responseNum=" + responseNum+","+ "ok=" + ok + ",toInput=" + toInput + ")";
	}
           
	public String getText() {
		return this.text;
	}
	public void setText(String text){
		this.text=text;
	}
	public Boolean getOk() {
		return this.ok;
	}
	public void setOk(Boolean ok){
		this.ok=ok;
	}
	public String getResponseNum() {
		return this.responseNum;
	}
	public void setResponseNum(String responseNum){
		this.responseNum=responseNum;
	}
	public Boolean getToInput() {
		return toInput;
	}
	public void setToInput(Boolean toInput) {
		this.toInput = toInput;
	}
	

        
}
  
