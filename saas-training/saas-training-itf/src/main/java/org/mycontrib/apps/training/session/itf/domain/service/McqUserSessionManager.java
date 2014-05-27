
package org.mycontrib.apps.training.session.itf.domain.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.mycontrib.apps.training.session.itf.domain.dto.McqUserSession;

@WebService
public interface McqUserSessionManager {  
	public Long createNewMcqUserSession(Long mcqId);
	//public void setChoiceList(Long userSessionId,List<QuestionResponseChoice> choiceList);
	//public McqUserSession computeMcqScore(Long userSessionId);
	public McqUserSession computeMcqScore(Long mcqId,Map<Integer,String> mapNumQuestionChoice);// version sans persistance
	//+saasUser , +officialSession, ...
	public Long storeNewComputedMcqUserSession(McqUserSession computedMcqUserSession,Long mcqId,Long saasUserId );
	public List<McqUserSession> getMcqSessionsOfUser(Long saasUserId);
}
