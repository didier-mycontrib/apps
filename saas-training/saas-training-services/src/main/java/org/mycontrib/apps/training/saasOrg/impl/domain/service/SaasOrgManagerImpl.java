package org.mycontrib.apps.training.saasOrg.impl.domain.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasOrg;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.itf.domain.enumeration.SaasRole;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager;
import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.service.internal.GenericInternalService;
import org.mycontrib.generic.service.internal.common.GenericInternalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService (endpointInterface="org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasOrgManager")
@Named
@Transactional()
public class SaasOrgManagerImpl implements SaasOrgManager {
	 private static Logger logger = LoggerFactory.getLogger(SaasOrgManagerImpl.class);
	 
	 private GenericInternalService<SaasOrg,Long> genericInternalServiceOrg =null;
	 
	 @PostConstruct  //@Post initialization(s) after injection
		protected void initGenericInternalService(){
		 genericInternalServiceOrg = new GenericInternalServiceImpl<SaasOrg,_SaasOrg,Long>(saasOrgDao,beanConverter,logger){};
			// new instance of anonymous inner class witch inherit of GenericSuperClass
		}
	 
	@Inject
	private DaoSaasOrg saasOrgDao;
	 
	@Inject
	private  DaoSaasRoleAccount saasRoleAccountDao;
		
	@Inject
	private GenericBeanConverter beanConverter;

	@Override
	public List<SaasOrg> findAllSaasOrg() {
		List<SaasOrg> orgList = null;
		try {
			List<_SaasOrg> persistentOrgList = saasOrgDao.findAllSaasOrg();
			orgList= beanConverter.convertList(persistentOrgList,SaasOrg.class);
		} catch (Exception e) {
			throw new GenericException("echec findAllSaasOrg",e);
		}
		return orgList;
	}

	@Override
	public SaasRoleAccount getSaasRoleAccountById(Long idAccount) {
		SaasRoleAccount saasRoleAccount =null;
		try {
			_SaasRoleAccount persistentSaasRoleAccount = saasRoleAccountDao.getEntityById(idAccount);
			saasRoleAccount= beanConverter.convert(persistentSaasRoleAccount,SaasRoleAccount.class);
		} catch (Exception e) {
			throw new GenericException("echec getSaasRoleAccountById",e);
		}
		return saasRoleAccount;
	}

	@Override
	public SaasRoleAccount findSaasRoleAccountByUsernameAndPassword(Long saasOrgId,
			String username, String password) {
		SaasRoleAccount saasRoleAccount =null;
		try {
		_SaasRoleAccount persistentSaasRoleAccount = saasRoleAccountDao.findSaasRoleAccountByUserNameAndPassword(saasOrgId,username,password);
		saasRoleAccount= beanConverter.convert(persistentSaasRoleAccount,SaasRoleAccount.class);
		}
		catch (Exception e) {
			throw new GenericException("echec findSaasRoleAccountByUsernameAndPassword",e);
		}
		return saasRoleAccount;
	}
	
	@Override
	public String getGenericUserName(SaasRole saasRole,String orgName){
		String username=null;
		switch(saasRole){
		case ADMIN_OF_ORG:
			username=SaasRoleAccount.GENERIC_ADMIN_OF_ORG_PREFIX+orgName;
			break;
		case AUTHOR_OF_ORG:
			username=SaasRoleAccount.GENERIC_AUTHOR_OF_ORG_PREFIX+orgName;
			break;
		case USER_OF_ORG:
			username=SaasRoleAccount.GENERIC_USER_OF_ORG_PREFIX+orgName;
			break;
		case ADMIN_OF_SAAS:
			username="adminOfSaas";
			break;
		default:
			username="???";
		}
		return username;
	}

	@Override
	public Long addSaasOrg(String name) throws GenericException {
		Long orgId= null;
		try {
			SaasOrg o = new SaasOrg();
			o.setName(name);
			orgId= genericInternalServiceOrg.persistIdNewEntityFromDto(o);
		} catch (Exception e) {
			throw new GenericException("echec addSaasOrg",e);
		}
		return orgId;
	}
	
	public static String firstUpper(String s){
		String res = s.substring(0, 1).toUpperCase() + s.substring(1);
		return res;
	}
	
