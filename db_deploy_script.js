--creating a role
CREATE ROLE termbase_admin WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD '123';
--creating a database
CREATE DATABASE termbase
    WITH 
    OWNER = termbase_admin
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
--creating a cast
CREATE CAST (character varying AS jsonb) WITH INOUT AS ASSIGNMENT;

	
--creating a sequence for 'columns' table
CREATE SEQUENCE public.column_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
--assigning sequence to termbase_admin
ALTER TABLE public.column_seq OWNER TO termbase_admin;
--creating 'columns' table
CREATE TABLE public.columns (
    id numeric NOT NULL,
    name character varying(256) NOT NULL,
    html_id character varying(256),
    sortable boolean NOT NULL,
    filterable boolean NOT NULL,
    element_type character varying(256) NOT NULL,
    mandatory boolean NOT NULL,
    "position" numeric NOT NULL
);
--assigning 'columns' table to termbase_admin
ALTER TABLE public.columns OWNER TO termbase_admin;
--adding restiriction
ALTER TABLE ONLY public.columns ADD CONSTRAINT columns_pkey PRIMARY KEY (id);


--creating a sequence for 'dropdown_options' table
CREATE SEQUENCE public.dropdown_option_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
--assigning sequence to termbase_admin
ALTER TABLE public.dropdown_option_seq OWNER TO termbase_admin;
--creating 'dropdown_options' table
CREATE TABLE public.dropdown_options (
    column_id numeric,
    "position" numeric,
    name character varying(512),
    id numeric NOT NULL
);
--assigning 'dropdown_options' table to termbase_admin
ALTER TABLE public.dropdown_options OWNER TO termbase_admin;
--adding a restriction
ALTER TABLE ONLY public.dropdown_options ADD CONSTRAINT dropdown_options_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.dropdown_options ADD CONSTRAINT dropdown_options_column_id_fkey FOREIGN KEY (column_id) REFERENCES public.columns(id) NOT VALID;


--creating 'privileges' table
CREATE TABLE public.privileges (
    id numeric NOT NULL,
    name character varying(128) NOT NULL,
    date_created timestamp without time zone,
    date_last_modified timestamp without time zone
);
--assigning 'privileges' table to termbase_admin
ALTER TABLE public.privileges OWNER TO termbase_admin;
--adding a restriction
ALTER TABLE ONLY public.privileges ADD CONSTRAINT name UNIQUE (name);
ALTER TABLE ONLY public.privileges ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
--insert privileges
INSERT INTO public.privileges(id, name, date_created, date_last_modified) VALUES (1, 'Allow manage users', '2021-11-19 00:00:00', '2021-11-19 00:00:00');
INSERT INTO public.privileges(id, name, date_created, date_last_modified) VALUES (2, 'Allow manage terms', '2021-11-19 00:00:00', '2021-11-19 00:00:00');
INSERT INTO public.privileges(id, name, date_created, date_last_modified) VALUES (3, 'Allow configure term grid', '2021-11-19 00:00:00', '2021-11-19 00:00:00');
INSERT INTO public.privileges(id, name, date_created, date_last_modified) VALUES (4, 'Allow manage suggestions', '2021-11-19 00:00:00', '2021-11-19 00:00:00');


--creating a sequence for 'suggestions' table
CREATE SEQUENCE public.suggestion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
--assigning sequence to termbase_admin
ALTER TABLE public.suggestion_seq OWNER TO termbase_admin;
--creating 'suggestions' table
CREATE TABLE public.suggestions (
    id numeric NOT NULL,
    properties jsonb NOT NULL,
    authored_by character varying(1024) NOT NULL
);
--assigning 'suggestions' table to termbase_admin
ALTER TABLE public.suggestions OWNER TO termbase_admin;
--add a restriction
ALTER TABLE ONLY public.suggestions ADD CONSTRAINT suggestions_pkey PRIMARY KEY (id);


--creating a sequence for 'terms' table
CREATE SEQUENCE public.term_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
--assigning sequence to termbase_admin
ALTER TABLE public.term_seq OWNER TO termbase_admin;
--creating 'terms' table
CREATE TABLE public.terms (
    id numeric NOT NULL,
    properties jsonb,
    authored_by character varying(1024) NOT NULL
);
--assigning 'terms' table to termbase_admin
ALTER TABLE public.terms OWNER TO termbase_admin;
--add a restriction
ALTER TABLE ONLY public.terms ADD CONSTRAINT terms_pkey PRIMARY KEY (id);


--creating a sequence for 'users' table
CREATE SEQUENCE public.user_seq
    START WITH 165
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
--assigning sequence to termbase_admin
ALTER TABLE public.user_seq OWNER TO termbase_admin;
--creating 'users' table
CREATE TABLE public.users (
    id numeric NOT NULL,
    email character varying(128) NOT NULL,
    password character varying(128) NOT NULL,
    status character varying(128),
    date_created timestamp without time zone,
    date_last_modified timestamp without time zone,
    first_name character varying(128),
    last_name character varying(128)
);
--assigning 'users' table to termbase_admin
ALTER TABLE public.users OWNER TO termbase_admin;
--adding a restriction
ALTER TABLE ONLY public.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_email_key UNIQUE (email);
--insert a new user
INSERT INTO public.users(id, email, password, status, date_created, date_last_modified, first_name, last_name) 
VALUES (nextval('public.user_seq'), 'fazz@mail.ru', '123', 'ACTIVE', '2022-04-10 18:05:42.197', '2022-04-10 18:05:42.197', 'Sergey', 'Selyuto');


--creating 'users_to_privileges' table
CREATE TABLE public.users_to_privileges (
    user_id numeric NOT NULL,
    privilege_id numeric NOT NULL
);
--assigning 'users_to_privileges' table to termbase_admin
ALTER TABLE public.users_to_privileges OWNER TO termbase_admin;
--adding a constraint
ALTER TABLE ONLY public.users_to_privileges ADD CONSTRAINT role_fk FOREIGN KEY (privilege_id) REFERENCES public.privileges(id);
ALTER TABLE ONLY public.users_to_privileges ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
--assigning priveleges for fazz@mail.ru
INSERT INTO users_to_privileges VALUES((SELECT id FROM users WHERE email = 'fazz@mail.ru'), 1);
INSERT INTO users_to_privileges VALUES((SELECT id FROM users WHERE email = 'fazz@mail.ru'), 2);
INSERT INTO users_to_privileges VALUES((SELECT id FROM users WHERE email = 'fazz@mail.ru'), 3);
INSERT INTO users_to_privileges VALUES((SELECT id FROM users WHERE email = 'fazz@mail.ru'), 4);
