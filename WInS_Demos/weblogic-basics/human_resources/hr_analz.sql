Rem
Rem $Header: hr_analz.sql 12-oct-2002.10:24:59 ahunold Exp $
Rem
Rem hr_analz.sql
Rem
Rem Copyright (c) 2001, 2002, Oracle Corporation.  All rights reserved.  
Rem
Rem    NAME
Rem      hr_analz.sql - Gathering statistics for HR schema
Rem
Rem    DESCRIPTION
Rem      Staistics are used by the cost based optimizer to
Rem      choose the best physical access strategy
Rem
Rem    NOTES
Rem      Results can be viewed in columns of DBA_TABLES, 
Rem      DBA_TAB_COLUMNS and such
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ahunold     10/12/02 - gather_schema_stats instead of gather_table_stats
Rem    hyeh        08/29/02 - hyeh_mv_comschema_to_rdbms
Rem    ahunold     03/12/01 - cleanup b3
Rem    ahunold     03/07/01 - Merged ahunold_hr_analz
Rem    ahunold     03/07/01 - Created
Rem

SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100
SET ECHO OFF

EXECUTE dbms_stats.gather_schema_stats( -
        'c##hr'                            ,       -
        granularity => 'ALL'            ,       -
        cascade => TRUE                 ,       -
        block_sample => TRUE            );


