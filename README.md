# Generate new spring project
1. Go start.spring.io
2. Select Maven Project
3. Select Project Language Java
4. Select Java 21
5. Add dependencies: Spring Web, Spring Security, Spring Data JPA, MySQL Driver, Lombok
6. Generate project
7. Unzip the project

# Get Started with Spring Boot
1. Import the project into an IDE (for VS code install Spring Boot extension pack and Extension Pack for Java)
2. Update src/main/resources/application.properties file with your database credentials
3. Run "mvn clean install" to compile the project and update the database
4. Run the application using the sytax "mvnw spring-boot:run"

## 1. Build the Backend JAR
1. run mvnw clean package -DskipTests
2. The backend jar file will be created in the target folder: backend/target/backend-0.0.1-SNAPSHOT.jar