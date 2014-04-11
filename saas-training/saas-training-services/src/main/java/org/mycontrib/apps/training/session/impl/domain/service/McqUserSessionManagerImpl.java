
package org.mycontrib.apps.training.session.impl.domain.service;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._QuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._ResponseMcq;
import org.mycontrib.apps.training.session.impl.persistence.dao.DaoMcqUserSession;
import org.mycontrib.apps.training.session.impl.persistence.entity._McqUserSession;
import org.mycontrib.apps.training.session.itf.domain.dto.McqUserSession;
import org.mycontrib.apps.training.session.itf.domain.dto.QuestionResponseChoice;
import org.mycontrib.apps.training.session.itf.domain.service.McqUserSessionManager;
import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.service.internal.GenericInternalService;
import org.mycontrib.generic.service.internal.common.GenericInternalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Named
@WebService (endpointInterface="org.xxx.myapp.sessionAndOwnership.itf.domain.service.McqUserSessionManager")
@Transactional()
public  class McqUserSessionManagerImpl implements McqUserSessionManager {
	 private static Logger logger = LoggerFactory.getLogger(McqUserSessionManagerImpl.class);
		
	private GenericInternalService<McqUserSession,Long> genericInternalService =null;
		
		@PostConstruct  //@Post initialization(s) after injection
		protected void initGenericInternalService(){
			genericInternalService = new GenericInternalServiceImpl<McqUserSession,_McqUserSession,Long>(mcqUserSessionDao,beanConverter,logger){};
			// new instance of anonymous inner class witch inherit of GenericSuperClass
		}
		
		@Inject
		private DaoMcqUserSession mcqUserSessionDao;
		
		
		@Inject
		private DaoMcq mcqDao;
		

	@Inject
	private GenericBeanConverter beanConverter;
//Start of user code specific attributes
	
//End of user code
	public McqUserSessionManagerImpl(){
		super(); 
	}

	@Override
	public Long createNewMcqUserSession(Long mcqId) {
		Long mcqUserSessionId=null;
		try {
			_McqUserSession persistentMcqUserSession = new _McqUserSession();
			persistentMcqUserSession.setMcq(mcqDao.getEntityById(mcqId));
			mcqUserSessionId=mcqUserSessionDao.persistIdNewEntity(persistentMcqUserSession);
		} catch (Exception e) {
			throw new GenericException("echec createNewMcqUserSession",e);
		}
		return mcqUserSessionId;
	}

	/*
	public void setChoiceList(Long userSessionId,
			List<QuestionResponseChoice> choiceList) {
		try {
			_McqUserSession persistentMcqUserSession = mcqUserSessionDao.getEntityById(userSessionId);
			persistentMcqUserSession.setChoices(beanConverter.convertList(choiceList, _QuestionResponseChoice.class));
		} catch (GenericException e) {
			throw new GenericException("echec setChoiceList",e);
		}
	}*/

	@Override
	public McqUserSession computeMcqScore(Long mcqId,Map<Integer,String> mapNumQuestionChoice) {
		McqUserSession computedMcqUserSession=new McqUserSession();
		try {
			_Mcq persistentMcq = mcqDao.getEntityById(mcqId);
			Integer subListSize= persistentMcq.getQuestionRandomSubListSize();
			int nbAskedQuestion = persistentMcq.getNbQuestions();
			if(subListSize!=null && subListSize>0 && subListSize<persistentMcq.getNbQuestions()){
				nbAskedQuestion=subListSize;
			}
			int nbGoodResponses=0;
			for(_QuestionMcq q :persistentMcq.getQuestionList()){
				String goodChoice=null;
				for(_ResponseMcq r: q.getResponseList()){
					if(r.getOk()!=null && r.getOk().booleanValue()==true)
						if(q.getExclusiveResponse()!=null && q.getExclusiveResponse().booleanValue()==true){
						    goodChoice=r.getResponseNum(); //en mode exclusif
						}else{
							if(goodChoice==null){
								goodChoice=r.getResponseNum();
							}else{
								goodChoice+=","+r.getResponseNum();  //ex: "A,B" , "A,D" , ...
							}	
						}
				}
				String userChoice = mapNumQuestionChoice.get(q.getQuestionNumber());
				if(userChoice!=null && userChoice.equals(goodChoice))
					nbGoodResponses++;
			}
			computedMcqUserSession.setNbGoodResponses(nbGoodResponses);
			computedMcqUserSession.setPctGoodResponses(String.valueOf(100.0 * (double)nbGoodResponses / (double)nbAskedQuestion) + "%" );
			computedMcqUserSession.setSubmitDateTime(DateFormat.getDateTimeInstance().format(new java.util.Date()));
			
		} catch (GenericException e) {
			throw new GenericException("echec computeMcqScore",e);
		}
		return computedMcqUserSession;
	}      
       

//other specific methods :         
}
