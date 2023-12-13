FROM openjdk:17
WORKDIR /spring-app
EXPOSE 8080
ADD target/spring-redis.jar spring-redis.jar
ENTRYPOINT [ "java", "-jar", "spring-redis.jar" ]