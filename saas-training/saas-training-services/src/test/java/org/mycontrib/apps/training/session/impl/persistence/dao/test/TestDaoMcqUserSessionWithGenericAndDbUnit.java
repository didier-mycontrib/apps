
package org.mycontrib.apps.training.session.impl.persistence.dao.test;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.session.impl.persistence.dao.DaoMcqUserSession;
import org.mycontrib.apps.training.session.impl.persistence.entity._McqUserSession;
import org.mycontrib.generic.test.spring.GenericDaoTestWithDbUnitSpring;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestDaoMcqUserSessionWithGenericAndDbUnit extends GenericDaoTestWithDbUnitSpring<_McqUserSession,Long>{ 

	private DaoMcqUserSession dao = null; // dao à tester
	
	@Override
	public Long getPkOfEntity(_McqUserSession entity){
		return entity.getMcqUserSessionId();
	}
	
	@Inject
	public void setDao(DaoMcqUserSession dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	} 
	
	public DaoMcqUserSession getDao() {
		return dao;
	} 
	
	@Inject
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }    
	 
	 

	//Start of user code test_DaoMcqUserSession_specific_methods
	@Test
    public void test_DaoMcqUserSession_specific_methods() {
 
     try{
        System.out.println("test_DaoMcqUserSession_specific_methods");
        //...
        }catch(RuntimeException ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
    }
    //End of user code
      
}
