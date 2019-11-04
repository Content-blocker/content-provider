FROM openjdk:8

RUN mkdir /provider

WORKDIR /provider

ADD ./provider-api/target/provider-api-1.0.0.jar /provider

EXPOSE 8080

CMD ["java", "-jar", "provider-api-1.0.0.jar"]