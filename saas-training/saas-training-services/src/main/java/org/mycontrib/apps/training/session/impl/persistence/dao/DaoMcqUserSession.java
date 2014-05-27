
package org.mycontrib.apps.training.session.impl.persistence.dao;

import java.util.List;

import org.mycontrib.apps.training.session.impl.persistence.entity._McqUserSession;
import org.mycontrib.generic.persistence.GenericDao;

public interface DaoMcqUserSession extends GenericDao<_McqUserSession,Long> {

	public List<_McqUserSession> getMcqSessionsOfUser(Long saasUserId);
	//Start of user code specific_dao_methods
	    //public List<_McqUserSession> findMcqUserSessionByXyz(...);
	//End of user code
}
