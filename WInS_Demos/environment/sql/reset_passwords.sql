ALTER PROFILE DEFAULT LIMIT
FAILED_LOGIN_ATTEMPTS UNLIMITED
PASSWORD_LIFE_TIME UNLIMITED;

alter user sys identified by welcome1;
alter user system identified by welcome1;
alter user ops_user identified by ops_user;
alter user ops identified by ops;]

alter user weblogic_cluster_domain identified by weblogic_cluster_domain;
alter user saf_target_domain identified by saf_target_domain;

exit;
