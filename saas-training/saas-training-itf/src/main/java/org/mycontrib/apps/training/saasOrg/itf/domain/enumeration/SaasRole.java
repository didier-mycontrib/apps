package org.mycontrib.apps.training.saasOrg.itf.domain.enumeration;
/**
 * 
 * @author didier Defrance
 * 
 * ADMIN_OF_SAAS : administrateur du logiciel Saas (ayant tous les droits à tous les niveaux sauf si confidentialité)
 * ADMIN_OF_ORG : administrateur d'une org (ayant tous les droits à tous les niveaux d'une organisation)
 * AUTHOR_OF_ORG : auteur de contenu (peut créer/modifier/et quelquefois supprimer du contenu mais pas l'org)
 * USER_OF_ORG : utilisateur désirant effectuer un qcm (lecture/execution seulement)
 *    + boolean generic (true or false for specific) au niveau de SaasRoleAccount 
 */
public enum SaasRole {
ADMIN_OF_SAAS,ADMIN_OF_ORG,AUTHOR_OF_ORG,USER_OF_ORG
} 


