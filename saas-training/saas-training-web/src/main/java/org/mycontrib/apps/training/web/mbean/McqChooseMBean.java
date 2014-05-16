package org.mycontrib.apps.training.web.mbean;

import java.util.ListIterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.web.mbean.common.McqChooseCommon;

@ManagedBean
@SessionScoped
public class McqChooseMBean extends McqChooseCommon{
	
	
	
	@ManagedProperty(value="#{serviceMcqChooser}")
	protected McqChooser serviceMcqChooser; 
	

	public void onFiltrageChange(ValueChangeEvent event){
		this.filtrage=(String)event.getNewValue();
		//System.out.println("filtrage:"+filtrage);
		initSubjectListSelonFiltrage();
		this.mcqList=null;//reset 
		this.subjectId=0L;
		this.mcqId=null;
		this.currentSubject=speudoSubject;
	}
	
	public void onSubjectChange(ValueChangeEvent event){
		this.subjectId=(Long)event.getNewValue();
		//System.out.println("onSubjectChange , new subjectId="+subjectId);
		if(subjectId!=null){
			if(subjectId>0){
			   this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
			   //supprimer éventuellement de la liste des sujets privés rangés dans des sujets public d'autres org:
			   Long ownerSaasOrgId = getSaasMBean().getSaasOrg().getIdOrg();
			   for (int i = mcqList.size() - 1 ; i >= 0 ; i--) {
				    if (!mcqList.get(i).getShared()) {
				    	mcqList.remove(i);
				    }
			   }
			}
			else this.mcqList = null;//subjectId==0 mean no choice (????)
		}
	}
	
	public McqChooser getServiceMcqChooser() {
		return serviceMcqChooser;
	}

	public void setServiceMcqChooser(McqChooser serviceMcqChooser) {
		this.serviceMcqChooser = serviceMcqChooser;
	}
	

	

	
}
