
package org.mycontrib.apps.training.session.impl.persistence.dao.jpa;

import java.util.List;

import javax.inject.Named;

import org.mycontrib.apps.training.session.impl.persistence.dao.DaoMcqUserSession;
import org.mycontrib.apps.training.session.impl.persistence.entity._McqUserSession;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoMcqUserSessionJpa extends GenericDaoJpaSpring<_McqUserSession,Long> implements DaoMcqUserSession {

	

	@Override
	public List<_McqUserSession> getMcqSessionsOfUser(Long saasUserId) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("select sess from _McqUserSession sess where sess.user.userId=:uid",
				                        _McqUserSession.class)
				            .setParameter("uid", saasUserId)
				            .getResultList();
	}
	//Start of user code specific_dao_methods
	    //public List<_McqUserSession> findMcqUserSessionByXyz(...){ .... }
	//End of user code
}
