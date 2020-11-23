FROM adoptopenjdk/openjdk11:alpine
WORKDIR /app
COPY . /app

EXPOSE 8080
CMD ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n"
