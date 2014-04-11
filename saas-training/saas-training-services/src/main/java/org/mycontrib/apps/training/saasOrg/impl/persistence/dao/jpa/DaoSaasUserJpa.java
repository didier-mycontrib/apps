
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.jpa;

import javax.inject.Named;

import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasUser;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasUser;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoSaasUserJpa extends GenericDaoJpaSpring<_SaasUser,Long> implements DaoSaasUser {
	//Start of user code specific_dao_methods
	    //public List<_SaasUser> findSaasUserByXyz(...){ .... }
	//End of user code
}
