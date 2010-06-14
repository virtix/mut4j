
// table for tests
drop table logtest;

create table logtest (
	id decimal NOT NULL, 
	prio varchar(15), 
	iprio decimal, 
	cat varchar(255), 
	thread varchar(30), 
	msg varchar(255), 
	layout_msg varchar(255), 
	throwable varchar(4000),
	ndc varchar(255), 
	mdc varchar(255), 
	mdc2 varchar(255), 
	info varchar(255), 
	addon varchar(255), 
	the_date date,
	the_time time,
	the_timestamp timestamp,
	created_by varchar(50) );

// MySQL Syntax
create table logtest (
	id decimal NOT NULL, 
	prio varchar(15), 
	iprio decimal, 
	cat varchar(255), 
	thread varchar(30), 
	msg varchar(255), 
	layout_msg varchar(255), 
	throwable varchar(2000),
	ndc varchar(255), 
	mdc varchar(255), 
	mdc2 varchar(255), 
	info varchar(255), 
	addon varchar(255), 
	the_date timestamp,
	the_time timestamp,
	the_timestamp timestamp,
	created_by varchar(50) );


//alter table logtest add CONSTRAINT logtest_pk PRIMARY KEY (id);

//delete from logtest;
//select * from logtest;

// table for SqlHandler

drop table log_log4j;

create table log_log4j (
	LogDate date, 
	Logger varchar(4000), 
	Priority varchar(4000), 
	Loc_ClassName varchar(4000), 
	Loc_MethodName varchar(4000), 
	Loc_FileName varchar(4000), 
	Loc_LineNumber varchar(4000), 
	Msg varchar(4000), 
	Throwable varchar(4000));

//delete from log_log4j; commit;

// test stored procedure (firebird syntax)
D:\Programme\Firebird\Firebird1.5\bin\isql 
connect danko user danko password danko;

DROP PROCEDURE PROC_INSERT_LOGTEST;
SET TERM !! ;
CREATE PROCEDURE PROC_INSERT_LOGTEST (
	id decimal, 
	prio varchar(15), 
	iprio decimal, 
	cat varchar(255), 
	thread varchar(30), 
	msg varchar(255), 
	layout_msg varchar(255), 
	throwable varchar(4000),
	ndc varchar(255), 
	mdc varchar(255), 
	mdc2 varchar(255), 
	info varchar(255), 
	addon varchar(255), 
	the_date date,
	the_time time,
	the_timestamp timestamp,
	created_by varchar(50)
) 
AS 
BEGIN 
INSERT INTO LOGTEST(ID, prio, iprio, cat, thread, msg, layout_msg, throwable, ndc, mdc, mdc2, info, addon, the_date, the_time, the_timestamp, created_by) 
VALUES (:id, :prio, :iprio, :cat, :thread, :msg, :layout_msg, :throwable, :ndc, :mdc, :mdc2, :info, :addon, :the_date, :the_time, :the_timestamp, :created_by); 
END !!
SET TERM ; !!

// Oracle 9 syntax
drop table logtest;

create table logtest (
	id decimal NOT NULL, 
	prio varchar(15), 
	iprio decimal, 
	cat varchar(255), 
	thread varchar(30), 
	msg varchar(255), 
	layout_msg varchar(255), 
	throwable varchar(4000),
	ndc varchar(255), 
	mdc varchar(255), 
	mdc2 varchar(255), 
	info varchar(255), 
	addon varchar(255), 
	the_timestamp timestamp,
	created_by varchar(50) );

DROP PROCEDURE PROC_INSERT_LOGTEST;

// SQL Navigator Syntax
PROCEDURE PROC_INSERT_LOGTEST (
	id decimal, 
	prio varchar,
	iprio decimal, 
	cat varchar,
	thread varchar,
	msg varchar,
	layout_msg varchar,
	throwable varchar,
	ndc varchar,
	mdc varchar,
	mdc2 varchar,
	info varchar,
	addon varchar,
	the_timestamp timestamp,
	created_by varchar
) 
AS 
BEGIN 
INSERT INTO LOGTEST(ID, prio, iprio, cat, thread, msg, layout_msg, throwable, ndc, mdc, mdc2, info, addon, the_timestamp, created_by) 
VALUES (id, prio, iprio, cat, thread, msg, layout_msg, throwable, ndc, mdc, mdc2, info, addon, the_timestamp, created_by);
END
\
