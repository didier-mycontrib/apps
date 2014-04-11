DROP DATABASE IF EXISTS training_db;
CREATE DATABASE training_db;
USE training_db;

DROP TABLE IF EXISTS QuestionResponseChoice;
DROP TABLE IF EXISTS McqUserSession;
DROP TABLE IF EXISTS McqOfficialSession;

DROP TABLE IF EXISTS ResponseMcq;
DROP TABLE IF EXISTS QuestionMcq;
DROP TABLE IF EXISTS McqSubjectMcq;
DROP TABLE IF EXISTS McqSubject;
DROP TABLE IF EXISTS Mcq;

DROP TABLE IF EXISTS SaasUser;
DROP TABLE IF EXISTS SaasGroup;
DROP TABLE IF EXISTS SaasOrg;
DROP TABLE IF EXISTS SaasRoleAccount;


######################## CREATE  TABLE ########################################

CREATE TABLE SaasRoleAccount(
	idAccount integer auto_increment,
	saasRole VARCHAR(24),
	email VARCHAR(64),
	userName VARCHAR(32),
	password VARCHAR(32),
        generic boolean,
	PRIMARY KEY(idAccount));

CREATE TABLE SaasOrg(
	idOrg integer auto_increment,
	name VARCHAR(64),
	info VARCHAR(64),
	ref_genericUserOfOrgAccount integer,
	ref_genericAdminOfOrgAccount integer,
        ref_genericAuthorOfOrgAccount integer,
	PRIMARY KEY(idOrg));
	
CREATE TABLE SaasGroup(
	idGroup integer auto_increment,
	name VARCHAR(32),
	info VARCHAR(64),
	ref_org integer,
	PRIMARY KEY(idGroup));
	
CREATE TABLE SaasUser(
	userId integer auto_increment,
	firstName VARCHAR(32),
	lastName VARCHAR(32),
	info VARCHAR(64),
	ref_saasAccount integer,
	ref_group integer,
	PRIMARY KEY(userId));	


CREATE TABLE Mcq(
	id integer auto_increment,
	title VARCHAR(64), 
	nbQuestions integer,
	questionRandomSubListSize integer,
	keyWords VARCHAR(64),
	shared boolean,
	ownerOrgId integer,
	PRIMARY KEY(id));	 

CREATE TABLE McqSubject(
    subjectId integer auto_increment,
	title VARCHAR(64),
	shared boolean,
	ownerOrgId integer,
	PRIMARY KEY(subjectId));	 
	
CREATE TABLE McqSubjectMcq(
	ref_subject integer,
	ref_mcq integer,
	PRIMARY KEY(ref_subject,ref_mcq));

CREATE TABLE QuestionMcq(
	idQuestion integer auto_increment,
	text VARCHAR(128),
	exclusiveResponse boolean,
	imageOrVideoFileName VARCHAR(64),
	questionNumber integer,
	ref_mcq integer,
	PRIMARY KEY(idQuestion));	
	
CREATE TABLE ResponseMcq(
	idQuestion integer,
	responseNum VARCHAR(1),
	text VARCHAR(128),
	ok boolean,
	toInput boolean,
	PRIMARY KEY(idQuestion,responseNum));	


CREATE TABLE McqOfficialSession(
	idSession integer auto_increment,
	startDateTime VARCHAR(32),
	duration VARCHAR(32),
	ref_mcq integer,
	ref_saasOrg integer,
	ref_assignedGroup integer,
	PRIMARY KEY(idSession));	
	
CREATE TABLE McqUserSession(
	mcqUserSessionId integer auto_increment,
	nbGoodResponses integer,
	pctGoodResponses VARCHAR(8),
	submitDateTime VARCHAR(32),
	ref_mcq integer,
	ref_user integer,
	ref_officialSession integer,
	PRIMARY KEY(mcqUserSessionId));		
	
CREATE TABLE QuestionResponseChoice(
	mcqUserSessionId integer,
	questionNumber integer,
	choiceString VARCHAR(16),
	PRIMARY KEY(mcqUserSessionId,questionNumber));	
	


#######################   FOREIGN KEY       ####################################

