package org.mycontrib.apps.training.saasOrg.impl.domain.service.test;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
public class TestServiceSaasOrgManager {
	
	 @Inject
		private SaasOrgManager service = null; // service metier a tester
		
	 @Test
	 public void testFindSaasRoleAccountByUserNameAndPassword(){
		 SaasRoleAccount saasRoleAccount =service.findSaasRoleAccountByUsernameAndPassword(1L, "adminOf_org1", "adm1");
		 Assert.assertNotNull(saasRoleAccount);
		 System.out.println("saasRoleAccount:" + saasRoleAccount);
		 SaasRoleAccount adminOfSaas =service.findSaasRoleAccountByUsernameAndPassword(null, "adminOfSaas", "saas");
		 Assert.assertNotNull(adminOfSaas);
		 System.out.println("adminOfSaas:" + adminOfSaas);
		 SaasRoleAccount specifUserRA =service.findSaasRoleAccountByUsernameAndPassword(1L, "alain-therieur", "pwd");
		 Assert.assertNotNull(specifUserRA);
		 System.out.println("specif_user:" + specifUserRA);
		 SaasRoleAccount anonymousUser =service.findSaasRoleAccountByUsernameAndPassword(3L, "anonymous", "");
		 Assert.assertNotNull(anonymousUser);
		 System.out.println("anonymous_user:" + anonymousUser);
	 }
}
