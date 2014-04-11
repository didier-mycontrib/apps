
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.jpa;

import javax.inject.Named;

import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoSaasRoleAccountJpa extends GenericDaoJpaSpring<_SaasRoleAccount,Long> implements DaoSaasRoleAccount {

	
	@Override
	public _SaasRoleAccount findSaasRoleAccountByUserNameAndPassword(
			Long saasOrgId, String username, String password) {
		_SaasRoleAccount saasRoleAccount=null;
		if(saasOrgId!=null){
		_SaasOrg saasOrg = entityManager.find(_SaasOrg.class, saasOrgId);
		
		if(saasOrg.getGenericUserOfOrgAccount().getUserName().equals(username) 
				&& saasOrg.getGenericUserOfOrgAccount().getPassword().equals(password))
					saasRoleAccount=saasOrg.getGenericUserOfOrgAccount();
		if(saasRoleAccount==null)
			if(saasOrg.getGenericAuthorOfOrgAccount().getUserName().equals(username) 
					&& saasOrg.getGenericAuthorOfOrgAccount().getPassword().equals(password))
						saasRoleAccount=saasOrg.getGenericAuthorOfOrgAccount();
		if(saasRoleAccount==null)
			if(saasOrg.getGenericAdminOfOrgAccount().getUserName().equals(username) 
					   && saasOrg.getGenericAdminOfOrgAccount().getPassword().equals(password))
						     saasRoleAccount=saasOrg.getGenericAdminOfOrgAccount();
		//V2: rechercher en + dans user of grouplist
		}else{
			//search _SaasRoleAccount with username,password and role=ADMIN_OF_SAAS
			_SaasRoleAccount aSaasRoleAccount = 
					entityManager.createQuery(
							"select sra from _SaasRoleAccount sra where sra.userName = :username and sra.password = :password",
							_SaasRoleAccount.class)
							.setParameter("username", username)
							.setParameter("password", password)
							.getSingleResult();
			if(aSaasRoleAccount!=null && aSaasRoleAccount.getSaasRole()==SaasRole.ADMIN_OF_SAAS)
				saasRoleAccount=aSaasRoleAccount;
		}
		return saasRoleAccount;
	}
	
}