ALTER TABLE SaasOrg ADD CONSTRAINT SaasOrg_avec_genericUserOfOrgAccount_valide 
FOREIGN KEY (ref_genericUserOfOrgAccount) REFERENCES SaasRoleAccount(idAccount);
ALTER TABLE SaasOrg ADD CONSTRAINT SaasOrg_avec_genericAdminOfOrgAccount_valide 
FOREIGN KEY (ref_genericAdminOfOrgAccount) REFERENCES SaasRoleAccount(idAccount);
ALTER TABLE SaasOrg ADD CONSTRAINT SaasOrg_avec_genericAuthorOfOrgAccount_valide 
FOREIGN KEY (ref_genericAuthorOfOrgAccount) REFERENCES SaasRoleAccount(idAccount);

ALTER TABLE SaasGroup ADD CONSTRAINT SaasGroup_avec_org_valide 
FOREIGN KEY (ref_org) REFERENCES SaasOrg(idOrg);

ALTER TABLE SaasUser ADD CONSTRAINT SaasUser_avec_group_valide 
FOREIGN KEY (ref_group) REFERENCES SaasGroup(idGroup);
ALTER TABLE SaasUser ADD CONSTRAINT SaasUser_avec_saasAccount_valide 
FOREIGN KEY (ref_saasAccount) REFERENCES SaasRoleAccount(idAccount);

ALTER TABLE McqSubject ADD CONSTRAINT McqSubject_avec_ownerOrgId_valide 
FOREIGN KEY (ownerOrgId) REFERENCES SaasOrg(idOrg);
ALTER TABLE Mcq ADD CONSTRAINT Mcq_avec_ownerOrgId_valide 
FOREIGN KEY (ownerOrgId) REFERENCES SaasOrg(idOrg);


ALTER TABLE ResponseMcq ADD CONSTRAINT ResponseMcq_avec_idQuestion_valide 
FOREIGN KEY (idQuestion) REFERENCES QuestionMcq(idQuestion);


ALTER TABLE McqSubjectMcq ADD CONSTRAINT McqSubjectMcq_avec_sujet_valide 
FOREIGN KEY (ref_subject) REFERENCES McqSubject(subjectId);
ALTER TABLE McqSubjectMcq ADD CONSTRAINT McqSubjectMcq_avec_qcm_valide 
FOREIGN KEY (ref_mcq) REFERENCES Mcq(id);


ALTER TABLE QuestionMcq ADD CONSTRAINT QuestionMcq_avec_qcm_valide 
FOREIGN KEY (ref_mcq) REFERENCES Mcq(id);


ALTER TABLE McqOfficialSession ADD CONSTRAINT McqOfficialSession_avec_qcm_valide 
FOREIGN KEY (ref_mcq) REFERENCES Mcq(id);
ALTER TABLE McqOfficialSession ADD CONSTRAINT McqOfficialSession_avec_saas_org_valide 
FOREIGN KEY (ref_saasOrg) REFERENCES SaasOrg(idOrg);
ALTER TABLE McqOfficialSession ADD CONSTRAINT McqOfficialSession_avec_saasGroup_valide 
FOREIGN KEY (ref_assignedGroup) REFERENCES SaasGroup(idGroup);

ALTER TABLE McqUserSession ADD CONSTRAINT McqUserSession_avec_qcm_valide 
FOREIGN KEY (ref_mcq) REFERENCES Mcq(id);
ALTER TABLE McqUserSession ADD CONSTRAINT McqUserSession_avec_user_valide 
FOREIGN KEY (ref_user) REFERENCES SaasUser(userId);
ALTER TABLE McqUserSession ADD CONSTRAINT McqUserSession_avec_officialSession_valide 
FOREIGN KEY (ref_officialSession) REFERENCES McqOfficialSession(idSession);

ALTER TABLE QuestionResponseChoice ADD CONSTRAINT QuestionResponseChoice_avec_mcqUserSession_valide 
FOREIGN KEY (mcqUserSessionId) REFERENCES McqUserSession(mcqUserSessionId);

##############
show tables;

##############"
GRANT SELECT,INSERT,UPDATE,DELETE
ON training_db.*
TO trainingdbuser@'%'
IDENTIFIED BY 'trainingdbpwd';
FLUSH PRIVILEGES;
