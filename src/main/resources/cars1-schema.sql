drop table if exists cars1 CASCADE;
create table cars1 

(

	id integer PRIMARY KEY AUTO_INCREMENT, 
	age integer not null, 
	brand varchar(255), 
	model varchar(255)

);