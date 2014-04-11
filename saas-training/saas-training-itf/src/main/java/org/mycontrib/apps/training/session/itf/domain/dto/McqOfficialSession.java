package org.mycontrib.apps.training.session.itf.domain.dto;

import java.util.List;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasGroup;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasOrg;

public  class McqOfficialSession  {
	private SaasGroup assignedGroup;
	private String startDateTime;
	private Long idSession;
	private String duration;
	private Mcq mcq;
	private SaasOrg saasOrg;
	private List<McqUserSession> users;


	public McqOfficialSession(){
		super(); 
	}      
	public String toString(){
		return "McqOfficialSession("+ "assignedGroup=" + assignedGroup+","+ "startDateTime=" + startDateTime+","+ "idSession=" + idSession+","+ "duration=" + duration+","+ "mcq=" + mcq+","+ "saasOrg=" + saasOrg + ")";
	}
           
	public SaasGroup getAssignedGroup() {
		return this.assignedGroup;
	}
	public void setAssignedGroup(SaasGroup assignedGroup){
		this.assignedGroup=assignedGroup;
	}
	public String getStartDateTime() {
		return this.startDateTime;
	}
	public void setStartDateTime(String startDateTime){
		this.startDateTime=startDateTime;
	}
	public long getIdSession() {
		return this.idSession;
	}
	public void setIdSession(long idSession){
		this.idSession=idSession;
	}
	public String getDuration() {
		return this.duration;
	}
	public void setDuration(String duration){
		this.duration=duration;
	}
	public Mcq getMcq() {
		return this.mcq;
	}
	public void setMcq(Mcq mcq){
		this.mcq=mcq;
	}
	public SaasOrg getSaasOrg() {
		return this.saasOrg;
	}
	public void setSaasOrg(SaasOrg saasOrg){
		this.saasOrg=saasOrg;
	}
	
	public List<McqUserSession> getUsers() {
		return this.users;
	}
	public void setUsers(List<McqUserSession> users){
		this.users=users;
	}
	
        
}
  
