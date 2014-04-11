
package org.mycontrib.apps.training.mcq.impl.domain.service.test;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
public class TestServiceMcqChooser { 

    @Inject
	private McqChooser service = null; // service metier a tester
	
	//@Test
   public void test_McqChooser() {
   //Start of user code test_McqChooser_implementation
     try{
        System.out.println("test_McqChooser");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }   
	       

	@Test
   public void test_getSubjectList() {
   //Start of user code test_getSubjectList_implementation
     try{
        System.out.println("test_getSubjectList");
         for(McqSubject s : service.getSubjectList(1L)){
        	 System.out.println("suject:"+s);
         }
        }catch(Exception /*ServiceException*/ ex){
      	   // System.err.println(ex.getMessage());
        	ex.printStackTrace();
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
	
	//@Test
   public void test_addSubject() {
   //Start of user code test_addSubject_implementation
     try{
        System.out.println("test_addSubject");
        //...
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
	@Test
   public void test_getMcqListBySubject() {
   //Start of user code test_getMcqBySubject_implementation
     try{
        System.out.println("test_getMcqBySubject");
        for(Mcq mcq : service.getMcqListBySubject(1L)){
        	System.out.println("mcq of subject_1 : " + mcq);
        }
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }
      
}