	@Override
	public void buildGenericUsersForNewSaasOrg(Long orgId)throws GenericException{
		try {
		_SaasOrg saasOrg= saasOrgDao.getEntityById(orgId);
		
		_SaasRoleAccount genericUserOfNewOrg = new _SaasRoleAccount();
		genericUserOfNewOrg.setUserName(this.getGenericUserName(SaasRole.USER_OF_ORG, saasOrg.getName()));
		genericUserOfNewOrg.setPassword("pwd"+firstUpper(genericUserOfNewOrg.getUserName()));
		genericUserOfNewOrg.setGeneric(true);
		genericUserOfNewOrg.setSaasRole(SaasRole.USER_OF_ORG);
		_SaasRoleAccount persistentGenericUserOfNewOrg = saasRoleAccountDao.persistNewEntity(genericUserOfNewOrg);
		saasOrg.setGenericUserOfOrgAccount(persistentGenericUserOfNewOrg);
		
		_SaasRoleAccount genericAuthorOfNewOrg = new _SaasRoleAccount();
		genericAuthorOfNewOrg.setUserName(this.getGenericUserName(SaasRole.AUTHOR_OF_ORG, saasOrg.getName()));
		genericAuthorOfNewOrg.setPassword("pwd"+firstUpper(genericAuthorOfNewOrg.getUserName()));
		genericAuthorOfNewOrg.setGeneric(true);
		genericAuthorOfNewOrg.setSaasRole(SaasRole.AUTHOR_OF_ORG);
		_SaasRoleAccount persistentGenericAuthorOfNewOrg = saasRoleAccountDao.persistNewEntity(genericAuthorOfNewOrg);
		saasOrg.setGenericAuthorOfOrgAccount(persistentGenericAuthorOfNewOrg);
		
		_SaasRoleAccount genericAdminOfNewOrg = new _SaasRoleAccount();
		genericAdminOfNewOrg.setUserName(this.getGenericUserName(SaasRole.ADMIN_OF_ORG, saasOrg.getName()));
		genericAdminOfNewOrg.setPassword("pwd"+firstUpper(genericAdminOfNewOrg.getUserName()));
		genericAdminOfNewOrg.setGeneric(true);
		genericAdminOfNewOrg.setSaasRole(SaasRole.ADMIN_OF_ORG);
		_SaasRoleAccount persistentGenericAdminOfNewOrg = saasRoleAccountDao.persistNewEntity(genericAdminOfNewOrg);
		saasOrg.setGenericAdminOfOrgAccount(persistentGenericAdminOfNewOrg);
		
		//saasOrgDao.updateEntity(saasOrg);//automatic if saasOrgDao is in persistent state
		
		} catch (Exception e) {
			throw new GenericException("echec buildGenericUsersForNewSaasOrg",e);
		}
	}

	@Override
	public void deleteSaasOrg(Long orgId) throws GenericException {
		try {
			saasOrgDao.deleteEntity(orgId);
		} catch (Exception e) {
			throw new GenericException("echec deleteSaasOrg",e);
		}
	}

	@Override
	public void updateSaasOrg(SaasOrg org) throws GenericException {
		try {
			genericInternalServiceOrg.updateEntityFromDto(org);
			//update also attached generic user/author/admin:
			if(org.getGenericUserOfOrgAccount()!=null)
			    saasRoleAccountDao.updateEntity(beanConverter.convert(org.getGenericUserOfOrgAccount(),_SaasRoleAccount.class));
			if(org.getGenericAuthorOfOrgAccount()!=null)
				saasRoleAccountDao.updateEntity(beanConverter.convert(org.getGenericAuthorOfOrgAccount(),_SaasRoleAccount.class));
			if(org.getGenericAdminOfOrgAccount()!=null)
				saasRoleAccountDao.updateEntity(beanConverter.convert(org.getGenericAdminOfOrgAccount(),_SaasRoleAccount.class));
			
		} catch (Exception e) {
			throw new GenericException("echec updateSaasOrg",e);
		}
		
	}

	@Override 
	public SaasOrg getSaasOrgById(Long orgId) throws GenericException {
		SaasOrg org=null;
		try {
			org = genericInternalServiceOrg.getDtoById(orgId);
		} catch (Exception e) {
			throw new GenericException("echec getSaasOrgById",e);
		}
		return org;
	}

}
