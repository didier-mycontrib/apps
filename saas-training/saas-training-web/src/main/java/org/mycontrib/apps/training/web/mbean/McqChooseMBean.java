package org.mycontrib.apps.training.web.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.web.mbean.common.McqChooseCommon;

@ManagedBean
@SessionScoped
public class McqChooseMBean extends McqChooseCommon{
	
	@ManagedProperty(value="#{serviceMcqChooser}")
	protected McqChooser serviceMcqChooser; 
	

	public void onSubjectChange(ValueChangeEvent event){
		this.subjectId=(Long)event.getNewValue();
		//System.out.println("onSubjectChange , new subjectId="+subjectId);
		if(subjectId!=null){
			if(subjectId>0)
			   this.mcqList = serviceMcqChooser.getMcqListBySubject(subjectId);
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
