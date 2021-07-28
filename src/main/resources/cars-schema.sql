drop table if exists cars CASCADE;
create table cars 

(

	id integer PRIMARY KEY AUTO_INCREMENT, 
	age integer not null, 
	brand varchar(255), 
	model varchar(255)

);