alter session set container=PDBORCL

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
