create user weblogic_examples_domain identified by weblogic_examples_domain;

create user ops_user identified by ops_user;
create user ops identified by ops;

create user weblogic_examples_domain identified by weblogic_examples_domain;
create user saf_target_domain identified by saf_target_domain;

DROP TABLE ACTIVE;

CREATE TABLE ACTIVE (
  SERVER VARCHAR2(150) NOT NULL,
  INSTANCE VARCHAR2(100) NOT NULL,
  DOMAINNAME VARCHAR2(50) NOT NULL,
  CLUSTERNAME VARCHAR2(50) NOT NULL,
  TIMEOUT DATE,
  PRIMARY KEY (SERVER, DOMAINNAME, CLUSTERNAME)
);