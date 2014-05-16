
package org.mycontrib.apps.training.mcq.impl.persistence.dao.jpa;

import java.util.List;

import javax.inject.Named;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcqSubject;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._McqSubject;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoMcqSubjectJpa extends GenericDaoJpaSpring<_McqSubject,Long> implements DaoMcqSubject {

	//Start of user code specific_dao_methods
	@Override
	public List<_McqSubject> findAllMcqSubject(Long ownerOrgId) {
		if(ownerOrgId!=null)
		    return this.entityManager.createQuery("select s from _McqSubject s where s.ownerOrgId=:orgId",_McqSubject.class).setParameter("orgId", ownerOrgId).getResultList();
		else
			return this.entityManager.createQuery("select s from _McqSubject s where s.shared=TRUE",_McqSubject.class).getResultList();
	}
	//End of user code

	

	@Override
	public List<_McqSubject> findSubjectListForMcq(Long mcqId) {
		return this.entityManager.createQuery(
				"select s from _McqSubject s inner join s.mcqList m where m.id=:mcqId",
				_McqSubject.class).setParameter("mcqId", mcqId).getResultList();
	}



}
