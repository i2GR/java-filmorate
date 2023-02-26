CREATE TABLE users (
  id BIGINT,
  email varchar(64) NOT NULL,
  login varchar(32) NOT NULL,
  display_name varchar(32) ,
  birthday date,
  CONSTRAINT usr_id_pk PRIMARY KEY (id),
  CONSTRAINT usr_email_unq UNIQUE (email),
  CONSTRAINT usr_login_unq UNIQUE (login)
);