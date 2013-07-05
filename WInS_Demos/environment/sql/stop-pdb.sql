alter session set container=PDBORCL;
execute dbms_service.stop_service('PDBORCL');
alter pluggable database PDBORCL close;

exit;
