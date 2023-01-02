# online-bootstore-springboot-app

### Steps to execute

1. Run docker compose using `docker-compose build` and then `docker-compose up`.
2. All APIs are secured through JWT authentication. 
3. Swagger UI is available to execute the APIs. 
   1. `/swagger-ui` url is not authenticated
   2. Execute `/authenticate` API and copy the token
   3. Currently, `username` and `password` are `test` each.
   4. Paste the token in the headers of the rest of the APIs
4. For reference, refer `src/main/java/com/readingisgood/getirhomeassignment/testRequests.http` where I have mentioned
all the API request with request bodies
5. `/h2-console` is not authenticated. You can check the stored data. Credentials are in `application.yml`
6. Tried to cover all the specs mentioned. 
7. `Architectre.png` of database entities is available in this directory. 
8. My tech stack Java, Spring boot, security, JPA, Mockito tests, H2 in memory database.
