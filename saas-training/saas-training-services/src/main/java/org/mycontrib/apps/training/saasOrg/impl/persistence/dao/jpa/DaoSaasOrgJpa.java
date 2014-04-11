
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.jpa;

import java.util.List;

import javax.inject.Named;

import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoSaasOrgJpa extends GenericDaoJpaSpring<_SaasOrg,Long> implements DaoSaasOrg {

	

	@Override
	public List<_SaasOrg> findAllSaasOrg() {
		return this.entityManager.createQuery("select o from _SaasOrg o",_SaasOrg.class).getResultList();
	}
	
}
