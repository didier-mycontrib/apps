
package org.mycontrib.apps.training.mcq.impl.persistence.dao;
//Start of user code java_imports
	import java.util.List;

import org.mycontrib.apps.training.mcq.impl.persistence.entity._McqSubject;
import org.mycontrib.generic.persistence.GenericDao;
//End of user code
public interface DaoMcqSubject extends GenericDao<_McqSubject,Long> {
	//Start of user code specific_dao_methods
	    public List<_McqSubject> findAllMcqSubject(Long ownerOrgId);//return all public/shared if ownerOrgId is null
	    public List<_McqSubject> findSubjectListForMcq(Long mcqId);
	//End of user code
		
}
