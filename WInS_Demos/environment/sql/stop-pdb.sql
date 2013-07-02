alter session set container=PDBORCL;
execute dbms_service.stop_service('orcl_pdb');
alter pluggable database PDBORCL close;
