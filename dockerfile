FROM eclipse-temurin:17.0.5_8-jre

RUN mkdir /primerjalnikCenApp

WORKDIR /primerjalnikCenApp

ADD ./api/target/api-1.0-SNAPSHOT.jar /primerjalnikCenApp

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-1.0-SNAPSHOT.jar"]


#Za DockerHub:
#docker login
#docker build -t primerjalnikcen .
#docker tag primerjalnikcen {username}/primerjalnikcen
#docker push {username}/primerjalnikcen