alter session set container=PDBORCL;

drop user weblogic_examples_domain cascade;
create user weblogic_examples_domain identified by weblogic_examples_domain;
grant connect to weblogic_examples_domain;
grant dba to weblogic_examples_domain;

drop user ops_user cascade;
create user ops_user identified by ops_user;
grant connect to ops_user;
grant dba to ops_user;

drop user ops cascade;
create user ops identified by ops;
grant connect to ops;
grant dba to ops;

drop user saf_target_domain cascade;
create user saf_target_domain identified by saf_target_domain;
grant connect to saf_target_domain;
grant dba to saf_target_domain;

CREATE TABLE weblogic_examples_domain.ACTIVE (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);

commit;

CREATE TABLE ops_user.ACTIVE (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);

CREATE TABLE ops.ACTIVE (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);

CREATE TABLE ops.saf_target_domain (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);

commit;

-- execute dbms_service.create_service('PDBORCL','PDBORCL');
-- execute dbms_service.start_service('PDBORCL');

exit;