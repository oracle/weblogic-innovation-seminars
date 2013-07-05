alter session set container=PDBORCL;
alter pluggable database PDBORCL open;
execute dbms_service.start_service('PDBORCL');

exit;
