FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY . .
EXPOSE 8000
RUN mvn clean install
CMD ["mvn", "tomcat7:run"]