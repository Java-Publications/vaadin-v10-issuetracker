#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER yyy;
    CREATE DATABASE xxx;
    GRANT ALL PRIVILEGES ON DATABASE xxx TO yyy;

    CREATE SCHEMA public;
    SET TIMEZONE = 'Europe/Berlin';
    CREATE EXTENSION xxx.pgcrypto;
EOSQL