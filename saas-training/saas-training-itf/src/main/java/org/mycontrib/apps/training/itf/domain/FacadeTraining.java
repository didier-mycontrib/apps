package org.mycontrib.apps.training.itf.domain;

import org.mycontrib.apps.training.mcq.itf.domain.service.McqChooser;
import org.mycontrib.apps.training.mcq.itf.domain.service.McqManager;
import org.mycontrib.apps.training.mcq.itf.domain.service.QuestionMcqManager;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasUserGroupManager;
import org.mycontrib.apps.training.session.itf.domain.service.McqUserSessionManager;
import org.mycontrib.generic.exception.GenericException;

public interface FacadeTraining {
    public McqChooser getServiceMcqChooser() throws GenericException;
    public McqManager getServiceMcqManager() throws GenericException;
    public QuestionMcqManager getServiceQuestionMcqManager() throws GenericException;
    public McqUserSessionManager getServiceMcqUserSessionManager() throws GenericException;
    public SaasOrgManager getServiceSaasOrgManager() throws GenericException;
    public SaasUserGroupManager getServiceSaasUserGroupManager() throws GenericException;
}
