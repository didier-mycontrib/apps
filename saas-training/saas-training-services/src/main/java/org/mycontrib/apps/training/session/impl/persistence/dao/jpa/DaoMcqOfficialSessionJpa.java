
package org.mycontrib.apps.training.session.impl.persistence.dao.jpa;

import javax.inject.Named;

import org.mycontrib.apps.training.session.impl.persistence.dao.DaoMcqOfficialSession;
import org.mycontrib.apps.training.session.impl.persistence.entity._McqOfficialSession;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoMcqOfficialSessionJpa extends GenericDaoJpaSpring<_McqOfficialSession,Long> implements DaoMcqOfficialSession {
	//Start of user code specific_dao_methods
	    //public List<_McqOfficialSession> findMcqOfficialSessionByXyz(...){ .... }
	//End of user code
}
