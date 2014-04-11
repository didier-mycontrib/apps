package org.mycontrib.apps.training.web.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.web.mbean.common.McqChooseCommon;

@ManagedBean
@SessionScoped
public class McqChooseForBuild  extends McqChooseCommon{
	
	@ManagedProperty(value="#{serviceMcqChooser}")
	protected McqChooser serviceMcqChooser; 
	
	@ManagedProperty(value="#{serviceMcqManager}")
	private McqManager serviceMcqManager;
	

	private String newSubjectTitle;
	private String newMcqTitle;
	
	private boolean confirmDelete=false;
	

	public void onSubjectChange(ValueChangeEvent event){
		this.subjectId=(Long)event.getNewValue();
		System.out.println("onSubjectChange , new subjectId="+subjectId);
		if(subjectId!=null){
			if(subjectId>0){
				this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
			}
			else{
				this.mcqList=null;
				this.mcqId=null;
			}
		}
	}
	
	
		
	public void onAddSubject(ActionEvent event){
		System.out.println("adding newSubject:" + this.newSubjectTitle);
		if(newSubjectTitle!=null && newSubjectTitle.length()>0){
			McqSubject newSubject = new McqSubject();
			newSubject.setTitle(this.newSubjectTitle);
			//+ ajout en base
			Long ownerOrgId =  getSaasMBean().getSaasOrg().getIdOrg();
			Boolean shared=false;
			Long newSubjectId=serviceMcqChooser.addSubject(newSubject.getTitle(),ownerOrgId, shared);
			newSubject.setSubjectId(newSubjectId);
			subjectList.add(newSubject);
			this.subjectId=newSubject.getSubjectId();
			this.mcqList =null;
			this.mcqId=null;
		}
	}
	
	public void onAddMcq(ActionEvent event){
		System.out.println("adding newQcm:" + this.newMcqTitle);
		if(newMcqTitle!=null && newMcqTitle.length()>0){
			Mcq newMcq = new Mcq();
			newMcq.setTitle(this.newMcqTitle);
			Long newMcqId= serviceMcqManager.createMcq(newMcq);
			newMcq.setId(newMcqId);
			serviceMcqChooser.addMcqInSubject(this.subjectId, newMcqId);//rattachement en base
			this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
			this.mcqId=newMcq.getId();
			System.out.println("new mcqId:" + this.mcqId);
		}
	}
	
	public void onDeleteMcq(ActionEvent event){
		if(this.confirmDelete==true){
			this.serviceMcqManager.deleteMcq(mcqId);//avec detachement sujet + suppressions toutes questions en casacade
			//et reactualisation de la liste des qcm du sujet courant:
			this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
			this.confirmDelete=false;//pour demander à reconfirmer une nouvelle suppression
		}
	}
	
	public McqChooser getServiceMcqChooser() {
		return serviceMcqChooser;
	}

	public void setServiceMcqChooser(McqChooser serviceMcqChooser) {
		this.serviceMcqChooser = serviceMcqChooser;
	}
	

	public String getNewSubjectTitle() {
		return newSubjectTitle;
	}


	public void setNewSubjectTitle(String newSubjectTitle) {
		this.newSubjectTitle = newSubjectTitle;
	}


	public String getNewMcqTitle() {
		return newMcqTitle;
	}


	public void setNewMcqTitle(String newMcqTitle) {
		this.newMcqTitle = newMcqTitle;
	}

	public McqManager getServiceMcqManager() {
		return serviceMcqManager;
	}

	public void setServiceMcqManager(McqManager serviceMcqManager) {
		this.serviceMcqManager = serviceMcqManager;
	}



	public boolean isConfirmDelete() {
		return confirmDelete;
	}



	public void setConfirmDelete(boolean confirmDelete) {
		this.confirmDelete = confirmDelete;
	}
	
	/*
	//NB: cette ancienne méthode est maintenant remplacé par le servlet "McqExportServlet" déclenché par un lien hypertexte 
	public String exportXmlMcq(){
		String suite=null;
		try {
			String mcqXmlString=serviceMcqManager.getMcqAsXmlString(this.mcqId);
			String xmlFilePath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("export/qcm.xml");
			FileOutputStream fos = new FileOutputStream(xmlFilePath);
			PrintStream ps= new PrintStream(fos);
			ps.println(mcqXmlString);
			ps.close();
			fos.close();
			System.out.println("file ready to download: "+xmlFilePath);
			suite="download"; //.xhtml
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suite;
	}*/
	
	
}
