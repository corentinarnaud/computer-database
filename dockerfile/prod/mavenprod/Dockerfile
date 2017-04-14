FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

CMD mvn clean package -Dmaven.test.skip=true