package org.mycontrib.apps.training.impl.domain;

import org.mycontrib.apps.training.itf.domain.FacadeTraining;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasUserGroupManager;
import org.mycontrib.apps.training.session.itf.domain.service.McqUserSessionManager;
import org.mycontrib.generic.exception.GenericException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FacadeTrainingImpl implements FacadeTraining {
	
	
	private McqChooser serviceMcqChooser;
	private McqManager serviceMcqManager;
	private QuestionMcqManager serviceQuestionMcqManager;
	private McqUserSessionManager serviceMcqUserSessionManager;
	private SaasOrgManager serviceSaasOrgManager;
	private SaasUserGroupManager serviceSaasUserGroupManager;
	
	private ApplicationContext ctx;
	
	private void initServiceFromApplicationContext(){
		serviceMcqChooser=(McqChooser)ctx.getBean(McqChooser.class);
		serviceMcqManager= (McqManager)ctx.getBean(McqManager.class);
		serviceQuestionMcqManager= (QuestionMcqManager)ctx.getBean(QuestionMcqManager.class);
		serviceMcqUserSessionManager= (McqUserSessionManager)ctx.getBean(McqUserSessionManager.class);
		serviceSaasOrgManager= (SaasOrgManager) ctx.getBean(SaasOrgManager.class);
		serviceSaasUserGroupManager= (SaasUserGroupManager) ctx.getBean(SaasUserGroupManager.class);
	}
	
	public FacadeTrainingImpl(){
		ctx = new ClassPathXmlApplicationContext("springContextOfModule.xml");
		initServiceFromApplicationContext();
	}
	
	public FacadeTrainingImpl(ApplicationContext ctx){	
		//System.out.println("FacadeTrainingImpl with ctx:" + ctx);
		this.ctx=ctx;
		initServiceFromApplicationContext();
		}

	@Override
	public McqChooser getServiceMcqChooser() throws GenericException {
		return serviceMcqChooser;
	}

	@Override
	public McqManager getServiceMcqManager() throws GenericException {
		return serviceMcqManager;
	}

	@Override
	public QuestionMcqManager getServiceQuestionMcqManager()
			throws GenericException {
		return serviceQuestionMcqManager;
	}

	@Override
	public McqUserSessionManager getServiceMcqUserSessionManager()
			throws GenericException {
		return serviceMcqUserSessionManager;
	}

	@Override
	public SaasOrgManager getServiceSaasOrgManager() throws GenericException {
		return serviceSaasOrgManager;
	}

	@Override
	public SaasUserGroupManager getServiceSaasUserGroupManager()
			throws GenericException {
		return serviceSaasUserGroupManager;
	}
	
	
	
	
	
	

}
