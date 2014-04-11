
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao;

import java.util.List;

import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
import org.mycontrib.generic.persistence.GenericDao;

public interface DaoSaasOrg extends GenericDao<_SaasOrg,Long> {
	//Start of user code specific_dao_methods
	    public List<_SaasOrg> findAllSaasOrg();
	//End of user code
}
