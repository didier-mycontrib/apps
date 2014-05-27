
package org.mycontrib.apps.training.session.impl.domain.service;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._QuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._ResponseMcq;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasUser;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasUser;
import org.mycontrib.apps.training.session.impl.persistence.dao.DaoMcqUserSession;
import org.mycontrib.apps.training.session.impl.persistence.entity._McqUserSession;
import org.mycontrib.apps.training.session.itf.domain.dto.McqUserSession;
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
		private DaoSaasUser daoSaasUser;
		
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
	public Long storeNewComputedMcqUserSession(McqUserSession computedMcqUserSession,Long mcqId,Long saasUserId) {
		
		//a faire dans future version: stocker en base les bonnes/mauvaises reponses donnees ????
		//seulement dans le cas d'une session officielle:
		// si generic_user : aucun stockage en base
		// si specific_user et pas session officielle : stocker que le score + date
		// si specific_user et session officielle : stocker tous les détails en base
		
		Long mcqUserSessionId=null;
		try {
			_McqUserSession persistentMcqUserSession = beanConverter.convert(computedMcqUserSession, _McqUserSession.class);			
			persistentMcqUserSession.setMcq(mcqDao.getEntityById(mcqId));
			persistentMcqUserSession.setUser(daoSaasUser.getEntityById(saasUserId));
			mcqUserSessionId = mcqUserSessionDao.persistIdNewEntity(persistentMcqUserSession);			
		} catch (Exception e) {
			throw new GenericException("echec storeNewComputedMcqUserSession",e);
		}
		return mcqUserSessionId;
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

	//non persistant:
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

	@Override
	public List<McqUserSession> getMcqSessionsOfUser(Long saasUserId) {
		try{
		   return  beanConverter.convertList(mcqUserSessionDao.getMcqSessionsOfUser(saasUserId),McqUserSession.class);
	   } catch (Exception e) {
		   throw new GenericException("echec createNewMcqUserSession",e);
	   }
	}      
       

//other specific methods :         
}
