#Script for docker jenkins

if ! docker inspect networkprod > /dev/null 2>&1
then
    sudo docker network create --driver=bridge --subnet=172.19.0.0/16 --gateway=172.19.0.1 networkprod
fi

if ! docker inspect mysqliprod > /dev/null 2>&1
then
sudo docker build -t mysqliprod dockerfile/prod/mysqlprod/
fi

if ! docker inspect mysqlprod > /dev/null 2>&1
then
    sudo docker run -i --net networkprod --ip 172.19.0.2 --name mysqlprod -d mysqliprod
    sleep 25
else
    sudo start mysqlprod
fi

if ! docker inspect maveniprod > /dev/null 2>&1
then
    sudo docker build -t maveniprod dockerfile/prod/mavenprod/
fi

if ! docker inspect mavenprod > /dev/null 2>&1
then
    sudo docker run -i --net networkprod --name mavenprod -d maveniprod
else
    sudo start -i mavenprod
fi


if ! docker inspect tomcat > /dev/null 2>&1
then
    sudo 
