
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.jpa;

import javax.inject.Named;

import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasGroup;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasGroup;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoSaasGroupJpa extends GenericDaoJpaSpring<_SaasGroup,Long> implements DaoSaasGroup {
	//Start of user code specific_dao_methods
	    //public List<_SaasGroup> findSaasGroupByXyz(...){ .... }
	//End of user code
}
