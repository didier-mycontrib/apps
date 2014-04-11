
package org.mycontrib.apps.training.mcq.impl.domain.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoQuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._QuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._ResponseMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.ResponseMcq;
import org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager;
import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.service.internal.GenericInternalService;
import org.mycontrib.generic.service.internal.common.GenericInternalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional()
@WebService (endpointInterface="org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager")
public  class QuestionMcqManagerImpl implements QuestionMcqManager {
	private static Logger logger = LoggerFactory.getLogger(McqManagerImpl.class);
	
	private GenericInternalService<QuestionMcq,Long> genericInternalService =null;
	
	@PostConstruct  //@Post initialization(s) after injection
	protected void initGenericInternalService(){
		genericInternalService = new GenericInternalServiceImpl<QuestionMcq,_QuestionMcq,Long>(questionMcqDao,beanConverter,logger){};
		// new instance of anonymous inner class witch inherit of GenericSuperClass
	}
		
	@Inject
	private DaoQuestionMcq questionMcqDao;
	@Inject
	private GenericBeanConverter beanConverter;

	public QuestionMcqManagerImpl(){
		super(); 
	}

	@Override
	public Long createQuestion(QuestionMcq q) throws GenericException {
		Long idQuestion=null;
		try {
			idQuestion=genericInternalService.persistIdNewEntityFromDto(q);
		} catch (Exception e) {
			throw new GenericException("echec createQuestion",e);
		}
		return idQuestion;
	}

	@Override
	public void updateQuestion(QuestionMcq q) throws GenericException {
		try {
			//genericInternalService.updateEntityFromDto(q);//en partie buggé au 7/01/2014 (écrase lien existant)
			// à priori , pour supprimer le bug, il faudrait ajouter une méthode beanConverter.copyProperties(src,existingDest)
			//où existingDest serait le resultat de genericDao.getEntityById
			
			_QuestionMcq existingPersistantQuestion = questionMcqDao.getEntityById(q.getIdQuestion());
			_QuestionMcq convertedQuestionWithNewValue= beanConverter.convert(q,_QuestionMcq.class);
			convertedQuestionWithNewValue.setMcq(existingPersistantQuestion.getMcq());
			questionMcqDao.updateEntity(convertedQuestionWithNewValue);	
		} catch (Exception e) {
			throw new GenericException("echec updateQuestion",e);
		}
	}

	@Override
	public void deleteQuestion(Long questionId) throws GenericException {
		try {
			_QuestionMcq questionToDelete = questionMcqDao.getEntityById(questionId);
			if(questionToDelete!=null){
				_Mcq attachedMcq = questionToDelete.getMcq();
				if(attachedMcq!=null){
					attachedMcq.setNbQuestions(attachedMcq.getNbQuestions()-1);
					//persistent --> automatically updated at end of transaction
				}
				questionMcqDao.removeEntity(questionToDelete);
			}
		} catch (Exception e) {
			throw new GenericException("echec deleteQuestion",e);
		}
	}

	@Override
	public QuestionMcq getQuestionById(Long questionId) throws GenericException {
		QuestionMcq question=null;
		try {
			question=genericInternalService.getDtoById(questionId);
		} catch (Exception e) {
			throw new GenericException("echec getQuestionById",e);
		}
		return question;
	}

	@Override
	public void updateResponseList(Long questionId,
			List<ResponseMcq> responseList) throws GenericException {
		try {
			_QuestionMcq persistentQuestion = questionMcqDao.getEntityById(questionId);
			List<_ResponseMcq> persistentResponseList = beanConverter.convertList(responseList, _ResponseMcq.class);
			persistentQuestion.setResponseList(persistentResponseList);
		} catch (Exception e) {
			throw new GenericException("echec updateResponseList",e);
		}
	}      
       


}
