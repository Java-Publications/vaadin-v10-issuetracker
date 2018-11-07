#FROM postgres:9.6.10
FROM postgres:11.0
LABEL maintainer="Sven Ruppert sven.ruppert@vaadin.com" description="PostgreSQl for V10 IssueTracker"

RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
ENV LANG de_DE.utf8

RUN apt-get update
RUN apt-get -y install --no-install-recommends apt-utils
RUN apt-get -y install postgresql-contrib

WORKDIR /

COPY docker-entrypoint-initdb.d /docker-entrypoint-initdb.d
RUN ls -la /docker-entrypoint-initdb.d/


