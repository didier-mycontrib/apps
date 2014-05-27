package org.mycontrib.apps.training.web.mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.io.IOUtils;
import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.web.mbean.common.McqChooseCommon;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class McqChooseForBuild  extends McqChooseCommon{
	
	@ManagedProperty(value="#{serviceMcqChooser}")
	protected McqChooser serviceMcqChooser; 
	
	@ManagedProperty(value="#{serviceMcqManager}")
	private McqManager serviceMcqManager;
	

	private String newSubjectTitle;
	private Boolean newSubjectShared;//true for shared/public
	private String newMcqTitle;
	
	public void handleFileUpload(FileUploadEvent event) { 
		String sMsg = "";
		try {
			UploadedFile file = event.getFile();
			sMsg += file.getFileName() + " is uploaded.";
			String mcqXmlStr =IOUtils.toString(file.getInputstream());
			//System.out.println("uploaded xml file string:" + mcqXmlStr);
			//importer le qcm depuis le fichier xml "uploaded" :
			Long storedOrUpdatedMcqId = serviceMcqManager.storeOrUpdateMcqFromXmlString(mcqXmlStr);
			//vérification et éventuelle fixation de ownerOrgId :
			Long ownerOrgId = getSaasMBean().getSaasOrg().getIdOrg();
			Mcq mcq = serviceMcqManager.getMcqById(storedOrUpdatedMcqId);
			if(mcq.getOwnerOrgId()==null  || !mcq.getOwnerOrgId().equals(ownerOrgId) ){
			   mcq.setOwnerOrgId(ownerOrgId);
			   serviceMcqManager.updateMcq(mcq);
			}
			if(storedOrUpdatedMcqId!=null){
				//le rattacher éventuellement au sujet (sauf si déjà fait / test effectué dans addMcqInSubject):
				serviceMcqChooser.addMcqInSubject(this.subjectId, storedOrUpdatedMcqId);
				sMsg += " import ok";
				//System.out.println(sMsg);
				this.mcqId=storedOrUpdatedMcqId; //le qcm qui vient d'être importé devient le qcm courant
				this.mcqList=serviceMcqChooser.getMcqListBySubject(this.subjectId);//réactualisation
			}
			FacesMessage msg = new FacesMessage("Succesful", sMsg);  
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Echec", sMsg + e.getMessage());  
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}  
    }  
	 
    
   

	public void onSubjectChange(ValueChangeEvent event){
		this.subjectId=(Long)event.getNewValue();
		//System.out.println("onSubjectChange , new subjectId="+subjectId);
		this.currentSubject=null;
		if(subjectId!=null){
			if(subjectId>0){
				this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
				for(McqSubject s : this.subjectList){
					if(s.getSubjectId().equals(subjectId)){
						this.currentSubject=s;break;
					}
				}
				if(!this.mcqList.isEmpty()){
					this.mcqId=this.mcqList.get(0).getId();
				}
			}
			else{
				this.currentSubject=speudoSubject;
				this.mcqList=null;
				this.mcqId=null;
			}
		}
	}
	
	public void onUpdateSubject(ActionEvent event){
		serviceMcqChooser.updateSubject(currentSubject);		
	}
	
	public void onDeleteSubject(ActionEvent event){
		//System.out.println("deleting subject (to implement):" + this.currentSubject.getTitle());
		try {
			if(this.currentSubject!=this.speudoSubject){
				serviceMcqChooser.deleteSubject(this.getSubjectId()/*this.currentSubject.getSubjectId()*/);
				//update locale subject list:
				subjectList.remove(this.currentSubject);
				//new currentSubject = speudoSubject without mqc list
				this.currentSubject=this.speudoSubject;
				this.subjectId=this.currentSubject.getSubjectId();
				this.mcqList =null;
				this.mcqId=null;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("suppression impossible ",e.getMessage()));
			System.err.println(e.getMessage());
			//e.printStackTrace();
		}
	}
		
	public void onAddSubject(ActionEvent event){
		//System.out.println("adding newSubject:" + this.newSubjectTitle);
		if(newSubjectTitle!=null && newSubjectTitle.length()>0){
			McqSubject newSubject = new McqSubject();
			newSubject.setTitle(this.newSubjectTitle);
			//+ ajout en base
			Long ownerOrgId =  getSaasMBean().getSaasOrg().getIdOrg();
			newSubject.setOwnerOrgId(ownerOrgId);
			Boolean shared=this.newSubjectShared;
			newSubject.setShared(shared);
			Long newSubjectId=serviceMcqChooser.addSubject(newSubject.getTitle(),ownerOrgId, shared);
			newSubject.setSubjectId(newSubjectId);
			subjectList.add(newSubject);
			this.subjectId=newSubject.getSubjectId();
			this.currentSubject=newSubject;
			this.mcqList =null;
			this.mcqId=null;
			this.newSubjectTitle=null; //for next adding
			this.newSubjectShared=null; //for next adding
		}
	}
	
	public void onAddMcq(ActionEvent event){
		//System.out.println("adding newQcm:" + this.newMcqTitle);
		if(newMcqTitle!=null && newMcqTitle.length()>0){
			Mcq newMcq = new Mcq();
			newMcq.setTitle(this.newMcqTitle);
			//fixer par defaut mcq.shared à parentSubject.shared:
			boolean bSubjectShared = (currentSubject.getShared()==null) ? false : currentSubject.getShared().booleanValue();
			newMcq.setShared(bSubjectShared);
			
			Long newMcqId= serviceMcqManager.createMcq(newMcq);
			newMcq.setId(newMcqId);
			serviceMcqChooser.addMcqInSubject(this.subjectId, newMcqId);//rattachement en base
			this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
			this.mcqId=newMcq.getId();
			//System.out.println("new mcqId:" + this.mcqId);
		}
	}
	
	public void onDeleteMcq(ActionEvent event){
		this.serviceMcqManager.deleteMcq(mcqId);//avec detachement sujet + suppressions toutes questions en casacade
		//et reactualisation de la liste des qcm du sujet courant:
		this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
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

	public Boolean getNewSubjectShared() {
		return newSubjectShared;
	}

	public void setNewSubjectShared(Boolean newSubjectShared) {
		this.newSubjectShared = newSubjectShared;
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
