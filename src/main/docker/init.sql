create database br;

\connect br;

create schema product;

create user br with encrypted password 'br';

grant all privileges on database br to br;

grant all privileges on schema product to br;

alter user br set search_path = 'product';