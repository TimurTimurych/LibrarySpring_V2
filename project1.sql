CREATE TABLE Person(
                       id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
                       name varchar(100) NOT NULL UNIQUE ,
                       year int CHECK (year > 1900)
    );

CREATE TABLE Book(
                     id int NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
                     title varchar(100) NOT NULL UNIQUE ,
                     author varchar(100) NOT NULL,
                     age int CHECK ( age>1700 ),
                     taken_date TIMESTAMP,
                     user_id int REFERENCES Person(id)
);