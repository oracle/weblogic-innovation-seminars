Rem
Rem $Header: hr_drop.sql 29-aug-2002.11:44:07 hyeh Exp $
Rem
Rem hr_drop.sql
Rem
Rem Copyright (c) 2001, 2002, Oracle Corporation.  All rights reserved.  
Rem
Rem    NAME
Rem      hr_drop.sql - Drop objects from HR schema
Rem
Rem    DESCRIPTION
Rem
Rem
Rem    NOTES
Rem
Rem    CREATED by Nancy Greenberg - 06/01/00
Rem    MODIFIED   (MM/DD/YY)
Rem    hyeh        08/29/02 - hyeh_mv_comschema_to_rdbms
Rem    ahunold     02/20/01 - New header, non-table objects
Rem    vpatabal    03/02/01 - DROP TABLE region 

SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100
SET ECHO OFF

CONNECT c##hr/&password_HR

DROP PROCEDURE add_job_history;
DROP PROCEDURE secure_dml;

DROP VIEW emp_details_view;

DROP SEQUENCE departments_seq;
DROP SEQUENCE employees_seq;
DROP SEQUENCE locations_seq;

DROP TABLE regions     CASCADE CONSTRAINTS;
DROP TABLE departments CASCADE CONSTRAINTS;
DROP TABLE locations   CASCADE CONSTRAINTS;
DROP TABLE jobs        CASCADE CONSTRAINTS;
DROP TABLE job_history CASCADE CONSTRAINTS;
DROP TABLE employees   CASCADE CONSTRAINTS;
DROP TABLE countries   CASCADE CONSTRAINTS;  

COMMIT;

