
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao;

import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasRoleAccount;
import org.mycontrib.generic.persistence.GenericDao;

public interface DaoSaasRoleAccount extends GenericDao<_SaasRoleAccount,Long> {
	//Start of user code specific_dao_methods
	    public _SaasRoleAccount findSaasRoleAccountByUserNameAndPassword(Long saasOrgId,String username,String password);
	//End of user code
}
