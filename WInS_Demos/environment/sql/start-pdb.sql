alter session set container=pdborcl;
alter pluggable database pdborcl open;
execute dbms_service.start_service('pdborcl');
exit;
