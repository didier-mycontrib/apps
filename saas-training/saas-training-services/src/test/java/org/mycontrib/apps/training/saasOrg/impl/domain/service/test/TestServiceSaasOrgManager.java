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
		 SaasRoleAccount saasRoleAccount =service.findSaasRoleAccountByUsernameAndPassword(1L, "adminOf_org1", "pwdAdminOf_org1");
		 Assert.assertNotNull(saasRoleAccount);
		 System.out.println("saasRoleAccount:" + saasRoleAccount);
		 SaasRoleAccount adminOfSaas =service.findSaasRoleAccountByUsernameAndPassword(null, "adminOfSaas", "pwdAdminOfSaas");
		 Assert.assertNotNull(adminOfSaas);
		 System.out.println("adminOfSaas:" + adminOfSaas);
	 }
}
