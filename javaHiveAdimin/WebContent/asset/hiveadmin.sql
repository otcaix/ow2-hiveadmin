create table if not exists testuser
	(id int auto_increment primary key,
	name varchar(20));

create table if not exists user
	(userid int auto_increment primary key,
	username varchar(30) not null);

create table if not exists  op_record
	(op_record_id int auto_increment primary key,
	op_desc text not null,
	op_sql text not null,
	op_res boolean,
	op_ts timestamp);

create table if not exists user_history
	(userid int not null,
	op_record_id int not null,
	constraint cst1 primary key(userid,op_record_id)
	);
