## htid-profile-service
# Run mongo database
docker-compose up -d

## API DOC
#URL 
http://localhost:8080/swagger.html
#Xuất file Yaml
http://localhost:8080/api-docs.yaml

##run project
./mvnw install && ./mvnw spring-boot:run -pl profile-api