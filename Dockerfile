FROM openjdk:17
WORKDIR /spring-app
EXPOSE 8080
ADD target/spring-mysql-redis.jar spring-mysql-redis.jar
ENTRYPOINT [ "java", "-jar", "spring-mysql-redis.jar" ]