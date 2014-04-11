
package org.mycontrib.apps.training.saasOrg.impl.persistence.dao.test;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasGroup;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasGroup;
import org.mycontrib.generic.test.spring.GenericDaoTestWithDbUnitSpring;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestDaoSaasGroupWithGenericAndDbUnit extends GenericDaoTestWithDbUnitSpring<_SaasGroup,Long>{ 

	private DaoSaasGroup dao = null; // dao à tester
	
	@Override
	public Long getPkOfEntity(_SaasGroup entity){
		return entity.getIdGroup();
	}
	
	@Inject
	public void setDao(DaoSaasGroup dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	} 
	
	public DaoSaasGroup getDao() {
		return dao;
	} 
	
	@Inject
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }    
	 
	 

	//Start of user code test_DaoSaasGroup_specific_methods
	@Test
    public void test_DaoSaasGroup_specific_methods() {
 
     try{
        System.out.println("test_DaoSaasGroup_specific_methods");
        //...
        }catch(RuntimeException ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
    }
    //End of user code
      
}
