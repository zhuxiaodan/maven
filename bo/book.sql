use defa4485eb44a411387d342eb8c124d65;

CREATE TABLE user (
   id int(3) NOT NULL auto_increment,
   name varchar(20) not null,
   password varchar(50) NOT NULL,
   address varchar(50),
   tel varchar(20),
   email varchar(50) not null,
   PRIMARY KEY (id)
);

create table book (
   id int(6) not null auto_increment,
   title varchar(100) not null,
   author varchar(100) not null,
   publisher varchar(50) not null,
   publish_year year(4) not null,
   total int(3) not null,
   leave_number int(3) not null,
   other text,
   primary key(id)
);

create table lend(
   id int(6) not null auto_increment,
   book_id int(6) not null,
   book_title varchar(100) not null,
   lend_time date not null,
   renew_time date,
   user_id int(3) not null,
   primary key(id, user_id)
);

create table lend_log(
   id int(8) not null auto_increment,
   book_id int(6) not null,
   user_id int(3) not null,
   lend_time date not null,
   return_time date,
   primary key(id)
);
