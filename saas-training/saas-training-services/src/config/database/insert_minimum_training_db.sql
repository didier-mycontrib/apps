USE training_db;

#########################  INSERT INTO   #####################################
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(1,'ADMIN_OF_ORG','admin@afcepf.fr','adminOf_afcepf','pwdAdminOfAfcepf',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(3,'USER_OF_ORG','user@afcepf.fr','userOf_afcepf','afcepf',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(5,'AUTHOR_OF_ORG','author@afcepf.fr','authorOf_afcepf','AfcepfAuthor',true);

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(7,'ADMIN_OF_SAAS','admin@saas.com','adminOfSaas','pwdAdminOfSaas',true);

INSERT INTO SaasOrg(idOrg,name,info,ref_genericUserOfOrgAccount,ref_genericAdminOfOrgAccount,ref_genericAuthorOfOrgAccount) VALUES(1,'afcepf','afcepf',3,1,5);


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


