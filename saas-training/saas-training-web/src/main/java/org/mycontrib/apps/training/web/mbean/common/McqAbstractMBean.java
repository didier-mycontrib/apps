package org.mycontrib.apps.training.web.mbean.common;

import java.util.List;

import javax.faces.context.FacesContext;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.web.mbean.SaasMBean;

/* McqAbstractMBean : abstract common superclass for McqBuild and McqTraining */

public abstract class McqAbstractMBean {
	
	protected List<QuestionMcq> questionList;
	protected int nbQuestions;
	protected int currentQuestionIndex;
	protected QuestionMcq currentQuestion;
	protected Long mcqId;
	protected Mcq mcq; //with option to update or ...
		
	public SaasMBean getSaasMBean(){
		SaasMBean bean = null;
		bean = (SaasMBean)FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("saasMBean");
		return bean;
	}
	

	public List<QuestionMcq> getQuestionList() {
		return questionList;
	}


	public void setQuestionList(List<QuestionMcq> questionList) {
		this.questionList = questionList;
	}


	public int getNbQuestions() {
		return nbQuestions;
	}


	public int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	public QuestionMcq getCurrentQuestion() {
		return currentQuestion;
	}


	public Long getMcqId() {
		return mcqId;
	}

	public void setMcqId(Long mcqId) {
		this.mcqId = mcqId;
	}


	public Mcq getMcq() {
		return mcq;
	}


	public void setMcq(Mcq mcq) {
		this.mcq = mcq;
	}
	
	public boolean getIsLastQuestion(){
		boolean bRes=false;
		if(this.currentQuestionIndex==(this.nbQuestions-1))
			bRes=true;
		return bRes;
	}
	
	

}
