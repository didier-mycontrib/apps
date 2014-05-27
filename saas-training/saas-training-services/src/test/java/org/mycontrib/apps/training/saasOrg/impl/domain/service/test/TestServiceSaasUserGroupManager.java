package org.mycontrib.apps.training.saasOrg.impl.domain.service.test;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasGroup;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasUser;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasUserGroupManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
public class TestServiceSaasUserGroupManager {
	
	 @Inject
		private SaasUserGroupManager service = null; // service metier a tester
		
	 @Test
	 public void testFindSaasGroupsOfOrg(){
		 for(SaasGroup g :service.findSaasGroupsOfOrg(1L))
			 System.out.println(g);
		 
	 }
	 
	 @Test
	 public void testFindSaasUsersOfGroup(){
		 for(SaasUser u :service.findSaasUsersOfGroup(1L))
			 System.out.println(u);
		 
	 }
	 
	 @Test
	 public void testFindSaasUserBySpecificRoleAccountId(){
		 SaasUser saasUser= service.findSaasUserBySpecificRoleAccountId(8L);
		 Assert.assertTrue(saasUser!=null);
		 System.out.println("saasUser with RoleAccountId=8:"+saasUser);//alex_therieur de initialDataSet.xml ?
	 }
}
