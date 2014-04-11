
package org.mycontrib.apps.training.mcq.impl.persistence.dao.jpa;

import javax.inject.Named;

import org.mycontrib.apps.training.mcq.impl.persistence.dao.DaoQuestionMcq;
import org.mycontrib.apps.training.mcq.impl.persistence.entity._QuestionMcq;
import org.mycontrib.generic.persistence.spring.GenericDaoJpaSpring;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class DaoQuestionMcqJpa extends GenericDaoJpaSpring<_QuestionMcq,Long> implements DaoQuestionMcq {
	//Start of user code specific_dao_methods
	    //public List<_QuestionMcq> findQuestionMcqByXyz(...){ .... }
	//End of user code
}
