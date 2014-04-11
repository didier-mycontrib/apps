
package org.mycontrib.apps.training.mcq.impl.domain.service.test;

	import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.ResponseMcq;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
public class TestServiceMcqManager { 

    @Inject
	private McqManager service = null; // service metier a tester
	
	//@Test
   public void test_McqManager() {
   //Start of user code test_McqManager_implementation
     try{
        System.out.println("test_McqManager");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }   
	    
   @Test
   public void testRandom(){
	   List l = new ArrayList();
	   l.add("1");l.add("2");l.add("3");l.add("4");l.add("5");l.add("6");
	   int s = l.size();
	   double dr=  Math.random();
	   int r = Math.min((int) (s * dr),s-1);
	   System.out.println("dr="+dr + ", r=" + r);
	   l.remove(r);
	   System.out.println("liste:"+l + "of size:" + l.size());
   }

	@Test
   public void test_addQuestion() {
   //Start of user code test_addQuestion_implementation
     try{
        System.out.println("test_addQuestion");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
	@Test
   public void test_getMcqById() {
   //Start of user code test_removeQuestion_implementation
     try{
        System.out.println("test_getMcqById");
        System.out.println(service.getMcqById(1L).toString());
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
	//@Test
	public void test_deleteMcq(){
		 try{
		        System.out.println("testDeleteMcq");
		        service.deleteMcq(2L);
		        }catch(Exception /*ServiceException*/ ex){
		      	    //System.err.println(ex.getMessage());
		        	ex.printStackTrace();
		      	    Assert.fail(ex.getMessage());
		      	}
	}
	
	@Test
	public void test_getMcqAsXmlString_for_export(){
		try{
	        System.out.println("test_getMcqAsXmlString");
	        System.out.println(service.getMcqAsXmlString(1L));
	        }catch(Exception /*ServiceException*/ ex){
	      	    System.err.println(ex.getMessage());

	      	}
	}
	
	@Test
   public void test_getQuestionList() {
   //Start of user code test_getQuestionList_implementation
     try{
        System.out.println("test_getQuestionList");
       for(QuestionMcq q : service.getQuestionList(1L)){
    	   System.out.println(q);
    	   for(ResponseMcq r : q.getResponseList()){
    		  System.out.println("\t"+r);
    	   }
       }
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
      
}
