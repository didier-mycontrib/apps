
package org.mycontrib.apps.training.mcq.impl.domain.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringEscapeUtils;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcqSubject;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoQuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._McqSubject;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.ResponseMcq;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager;
import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.service.internal.GenericInternalService;
import org.mycontrib.generic.service.internal.common.GenericInternalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Named
@WebService (endpointInterface="org.mycontrib.apps.training.mcq.itf.domain.service.McqManager")
@Transactional()
public  class McqManagerImpl implements McqManager {
	 private static Logger logger = LoggerFactory.getLogger(McqManagerImpl.class);
		
		private GenericInternalService<Mcq,Long> genericInternalService =null;
		
		@PostConstruct  //@Post initialization(s) after injection
		protected void initGenericInternalService(){
			genericInternalService = new GenericInternalServiceImpl<Mcq,_Mcq,Long>(mcqDao,beanConverter,logger){};
			// new instance of anonymous inner class witch inherit of GenericSuperClass
		}
		
		@Inject
		private DaoMcq mcqDao;
		
		@Inject
		private DaoQuestionMcq questionMcqDao;
		
		@Inject
		private DaoMcqSubject subjectDao;
		
		@Inject
		private QuestionMcqManager questionMcqManager;

	@Inject
	private GenericBeanConverter beanConverter;

