package org.mycontrib.apps.training.saasOrg.itf.domain.service;

import java.util.List;

import javax.jws.WebService;

import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasGroup;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasUser;
import org.mycontrib.generic.exception.GenericException;

@WebService
public interface SaasUserGroupManager {
	
	public List<SaasGroup> findSaasGroupsOfOrg(Long orgId)throws GenericException;
	public Long addSaasGroup(Long orgId,String name,String info) throws GenericException;//return id/pk
	public void deleteSaasGroup(Long groupId) throws GenericException;
	//public void updateSaasGroup(SaasGroup g) throws GenericException;
	
	
	public List<SaasUser> findSaasUsersOfGroup(Long groupId)throws GenericException;
	public Long addSaasUser(Long groupId,SaasUser newUser) throws GenericException;//return id/pk
	public void deleteSaasUser(Long userId) throws GenericException;
	public void updateSaasUser(SaasUser u) throws GenericException;//update also attached SaasRoleAccount
	
}
