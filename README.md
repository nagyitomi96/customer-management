Tech Stack:
- Java 21
- Spring Boot 3.4.3 (Spring Security, Spring Data JPA, Spring Web)
- JWT Authentication 
- H2 Database (In-memory for testing)
- Swagger (API Documentation)
- Maven (Dependency Management)
- --------------------------------------------------------------------------------------------
How to run the project:
1. Clone the repository : git clone https://github.com/nagyitomi96/customer-management.git
2. Go to the project directory: cd customer-management
3. Run the project: mvn clean install -DskipTests -> mvn spring-boot:run
--------------------------------------------------------------------------------------------
API Documentation:
http://localhost:8080/swagger-ui.html
--------------------------------------------------------------------------------------------
Authentication:
1. Register a new user: POST /api/auth/register
2. Request Body:
{
  "username": "testuser",
  "password": "password"
}
3. Get a JWT Token: POST /api/auth/login
4. Request Body:
{
  "username": "testuser",
  "password": "password"
}
5. Response:
{
   "token": "your_jwt_token"
}
6. Use JWT for Authorization in the header:
GET /api/customers
Authorization: Bearer your_jwt_token
--------------------------------------------------------------------------------------------
Example API Calls:
1. Get all customers: GET /api/customers

Response Example:
[
    {
       "name": "Test",
       "email": "test@example.com",
       "age": 30,
       "city": "City1",
       "phoneNumber": "+36701234567",
       "address": "Test Address",
       "isActive": true
    }
]
2. Get customer by ID: GET /api/customers/{id}
3. Update Customer: PUT /api/customers/{id}

Request Body:
[
    {
        "name": "New Name",
        "email": "new@example.com",
        "age": 28,
        "city": "City2",
        "phoneNumber": "+36703333333",
        "address": "New Address",
        "isActive": true
    }
]
4. Delete Customer: DELETE /api/customers/{id}
--------------------------------------------------------------------------------------------
Running tests: mvn test
--------------------------------------------------------------------------------------------
Notes:
- The database resets on restart (H2 in-memory database).
- Authentication is required for all API calls except registration and login.
- Use Swagger UI to test endpoints interactively.