	private JAXBContext jaxbCtx;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	public McqManagerImpl(){
		super(); 
	}
@Override
public Long createMcq(Mcq mcq) throws GenericException {
	Long mcqId=null;
	try {
		mcqId=genericInternalService.persistIdNewEntityFromDto(mcq);
	} catch (Exception e) {
		throw new GenericException("echec createMcq",e);
	}
	return mcqId;
}
@Override
public void updateMcq(Mcq mcq) throws GenericException {
	try {
		genericInternalService.updateEntityFromDto(mcq);
	} catch (Exception e) {
		throw new GenericException("echec updateMcq",e);
	}
}
@Override
public void deleteMcq(Long mcqId) throws GenericException {
	try {
		_Mcq mcqToDelete = mcqDao.getEntityById(mcqId);
		//1.detacher le qcm de son/ses sujet(s)
		for(_McqSubject s :subjectDao.findSubjectListForMcq(mcqId)){
			s.getMcqList().remove(mcqToDelete);
		}
		//2.supprimer le qcm
		mcqDao.removeEntity(mcqToDelete);
		//et avec suppression automatique des questions en cascade (V1)
	} catch (Exception e) {
		throw new GenericException("echec deleteMcq",e);
	}
}
@Override
public Mcq getMcqById(Long id) throws GenericException {
	Mcq mcq=null;
	try {
		mcq= genericInternalService.getDtoById(id);
	} catch (Exception e) {
		throw new GenericException("echec getMcqById",e);
	}
	return mcq;
}
@Override
public void attachQuestion(Long idMcq, Long idQuestion) throws GenericException {
	try {
		_Mcq persistentMcq = mcqDao.getEntityById(idMcq);
		_QuestionMcq persistentQuestion = questionMcqDao.getEntityById(idQuestion);
		//persistentMcq.getQuestionList().add(persistentQuestion);//sens pas optimum et secondaire (mappedBy)
		persistentQuestion.setMcq(persistentMcq);//sens optimum et principal (persistant)
		Integer nbQuestions = persistentMcq.getNbQuestions();
		if(nbQuestions==null){
			nbQuestions=0;
		}
		persistentMcq.setNbQuestions(nbQuestions+1);
	} catch (Exception e) {
		throw new GenericException("echec attachQuestion",e);
	}
}

@Override
public List<QuestionMcq> getQuestionList(Long idMcq) throws GenericException {
	List<QuestionMcq> questionList = null;
	try {
		_Mcq persistentMcq = mcqDao.getEntityById(idMcq);
		questionList= beanConverter.convertList(persistentMcq.getQuestionList(),QuestionMcq.class);
	} catch (Exception e) {
		throw new GenericException("echec getQuestionList",e);
	}
	return questionList;
}


private void initJaxb(){
	try {
		if(jaxbCtx==null){
			jaxbCtx = JAXBContext.newInstance(Mcq.class);
			marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			unmarshaller = jaxbCtx.createUnmarshaller();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} 
}

@Override
public String getMcqAsXmlString(Long idMcq) throws GenericException {
	String mcqXmlString=null;
	try {
		initJaxb();
		Mcq mcq= genericInternalService.getDtoById(idMcq);
		//escapeXml pour transformer "é,à,è,ù,..." en &...; 
		for(QuestionMcq q : mcq.getQuestionList()){
			q.setText(StringEscapeUtils.escapeXml(q.getText()));
			for(ResponseMcq r : q.getResponseList()){
				r.setText(StringEscapeUtils.escapeXml(r.getText()));
			}
		}
		StringWriter sw=new StringWriter();
		marshaller.marshal(mcq, sw);
		mcqXmlString=sw.toString();
	} catch (Exception e) {
		throw new GenericException("echec getMcqAsXmlString",e);
	}
	return mcqXmlString;
}

@Override
public Long storeOrUpdateMcqFromXmlString(String mcqXmlStr) {
	Long mcqId=null;
	try{
		initJaxb();
		Mcq mcq = (Mcq)unmarshaller.unmarshal(new StringReader(mcqXmlStr));
		//unscapeXml pour transformer   &...  en "é,à,è,ù,..."; 
			for(QuestionMcq q : mcq.getQuestionList()){
				q.setText(StringEscapeUtils.unescapeXml(q.getText()));
				for(ResponseMcq r : q.getResponseList()){
					r.setText(StringEscapeUtils.unescapeXml(r.getText()));
				}
			}
		//System.out.println("*** mcq to import: " + mcq);
		Long mcqIdFromXml = mcq.getId();
		if(mcqIdFromXml!=null){
			//nb: on considere l'id provenant du xml comme significatif que si un qcm existant 
			//possède à la fois le même id et le même titre
			_Mcq existingMcq = mcqDao.getEntityById(mcqIdFromXml);
			if(existingMcq !=null && existingMcq.getTitle().equals(mcq.getTitle())){
				genericInternalService.updateEntityFromDto(mcq);
			}else{
			  mcq.setId(null);mcqIdFromXml=null;
			}
		}
		if(mcqIdFromXml==null){
			mcqId=genericInternalService.persistIdNewEntityFromDto(mcq);//ne rend persistant que le qcm
			//rendre également persistante toutes les questions et réponses 
			//(en réinitialisant préalablement les id non significatifs à null):
			for(QuestionMcq q  : mcq.getQuestionList()){
				q.setIdQuestion(null);
				Long idQuestion = questionMcqManager.createQuestion(q);
				this.attachQuestion(mcqId, idQuestion);
			}
			
		}
	} catch (Exception e) {
		throw new GenericException("echec storeOrUpdateMcqFromXmlString",e);
	}
	return mcqId;
}
@Override
public List<QuestionMcq> getRandomQuestionSubList(Long idMcq)
		throws GenericException {
	List<QuestionMcq> questionList = null;
	try {
		_Mcq persistentMcq = mcqDao.getEntityById(idMcq);
		questionList= beanConverter.convertList(persistentMcq.getQuestionList(),QuestionMcq.class);
		Integer nbTotalQ = questionList.size();
		Integer subListSize= persistentMcq.getQuestionRandomSubListSize();
		if(subListSize!=null && subListSize>0 && subListSize<nbTotalQ){
			Integer nbQToRemove= nbTotalQ - subListSize;
			for(int i =0; i<nbQToRemove;i++){
				//index aleatoire
				int index = Math.min((int) (nbTotalQ * Math.random()),nbTotalQ-1);
				//enlever de la liste l'element de l'index indiqué:
				questionList.remove(index);
				nbTotalQ = questionList.size();
			}
		}
	} catch (Exception e) {
		throw new GenericException("echec getRandomQuestionSubList",e);
	}
	return questionList;
}      
       


}
