use master
go
create database appuser_ms
go
use appuser_ms;
create table app_user(
	id bigint identity primary key,
	name nvarchar(100),
	email varchar(50),
	pwd varchar(200),
)
use master
go
create database project_ms
go
use project_ms
create table project(
	id bigint identity primary key,
	name nvarchar(100),
	description nvarchar(200),
	start_date datetime,
	end_date datetime,
	status nvarchar(50),
	host_id bigint

)
use master
go
create database lifetask_ms
go
use lifetask_ms 
create table life_task(
	id bigint identity primary key,
	name nvarchar(100),
	description nvarchar(200),
	start_date datetime,
	end_date datetime,
	status nvarchar(50),
	user_id bigint
)
use master
go
create database projecttask_ms
go
use projecttask_ms
create table project_task(
	id bigint identity primary key,
	name nvarchar(100),
	description nvarchar(200),
	start_date datetime,
	end_date datetime,
	status nvarchar(50),
	project_id bigint
)
use master;
drop database appuser_ms
drop database project_ms
drop database lifetask_ms