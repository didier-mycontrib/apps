USE training_db;

#########################  INSERT INTO   #####################################

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) 
VALUES(1,'USER_OF_ORG','anonymous@public','anonymous','',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(2,'ADMIN_OF_SAAS','didier@d-defrance.fr','adminOfSaas','SaasAdmin',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(3,'USER_OF_ORG','user@d-defrance.fr','userOf_mycontrib','mycontrib',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(4,'AUTHOR_OF_ORG','author@d-defrance.fr','authorOf_mycontrib','MyContribAuthor',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(5,'ADMIN_OF_ORG','admin@d-defrance.fr','adminOf_mycontrib','MyContribAdmin',true);

INSERT INTO SaasOrg(idOrg,name,info,ref_genericUserOfOrgAccount,ref_genericAdminOfOrgAccount,ref_genericAuthorOfOrgAccount) VALUES(1,'public','public domain',1,null,null);

INSERT INTO SaasOrg(idOrg,name,info,ref_genericUserOfOrgAccount,ref_genericAdminOfOrgAccount,ref_genericAuthorOfOrgAccount) VALUES(2,'mycontrib','mycontrib.fr',3,5,4);


###################### VERIFICATIONS ###########################################
show tables;
SELECT * FROM SaasRoleAccount;
SELECT * FROM SaasOrg;
SELECT * FROM SaasGroup;
SELECT * FROM SaasUser;

SELECT * FROM McqSubject;
SELECT * FROM Mcq;
SELECT * FROM McqSubjectMcq;
SELECT * FROM ResponseMcq;
SELECT * FROM QuestionMcq;

SELECT * FROM McqOfficialSession;
SELECT * FROM McqUserSession;
SELECT * FROM QuestionResponseChoice;


