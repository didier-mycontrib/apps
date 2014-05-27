package org.mycontrib.apps.training.saasOrg.impl.domain.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasGroup;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.impl.persistence.dao.DaoSaasUser;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasGroup;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasOrg;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasRoleAccount;
import org.mycontrib.apps.training.saasOrg.impl.persistence.entity._SaasUser;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasGroup;
import org.mycontrib.apps.training.saasOrg.itf.domain.dto.SaasUser;
import org.mycontrib.apps.training.saasOrg.itf.domain.service.SaasUserGroupManager;
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
public class SaasUserGroupManagerImpl implements SaasUserGroupManager {
	 private static Logger logger = LoggerFactory.getLogger(SaasUserGroupManagerImpl.class);
	 
	 private GenericInternalService<SaasGroup,Long> genericInternalServiceGroup =null;
	 private GenericInternalService<SaasUser,Long> genericInternalServiceUser =null;
	 
	 @PostConstruct  //@Post initialization(s) after injection
		protected void initGenericInternalService(){
		 genericInternalServiceGroup = new GenericInternalServiceImpl<SaasGroup,_SaasGroup,Long>(saasGroupDao,beanConverter,logger){};
		 genericInternalServiceUser = new GenericInternalServiceImpl<SaasUser,_SaasUser,Long>(saasUserDao,beanConverter,logger){};
			// new instance of anonymous inner class witch inherit of GenericSuperClass
		}
	 
	@Inject
	private DaoSaasOrg saasOrgDao;
	
	@Inject
	private DaoSaasGroup saasGroupDao;
	
	@Inject
	private DaoSaasUser saasUserDao;
	 
	@Inject
	private DaoSaasRoleAccount saasRoleAccountDao;
		
	@Inject
	private GenericBeanConverter beanConverter;


	@Override
	public List<SaasGroup> findSaasGroupsOfOrg(Long orgId)
			throws GenericException {
		List<SaasGroup> groupList=null;
		try {
		_SaasOrg persistentOrg = saasOrgDao.getEntityById(orgId);
		groupList= beanConverter.convertList(persistentOrg.getOrgGroupList(), SaasGroup.class);
		} catch (Exception e) {
			throw new GenericException("echec findSaasGroupsOfOrg",e);
		}
		return groupList;
	}

	@Override
	public Long addSaasGroup(Long orgId, String name, String info)
			throws GenericException {
		Long groupId= null;
		try {
			SaasGroup g = new SaasGroup();
			g.setName(name);g.setInfo(info);
			groupId= genericInternalServiceGroup.persistIdNewEntityFromDto(g);
			//a faire : ajouter/attacher dans liste de groupes de SaasOrg
		} catch (Exception e) {
			throw new GenericException("echec addSaasGroup",e);
		}
		return groupId;
	}

	@Override
	public void deleteSaasGroup(Long groupId) throws GenericException {
		try {
			saasGroupDao.deleteEntity(groupId);
		} catch (Exception e) {
			throw new GenericException("echec deleteSaasGroup",e);
		}
	}

	@Override
	public List<SaasUser> findSaasUsersOfGroup(Long groupId)
			throws GenericException {
		List<SaasUser> userList=null;
		try {
		_SaasGroup persistentGroup = saasGroupDao.getEntityById(groupId);
		userList= beanConverter.convertList(persistentGroup.getUserList(), SaasUser.class);
		} catch (Exception e) {
			throw new GenericException("echec findSaasUsersOfGroup",e);
		}
		return userList;
	}

	@Override
	public Long addSaasUser(Long groupId, SaasUser newUser)
			throws GenericException {
		Long userId= null;
		try {
			userId= genericInternalServiceUser.persistIdNewEntityFromDto(newUser);
			//ajouter/attacher dans liste de users de SaasGroup:
			_SaasGroup saasGroup = saasGroupDao.getEntityById(groupId);
			_SaasUser saasUser = saasUserDao.getEntityById(userId);
			 saasGroup.getUserList().add(saasUser);
			 //saasGroupDao.updateEntity(saasGroup); //automatic in persitent state
		} catch (Exception e) {
			throw new GenericException("echec addSaasUser",e);
		}
		return userId;
	}

	@Override
	public void deleteSaasUser(Long userId) throws GenericException {
		try {
			saasUserDao.deleteEntity(userId);
		} catch (Exception e) {
			throw new GenericException("echec deleteSaasUser",e);
		}
	}

	@Override
	public void updateSaasUser(SaasUser u) throws GenericException {
		try {
			genericInternalServiceUser.updateEntityFromDto(u);
			if(u.getSaasAccount()!=null){
			    saasRoleAccountDao.updateEntity(beanConverter.convert(u.getSaasAccount(),_SaasRoleAccount.class));
			}
		} catch (Exception e) {
			throw new GenericException("echec updateSaasUser",e);
		}
	}

	@Override
	public SaasUser getSaasUserById(Long userId) throws GenericException {
		SaasUser saasUser=null;
		try {
			saasUser = genericInternalServiceUser.getDtoById(userId);
			return saasUser;
		} catch (Exception e) {
			throw new GenericException("echec getSaasUserById",e);
		}
	}

	@Override
	public SaasUser findSaasUserBySpecificRoleAccountId(Long saasRoleAccountId) {
		SaasUser saasUser=null;
		try {
			_SaasUser persitentSaasUser = saasUserDao.getSaasUserBySpecificRoleAccountId(saasRoleAccountId);
			if(persitentSaasUser!=null){
				saasUser = beanConverter.convert(persitentSaasUser, SaasUser.class);
			}
			return saasUser;
		} catch (Exception e) {
			throw new GenericException("echec findSaasUserBySpecificRoleAccountId",e);
		}
	}
	
	

}
