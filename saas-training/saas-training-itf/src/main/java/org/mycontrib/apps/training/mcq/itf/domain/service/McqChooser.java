
package org.mycontrib.apps.training.mcq.itf.domain.service;

import java.util.List;

import javax.jws.WebService;

import org.mycontrib.apps.training.mcq.itf.domain.dto.Mcq;
import org.mycontrib.apps.training.mcq.itf.domain.dto.McqSubject;
import org.mycontrib.generic.exception.GenericException;

@WebService
public interface McqChooser {  
	public List<McqSubject> getSubjectList(Long ownerOrgId) throws GenericException;//if ownerOrgId==null , return all public/shared
	public void deleteSubject(Long subjectId) throws GenericException;
	public void addMcqInSubject(Long subjectId,Long mcqId) throws GenericException;
	public void updateSubject(McqSubject subject) throws GenericException;
	public Long addSubject(String title,Long ownerOrgId,Boolean shared) throws GenericException;//return id/pk
	public List<Mcq> getMcqListBySubject(Long subjectId) throws GenericException;//liste de Mcq sans sous liste de questions
}
