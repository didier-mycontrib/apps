
package org.mycontrib.apps.training.session.impl.domain.service.test;

	import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.apps.training.session.itf.domain.dto.McqUserSession;
import org.mycontrib.apps.training.session.itf.domain.service.McqUserSessionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
	

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/serviceSpringConf.xml","/dataSourceForTestSpringConf.xml"})
public class TestServiceMcqUserSessionManager { 

    @Inject
	private McqUserSessionManager service = null; // service metier a tester
	
	@Test
   public void test_McqUserSessionManager() {
   //Start of user code test_McqUserSessionManager_implementation
     try{
        System.out.println("test_McqUserSessionManager");
            for(McqUserSession sess : service.getMcqSessionsOfUser(1L)){
            	System.out.println(sess);
            }
            	
        }catch(Exception /*ServiceException*/ ex){
      	    System.err.println(ex.getMessage());
      	    Assert.fail(ex.getMessage());
      	}
   //End of user code
   }   
	       

      
}
