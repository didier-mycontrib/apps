
package org.mycontrib.apps.training.mcq.itf.domain.service;

import java.util.List;

import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.QuestionMcq;
import org.mycontrib.generic.exception.GenericException;

@WebService
public interface McqManager {  
    public Long createMcq(Mcq mcq)throws GenericException; //return pk/id
    public void updateMcq(Mcq mcq)throws GenericException;
    public void deleteMcq(Long mcqId)throws GenericException;
	public Mcq getMcqById(Long id) throws GenericException;
	public String getMcqAsXmlString(Long idMcq) throws GenericException;//for export
	public Long storeOrUpdateMcqFromXmlString(String mcqXmlStr); //for import , return pk/id
	public void attachQuestion(Long idMcq, Long idQuestion) throws GenericException;//return pk/id
	public List<QuestionMcq> getQuestionList(Long idMcq) throws GenericException;
	public List<QuestionMcq> getRandomQuestionSubList(Long idMcq) throws GenericException;
}
