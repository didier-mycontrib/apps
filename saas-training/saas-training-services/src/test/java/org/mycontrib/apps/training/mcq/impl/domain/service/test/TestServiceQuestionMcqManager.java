
package org.mycontrib.apps.training.mcq.impl.domain.service.test;

	import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
public class TestServiceQuestionMcqManager { 

    @Inject
	private QuestionMcqManager service = null; // service metier a tester
	
	@Test
   public void test_QuestionMcqManager() {
   //Start of user code test_QuestionMcqManager_implementation
     try{
        System.out.println("test_QuestionMcqManager");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }   
	       

	@Test
   public void test_CRUD_question() {
   //Start of user code test_CRUD_question_implementation
     try{
        System.out.println("test_CRUD_question");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
	@Test
   public void test_CRUD_response() {
   //Start of user code test_CRUD_response_implementation
     try{
        System.out.println("test_CRUD_response");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
	@Test
   public void test_addResponse() {
   //Start of user code test_addResponse_implementation
     try{
        System.out.println("test_addResponse");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
      
}
