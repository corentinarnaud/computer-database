docker rm -f jenkins || true
docker build -t jenkinsi jenkins/
docker run -d --name jenkins -v CDB_Volume:/cdb -v WAR_CDB:/cdb_war -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p 8085:8080 jenkinsi
sleep 1
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword

