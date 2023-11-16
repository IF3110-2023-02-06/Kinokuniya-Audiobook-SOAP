FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY . .
EXPOSE 8001
RUN mvn clean install
ENTRYPOINT ["bash", "./scripts/init_server.sh"]
