FROM openjdk:8

RUN mkdir /provider-api

WORKDIR /provider-api

ADD ./provider-api/target/provider-api-1.0.0.jar /provider-api

EXPOSE 8080

CMD ["java", "-jar", "provider-api-1.0.0.jar"]