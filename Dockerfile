FROM eclipse-temurin:21-jdk


EXPOSE 8081

ADD build/libs/myapp.jar myapp.jar

ENTRYPOINT ["java","-jar","/myapp.jar"]
