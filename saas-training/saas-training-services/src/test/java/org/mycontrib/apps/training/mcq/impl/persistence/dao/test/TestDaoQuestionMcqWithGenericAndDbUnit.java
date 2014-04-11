
package org.mycontrib.apps.training.mcq.impl.persistence.dao.test;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoQuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._QuestionMcq;
import org.mycontrib.generic.test.spring.GenericDaoTestWithDbUnitSpring;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
@TransactionConfiguration(transactionManager="txManager",defaultRollback=false)
public class TestDaoQuestionMcqWithGenericAndDbUnit extends GenericDaoTestWithDbUnitSpring<_QuestionMcq,Long>{ 

	private DaoQuestionMcq dao = null; // dao à tester
	
	@Override
	public Long getPkOfEntity(_QuestionMcq entity){
		return entity.getIdQuestion();
	}
	
	@Inject
	public void setDao(DaoQuestionMcq dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	} 
	
	public DaoQuestionMcq getDao() {
		return dao;
	} 
	
	@Inject
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }    
	 
	 

	//Start of user code test_DaoQuestionMcq_specific_methods
	@Test
    public void test_DaoQuestionMcq_specific_methods() {
 
     try{
        System.out.println("test_DaoQuestionMcq_specific_methods");
        //...
        }catch(RuntimeException ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
    }
    //End of user code
      
}
