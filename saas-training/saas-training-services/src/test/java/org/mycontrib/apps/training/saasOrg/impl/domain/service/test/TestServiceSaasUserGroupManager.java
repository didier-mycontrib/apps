package org.mycontrib.apps.training.saasOrg.impl.domain.service.test;

import java.util.List;

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
	 public void testAddNewGroup(){
		 Long orgId=1L;
		 int intialGroupListSize= service.findSaasGroupsOfOrg(1L).size();
		 Long newGroupId = service.addSaasGroup(orgId, "new_group_of_org1", "info qui va bien");
		 Assert.assertTrue(newGroupId !=null);
		 List<SaasGroup> groupList = service.findSaasGroupsOfOrg(1L);
		 int updatedGroupListSize= groupList.size();
		 Assert.assertTrue(updatedGroupListSize == intialGroupListSize+1);
		 for(SaasGroup g :groupList)
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
