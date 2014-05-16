USE training_db;

#########################  INSERT INTO   #####################################

INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(1,'ADMIN_OF_ORG','admin@org1.com','adminOf_org1','adm1',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(2,'ADMIN_OF_ORG','admin@org2.com','adminOf_org2','adm2',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(3,'USER_OF_ORG','user@org1.com','userOf_org1','u1',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(4,'USER_OF_ORG','user@org2.com','userOf_org2','u2',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(5,'AUTHOR_OF_ORG','author@org1.com','authorOf_org1','a1',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(6,'AUTHOR_OF_ORG','author@org2.com','authorOf_org2','a2',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) VALUES(7,'ADMIN_OF_SAAS','admin@saas.com','adminOfSaas','saas',true);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) 
VALUES(8,'USER_OF_ORG','alain.therieur@org1.com','alain-therieur','pwd',false);
INSERT INTO SaasRoleAccount(idAccount,saasRole,email,userName,password,generic) 
VALUES(9,'USER_OF_ORG','alex.therieur@org1.com','alex-therieur','pwd',false);

INSERT INTO SaasOrg(idOrg,name,info,ref_genericUserOfOrgAccount,ref_genericAdminOfOrgAccount,ref_genericAuthorOfOrgAccount) VALUES(1,'org1','organisation 1',3,1,5);
INSERT INTO SaasOrg(idOrg,name,info,ref_genericUserOfOrgAccount,ref_genericAdminOfOrgAccount,ref_genericAuthorOfOrgAccount) VALUES(2,'org2','organisation 2',4,2,6);

INSERT INTO SaasGroup(idGroup,name,info,ref_org) VALUES(1,'group1_of_org1',null,1);
INSERT INTO SaasGroup(idGroup,name,info,ref_org) VALUES(2,'group2_of_org1',null,1);
INSERT INTO SaasGroup(idGroup,name,info,ref_org) VALUES(3,'group1_of_org2',null,2);
INSERT INTO SaasGroup(idGroup,name,info,ref_org) VALUES(4,'group2_of_org2',null,2);

INSERT INTO SaasUser(userId,firstName,lastName,info,ref_saasAccount,ref_group) 
               VALUES(1,'alain','therieur',null,8,1);
INSERT INTO SaasUser(userId,firstName,lastName,info,ref_saasAccount,ref_group) 
               VALUES(2,'alex','therieur',null,9,1);               


INSERT INTO McqSubject (subjectId,title,shared,ownerOrgId)  VALUES (1,'sujet_1',true,1);
INSERT INTO McqSubject (subjectId,title,shared,ownerOrgId)  VALUES (2,'sujet_2',false,1);
INSERT INTO McqSubject (subjectId,title,shared,ownerOrgId)  VALUES (3,'sujet_3',true,2);

INSERT INTO Mcq (id,title,nbQuestions,keyWords,shared,ownerOrgId) VALUES (1,'qcm1',2,'java;jee',true,1);
INSERT INTO Mcq (id,title,nbQuestions,keyWords,shared,ownerOrgId) VALUES (2,'qcm2',1,'xml',true,1);
INSERT INTO Mcq (id,title,nbQuestions,keyWords,shared,ownerOrgId) VALUES (3,'qcm3',0,'aaa;bbb',false,2);
INSERT INTO McqSubjectMcq (ref_subject,ref_mcq) VALUES (1,1);
INSERT INTO McqSubjectMcq (ref_subject,ref_mcq) VALUES (1,2);
INSERT INTO McqSubjectMcq (ref_subject,ref_mcq) VALUES (3,3);

INSERT INTO QuestionMcq (idQuestion,text,exclusiveResponse,imageOrVideoFileName,questionNumber,ref_mcq) 
     VALUES (1,'java est ...',true,null,1,1);              
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (1,'A','un langage purement interprete',false);
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (1,'B','un langage purement compile',false);
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (1,'C','un langage semi compile et semi interpete',true);
INSERT INTO QuestionMcq (idQuestion,text,exclusiveResponse,imageOrVideoFileName,questionNumber,ref_mcq) 
     VALUES (2,'tomcat est ...',true,null,2,1);              
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (2,'A','un gros chat',false);
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (2,'B','un conteneur web',true);
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (2,'C','un mot clef du langage java',false);
     
INSERT INTO QuestionMcq (idQuestion,text,exclusiveResponse,imageOrVideoFileName,questionNumber,ref_mcq) 
     VALUES (3,'xml est ...',true,null,1,2);              
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (3,'A','un meta langage pour document ',true);
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (3,'B','un SGBDR',false);
INSERT INTO ResponseMcq (idQuestion,responseNum,text,ok)  
     VALUES (3,'C','un protocole réseau',false);     



INSERT INTO McqOfficialSession(idSession,startDateTime,duration,ref_mcq,ref_saasOrg,ref_assignedGroup) 
    VALUES(1,null,'20',1,1,1);
INSERT INTO McqUserSession(mcqUserSessionId,nbGoodResponses,pctGoodResponses,submitDateTime,ref_mcq,ref_user,ref_officialSession) 
       VALUES(1,3,'66',null,1,1,1);
INSERT INTO QuestionResponseChoice(mcqUserSessionId,questionNumber,choiceString) VALUES(1,1,'B');
INSERT INTO QuestionResponseChoice(mcqUserSessionId,questionNumber,choiceString) VALUES(1,2,'C');
INSERT INTO QuestionResponseChoice(mcqUserSessionId,questionNumber,choiceString) VALUES(1,3,'B');
INSERT INTO QuestionResponseChoice(mcqUserSessionId,questionNumber,choiceString) VALUES(1,4,'A');
INSERT INTO QuestionResponseChoice(mcqUserSessionId,questionNumber,choiceString) VALUES(1,5,'A');

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


