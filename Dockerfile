FROM maven:3.6.3-jdk-11
WORKDIR /usr/java/
COPY . .
RUN mvn clean package

WORKDIR /usr/app
RUN cp /usr/java/target/*.jar ./app.jar
EXPOSE 6060
CMD ["java", "-jar", "app.jar"]