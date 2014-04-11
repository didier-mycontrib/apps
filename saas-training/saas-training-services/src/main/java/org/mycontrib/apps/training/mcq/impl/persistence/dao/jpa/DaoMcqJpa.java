
package org.mycontrib.apps.training.mcq.impl.persistence.dao.jpa;

import javax.inject.Named;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._Mcq;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoMcqJpa extends GenericDaoJpaSpring<_Mcq,Long> implements DaoMcq {
	//Start of user code specific_dao_methods
	    //public List<_Mcq> findMcqByXyz(...){ .... }
	//End of user code
}
