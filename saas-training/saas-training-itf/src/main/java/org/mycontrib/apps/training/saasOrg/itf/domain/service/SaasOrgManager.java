package org.mycontrib.apps.training.saasOrg.itf.domain.service;

import java.util.List;

import javax.jws.WebService;

import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasOrg;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;
import org.mycontrib.generic.exception.GenericException;

@WebService
public interface SaasOrgManager {
	
	public List<SaasOrg> findAllSaasOrg()throws GenericException;
	public SaasOrg getSaasOrgById(Long orgId)throws GenericException;
	public SaasRoleAccount getSaasRoleAccountById(Long idAccount)throws GenericException;
	public Long addSaasOrg(String name) throws GenericException;//return id/pk  
	public void buildGenericUsersForNewSaasOrg(Long orgId)throws GenericException;//build and attach defaut generic user/author/admin
	public void deleteSaasOrg(Long orgId) throws GenericException;
	public void updateSaasOrg(SaasOrg org) throws GenericException;//update also attached generic user
	public SaasRoleAccount findSaasRoleAccountByUsernameAndPassword(Long saasOrgId,String username,String password)throws GenericException;;
	public String getGenericUserName(SaasRole saasRole,String orgName);

}
