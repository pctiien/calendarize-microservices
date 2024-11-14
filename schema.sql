use master
go
create database appuser_ms
go
use appuser_ms;
create table users(
	id bigint identity primary key,
	name nvarchar(100),
	email varchar(50) unique,
	pwd varchar(200),
	image_url varchar(200),
	auth_provider varchar(50)
)
create table roles(
	id bigint identity primary key,
	role_name nvarchar(255)
)
create table user_role (
	user_id bigint ,
	role_id bigint,
	primary key (user_id,role_id),
	constraint UserRole_User_FK foreign key (user_id) references users(id),
	constraint UserRole_Role_FK foreign key (role_id) references roles(id),
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
	status nvarchar(50)
)

create table project_task(
	id bigint identity primary key,
	name nvarchar(100),
	description nvarchar(200),
	start_date datetime,
	end_date datetime,
	status nvarchar(50),
	project_id bigint,
	constraint ProjectTask_FK foreign key(project_id) references project(id)
)
create table project_user(
	id bigint identity primary key,
	user_id bigint,
	project_id bigint ,
	constraint ProjectUser_Project_FK foreign key(project_id) references project(id)
)
create table project_role(
	id bigint identity primary key,
	name nvarchar(255) unique
)
insert into project_role(name) values('PROJECT_OWNER'),('PROJECT_CONTRIBUTOR'),('PROJECT_MEMBER'),('PROJECT_VIEWER')
create table project_permission(
	id bigint identity primary key,
	name nvarchar(255) unique
)
insert into project_permission(name) 
values('PROJECT:VIEW'),('PROJECT:EDIT'),('PROJECT:DELETE'),('PROJECT:ADD_MEMBER'),('PROJECT:REMOVE_MEMBER'),
('PROJECT:ASSIGN_ROLE'),('TASK:CREATE'),('TASK:EDIT'),('TASK:DELETE'),('TASK:VIEW')
	
create table projectrole_permission(
	projectrole_id bigint,
	projectpermission_id bigint,
	primary key (projectrole_id,projectpermission_id),
	constraint ProjectRolePermission_ProjectRole_FK foreign key(projectrole_id) references project_role(id),
	constraint ProjectRolePermission_ProjectPermission_FK foreign key(projectpermission_id) references project_permission(id)
)
insert into projectrole_permission
values(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),
(2,1),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),
(3,1),(3,10),(3,8),
(4,1),(4,10)
create table projectuser_role(
	projectuser_id bigint,
	projectrole_id bigint,
	primary key(projectuser_id,projectrole_id),
	constraint ProjectUserRole_ProjectUser_FK foreign key(projectuser_id) references project_user(id),
	constraint ProjectUserRole_ProjectRole_FK foreign key(projectrole_id) references project_role(id)
)

create table projectuser_task(
	projectuser_id bigint,
	projecttask_id bigint,
	primary key(projectuser_id,projecttask_id),
	constraint ProjectUserTask_ProjectUser_FK foreign key (projectuser_id) references project_user(id),
	constraint ProjectUserTask_ProjectTask_FK foreign key (projecttask_id) references project_task(id)
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

use master;
drop database appuser_ms
drop database project_ms
drop database lifetask_ms
