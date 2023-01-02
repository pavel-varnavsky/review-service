# review-service
**Service for managing products reviews** 

For persisting data service is using:
- PostgreSQL. When running in Docker all necessary scripts will be executed at startup. If running with standalone db instance then need to provide correct url, credentials and schema name for it (in bootstrap.yml `spring.datasource.*` and `spring.liquibase.liquibase.schema`).
- Liquibase for migrations. Some of initial data is predefined and if you need to add more initial rows for reviews you can do it in file `src/main/resources/db_migrations/issues/ISSUE-1/002-TASK-1-seed_initial_data.sql`.
- Initial review data seeded for product id = "BB5476"

Security:
- in `bootstrap.yml` you can change user and password for authentication to access API (`spring.security.user`). The same user/password must be set in product-service.
- there were some troubles with restrict all API's except 'get product review' so all endpoints are closed with authentication.

For easier access to API there was added Swagger dependency. It's accessible through http://localhost:8082/swagger-ui.html (change to your own host/port if needed).

Run standalone:
1. `mvn clean package`
2. `cd target`
3. `java -jar review-service-0.0.1-SNAPSHOT.jar`

Run in Docker:
1. `mvn clean package`
2. `cp target/review-service-0.0.1-SNAPSHOT.jar src/main/docker`
3. `cd src/main/docker`
4. `docker-compose -p "review" up`

When running in Docker default external port for application is 8082. Change to your own in `scr/main/docker/docker-compose.yml` if needed.