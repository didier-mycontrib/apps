
package org.mycontrib.apps.training.mcq.impl.persistence.dao.test;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoMcqSubject;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._McqSubject;
import org.mycontrib.generic.test.spring.GenericDaoTestWithDbUnitSpring;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestDaoMcqSubjectWithGenericAndDbUnit extends GenericDaoTestWithDbUnitSpring<_McqSubject,Long>{ 

	private DaoMcqSubject dao = null; // dao à tester
	
	@Override
	public Long getPkOfEntity(_McqSubject entity){
		return entity.getSubjectId();
	}
	
	@Inject
	public void setDao(DaoMcqSubject dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	} 
	
	public DaoMcqSubject getDao() {
		return dao;
	} 
	
	@Inject
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }    
	 
	 

	//Start of user code test_DaoMcqSubject_specific_methods
	@Test
    public void test_DaoMcqSubject_specific_methods() {
 
     try{
        System.out.println("test_DaoMcqSubject_specific_methods");
        //...
        }catch(RuntimeException ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
    }
    //End of user code
      
}
