FROM maven:3.9.6-amazoncorretto-17 AS build

RUN mkdir /app
COPY . /app
WORKDIR /app
RUN mvn package -Dmaven.test.skip

FROM amazoncorretto:17

RUN mkdir /app
COPY --from=build /app/target/cicd-deploy-demo-1.0.0.jar /app/cicd-deploy-demo.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/cicd-deploy-demo.jar"]
