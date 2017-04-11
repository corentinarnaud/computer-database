FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root

EXPOSE 3306

ADD ./1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
ADD ./2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
ADD ./3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql