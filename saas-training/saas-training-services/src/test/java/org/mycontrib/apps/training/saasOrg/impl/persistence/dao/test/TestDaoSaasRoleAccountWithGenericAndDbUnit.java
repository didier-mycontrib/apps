
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.test;

	import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasRoleAccount;
import org.mycontrib.generic.test.spring.GenericDaoTestWithDbUnitSpring;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestDaoSaasRoleAccountWithGenericAndDbUnit extends GenericDaoTestWithDbUnitSpring<_SaasRoleAccount,Long>{ 

	private DaoSaasRoleAccount dao = null; // dao à tester
	
	@Override
	public Long getPkOfEntity(_SaasRoleAccount entity){
		return entity.getIdAccount();
	}
	
	@Inject
	public void setDao(DaoSaasRoleAccount dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	} 
	
	public DaoSaasRoleAccount getDao() {
		return dao;
	} 
	
	@Inject
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }    
	 
	 

	//Start of user code test_DaoSaasRoleAccount_specific_methods
	@Test
    public void test_DaoSaasRoleAccount_specific_methods() {
 
     try{
        System.out.println("test_DaoSaasRoleAccount_specific_methods");
        //...
        }catch(RuntimeException ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
    }
    //End of user code
      
}
