rem
rem Header: hr_main.sql 09-jan-01
rem
rem Copyright (c) 2001, 2010, Oracle and/or its affiliates. 
rem All rights reserved. 
rem
rem Owner  : ahunold
rem
rem NAME
rem   hr_main.sql - Main script for HR schema
rem
rem DESCRIPTON
rem   HR (Human Resources) is the smallest and most simple one 
rem   of the Sample Schemas
rem   
rem NOTES
rem   Run as SYS or SYSTEM
rem
rem MODIFIED   (MM/DD/YY)
rem   celsbern  06/17/10 - fixing bug 9733839
rem   pthornto  07/16/04 - obsolete 'connect' role 
rem   hyeh      08/29/02 - hyeh_mv_comschema_to_rdbms
rem   ahunold   08/28/01 - roles
rem   ahunold   07/13/01 - NLS Territory
rem   ahunold   04/13/01 - parameter 5, notes, spool
rem   ahunold   03/29/01 - spool
rem   ahunold   03/12/01 - prompts
rem   ahunold   03/07/01 - hr_analz.sql
rem   ahunold   03/03/01 - HR simplification, REGIONS table
rem   ngreenbe  06/01/00 - created

SET ECHO OFF
SET VERIFY OFF

PROMPT 
PROMPT specify password for HR as parameter 1:
DEFINE pass     = &1
PROMPT 
PROMPT specify default tablespeace for HR as parameter 2:
DEFINE tbs      = &2
PROMPT 
PROMPT specify temporary tablespace for HR as parameter 3:
DEFINE ttbs     = &3
PROMPT 
PROMPT specify password for SYS as parameter 4:
DEFINE pass_sys = &4
PROMPT 
PROMPT specify log path as parameter 5:
DEFINE log_path = &5
PROMPT

-- The first dot in the spool command below is 
-- the SQL*Plus concatenation character

DEFINE spool_file = &log_path.hr_main.log
SPOOL &spool_file

REM =======================================================
REM cleanup section
REM =======================================================

DROP USER c##hr CASCADE;

REM =======================================================
REM create user
REM three separate commands, so the create user command 
REM will succeed regardless of the existence of the 
REM DEMO and TEMP tablespaces 
REM =======================================================

CREATE USER c##hr IDENTIFIED BY &pass;

ALTER USER c##hr DEFAULT TABLESPACE &tbs
              QUOTA UNLIMITED ON &tbs;

ALTER USER c##hr TEMPORARY TABLESPACE &ttbs;

GRANT CREATE SESSION, CREATE VIEW, ALTER SESSION, CREATE SEQUENCE TO c##hr;
GRANT CREATE SYNONYM, CREATE DATABASE LINK, RESOURCE TO c##hr;

REM =======================================================
REM grants from sys schema
REM =======================================================

CONNECT sys/&pass_sys AS SYSDBA;
GRANT execute ON sys.dbms_stats TO c##hr;

REM =======================================================
REM create hr schema objects
REM =======================================================

CONNECT c##hr/&pass
ALTER SESSION SET NLS_LANGUAGE=American;
ALTER SESSION SET NLS_TERRITORY=America;

--
-- create tables, sequences and constraint
--

@?/demo/schema/human_resources/hr_cre

-- 
-- populate tables
--

@?/demo/schema/human_resources/hr_popul

--
-- create indexes
--

@?/demo/schema/human_resources/hr_idx

--
-- create procedural objects
--

@?/demo/schema/human_resources/hr_code

--
-- add comments to tables and columns
--

@?/demo/schema/human_resources/hr_comnt

--
-- gather schema statistics
--

@?/demo/schema/human_resources/hr_analz

spool off
