FROM adoptopenjdk/openjdk11:alpine
WORKDIR /app
COPY . /app

EXPOSE 8080
CMD ./mvnw install && ./mvnw spring-boot:run -pl profile-api
