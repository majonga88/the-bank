--This script is used for unit test cases, DO NOT CHANGE!

DROP TABLE IF EXISTS user;

CREATE TABLE user (id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 username VARCHAR(30) NOT NULL,
 firstName VARCHAR(30) NOT NULL,
 lastName VARCHAR(30) NOT NULL,
 email VARCHAR(50) NOT NULL);

CREATE UNIQUE INDEX idx_ue on User(firstName,lastName);

INSERT INTO User (username, firstName, lastName, email) VALUES ('mfinchley','Michael','Finchley','m.finchley@mail.com');
INSERT INTO User (username, firstName, lastName, email) VALUES ('jdeer','John','Deer','j.deer@mail.com');
INSERT INTO User (username, firstName, lastName, email) VALUES ('jbond','James','Bond','j.bond@mail.com');

DROP TABLE IF EXISTS account;

CREATE TABLE account (id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
username VARCHAR(30),
name VARCHAR(30),
balance DECIMAL(19,4),
currency VARCHAR(30)
);

CREATE UNIQUE INDEX idx_account on account(id);

INSERT INTO Account (username, name, balance, currency) VALUES ('mfinchley','Bank Account',100.0000,'EUR');
INSERT INTO Account (username, name, balance, currency) VALUES ('mfinchley','Bank Account',200.0000,'EUR');
INSERT INTO Account (username, name, balance, currency) VALUES ('jdeer','Bank Account',100.0000,'EUR');
INSERT INTO Account (username, name, balance, currency) VALUES ('jbond','Bank Account',400.0000,'EUR');
INSERT INTO Account (username, name, balance, currency) VALUES ('jbond','Bank Account',500.0000,'EUR');

COMMIT;
