alter session set container=PDBORCL;

create user weblogic_examples_domain identified by weblogic_examples_domain;
grant connect to weblogic_examples_domain;
grant dba to weblogic_examples_domain;

create user ops_user identified by ops_user;
grant connect to ops_user;
grant dba to ops_user;

create user ops identified by ops;
grant connect to ops;
grant dba to ops;

create user saf_target_domain identified by saf_target_domain;
grant connect to saf_target_domain;
grant dba to saf_target_domain;

connect weblogic_examples_domain / weblogic_examples_domain;

CREATE TABLE ACTIVE (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);

commit;


connect ops_user / ops_user;

CREATE TABLE ACTIVE (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);

commit;

execute dbms_service.create_service('PDBORCL','PDBORCL');
execute dbms_service.start_service('PDBORCL');