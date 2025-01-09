create table customers (
    customer_id int primary key auto_increment,
    first_name varchar(255) not null ,
    last_name varchar(255),
    age int not null ,
    email varchar(255) unique not null,
    phone varchar(255) not null
)