create table file
(
    file_id bigint not null auto_increment,
    file_name varchar(255),
    store_file_name varchar(255),
    file_size bigint,
    owner varchar(255),
    upload_time datetime,
    primary key (file_id)
)

create table member
(
    member_id bigint not null auto_increment,
    id varchar(255),
    password varchar(255),
    capacity bigint default 10737418240,
    pending tinyint(1),

    primary key (member_id)
)