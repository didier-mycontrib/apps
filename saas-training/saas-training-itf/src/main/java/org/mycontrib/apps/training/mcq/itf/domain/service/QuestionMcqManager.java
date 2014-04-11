
package org.mycontrib.apps.training.mcq.itf.domain.service;

import java.util.List;

import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.ResponseMcq;
import org.mycontrib.generic.exception.GenericException;

@WebService
public interface QuestionMcqManager {  

	public Long createQuestion(QuestionMcq q) throws GenericException;//return pk/id
	public void updateQuestion(QuestionMcq q) throws GenericException;
	public void deleteQuestion(Long questionId) throws GenericException;
	public QuestionMcq getQuestionById(Long questionId) throws GenericException;//return question with ResponseList
	
	public void updateResponseList(Long questionId,List<ResponseMcq> responseList) throws GenericException;
}
