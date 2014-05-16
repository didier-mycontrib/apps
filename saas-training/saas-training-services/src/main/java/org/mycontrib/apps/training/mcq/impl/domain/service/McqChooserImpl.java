
package org.mycontrib.apps.training.mcq.impl.domain.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcqSubject;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._McqSubject;
import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.service.internal.GenericInternalService;
import org.mycontrib.generic.service.internal.common.GenericInternalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService (endpointInterface="org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser")
@Named
@Transactional()
public  class McqChooserImpl implements McqChooser {
	
     private static Logger logger = LoggerFactory.getLogger(McqChooserImpl.class);
	
	private GenericInternalService<McqSubject,Long> genericInternalService =null;
	
	@PostConstruct  //@Post initialization(s) after injection
	protected void initGenericInternalService(){
		genericInternalService = new GenericInternalServiceImpl<McqSubject,_McqSubject,Long>(subjectDao,beanConverter,logger){};
		// new instance of anonymous inner class witch inherit of GenericSuperClass
	}
	
	@Inject
	private DaoMcqSubject subjectDao;
	
	@Inject
	private DaoMcq mcqDao;

	@Inject
	private GenericBeanConverter beanConverter;

	public McqChooserImpl(){
		super(); 
	}      
       


	@Override
	public void deleteSubject(Long subjectId) throws GenericException {
		try {
			_McqSubject s = subjectDao.getEntityById(subjectId);
			//v1 : suppression en cascade des qcm (sans alternative : copies , sauvegardes , ...):
			for(_Mcq mcq : s.getMcqList()){
				mcqDao.removeEntity(mcq);
			}
			subjectDao.removeEntity(s);
			
		} catch (Exception e) {
			throw new GenericException("echec deleteSubject",e);
		}
	}


	@Override
	public void updateSubject(McqSubject subject) throws GenericException {
		try {
			genericInternalService.updateEntityFromDto(subject);
		} catch (Exception e) {
			throw new GenericException("echec updateSubject",e);
		}
	}


	@Override
	public Long addSubject(String title,Long ownerOrgId,Boolean shared) throws GenericException {
		Long subjectId= null;
		try {
			McqSubject s = new McqSubject();
			s.setTitle(title);
			s.setOwnerOrgId(ownerOrgId);
			if(shared==null) 
				shared=false;
			s.setShared(shared);
			subjectId= genericInternalService.persistIdNewEntityFromDto(s);
		} catch (Exception e) {
			throw new GenericException("echec addSubject",e);
		}
		return subjectId;
	}


	@Override
	public List<Mcq> getMcqListBySubject(Long subjectId)
			throws GenericException {
		List<Mcq> listMcq = null;
		try {
			_McqSubject persistentSubject = subjectDao.getEntityById(subjectId);
			listMcq=beanConverter.convertList(persistentSubject.getMcqList(),Mcq.class);
		} catch (Exception e) {
			throw new GenericException("echec getMcqListBySubject",e);
		}
		return listMcq;
	}



	@Override
	public List<McqSubject> getSubjectList(Long ownerOrgId) throws GenericException {
		 List<McqSubject> listSubject = null;
		 try {
			if(ownerOrgId!=null){
			    listSubject=beanConverter.convertList(subjectDao.findAllMcqSubject(ownerOrgId),McqSubject.class);
			}
			else {
				//si ownerOrgId , retourner tous les sujets publics (shared=true):
				listSubject=beanConverter.convertList(subjectDao.findAllMcqSubject(null),McqSubject.class);
			}
		} catch (Exception e) {
			throw new GenericException("echec getSubjectList",e);
		}
		return listSubject;
	}



	@Override
	public void addMcqInSubject(Long subjectId, Long mcqId)
			throws GenericException {
		 try {
			 _McqSubject persistentSubject = subjectDao.getEntityById(subjectId);
			boolean alreadyAttached=false;
			
			for(_Mcq mcq : persistentSubject.getMcqList()){
					if(mcq.getId().equals(mcqId)){
						alreadyAttached=true; break;
					}
			}
			if(!alreadyAttached){
			       _Mcq persistentMcq =mcqDao.getEntityById(mcqId);
			       persistentSubject.getMcqList().add(persistentMcq);
			}
		//subjectDao.updateEntity(persistentSubject); automatic in @Transactional for persistent entity
		 } catch (Exception e) {
				throw new GenericException("echec addMcqInSubject",e);
			}
	}
}
