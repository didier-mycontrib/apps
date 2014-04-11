
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.test;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
import org.mycontrib.generic.test.spring.GenericDaoTestWithDbUnitSpring;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestDaoSaasOrgWithGenericAndDbUnit extends GenericDaoTestWithDbUnitSpring<_SaasOrg,Long>{ 

	private DaoSaasOrg dao = null; // dao à tester
	
	@Override
	public Long getPkOfEntity(_SaasOrg entity){
		return entity.getIdOrg();
	}
	
	@Inject
	public void setDao(DaoSaasOrg dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	} 
	
	public DaoSaasOrg getDao() {
		return dao;
	} 
	
	@Inject
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }    
	 
	 

	//Start of user code test_DaoSaasOrg_specific_methods
	@Test
    public void test_DaoSaasOrg_specific_methods() {
 
     try{
        System.out.println("test_DaoSaasOrg_specific_methods");
        //...
        }catch(RuntimeException ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
    }
    //End of user code
      
}
