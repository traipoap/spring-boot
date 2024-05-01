FROM amazoncorretto:21.0.3-alpine3.19

WORKDIR /spring

COPY target /spring/target

CMD ["java", "-jar", "target/local.com-0.0.1-SNAPSHOT.jar"]