# 🧠 Online Quiz Application

A full-stack backend application built using **Spring Boot** that allows users to register, log in, take quizzes, and view their scores.  
This project implements **JWT authentication**, **password encryption**, and supports **admin and user roles**.

---

##  Features

###  User Management
- User signup with validation (unique username & email)
- Login with JWT token generation
- Role-based access control (`ROLE_USER`, `ROLE_ADMIN`)
- Secure password storage using **BCrypt hashing and salting**

###  Quiz Management (Admin Only)
- Create, update, and delete quizzes
- Add multiple-choice questions with correct answers
- Assign quizzes to categories or topics

###  User Quiz Features
- Attempt quizzes and submit answers
- Score calculation after quiz submission
- View past quiz attempts and scores

## 🧰 Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Spring Boot |
| Security | Spring Security + JWT |
| Database | MySQL |
| ORM | Spring Data JPA |
| Password Encryption | BCrypt |
| API Testing | Postman |
| Build Tool | Maven |
| Language | Java 17 |

---

##  Project Structure

Online-Quiz-Application/
│
├── src/main/java/com/Online/Quiz/Application/
│ ├── controller/ # REST Controllers
│ ├── service/ # Business Logic
│ ├── repository/ # Data Access Layer
│ ├── entity/ # JPA Entities
│ ├── dto/ # Data Transfer Objects
│ ├── security/ # JWT & Security Config
│ └── exception/ # Global Exception Handling
│
├── src/main/resources/
│ ├── application.properties # DB Config
│
└── README.md


 Configure the Database
Edit your application.properties file:

## properties
spring.datasource.url=jdbc:mysql://localhost:3306/quiz_app
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret=your_secret_key

## Build and Run the Application
mvn clean install
mvn spring-boot:run
The app runs by default on http://localhost:8080

## API Endpoints
## Authentication
Method	Endpoint	Description
POST	/api/user/signup	Register a new user
POST	/api/user/login	Authenticate user & generate JWT
POST	/api/user/admin	Create admin (restricted)

## Quiz Management (Admin)
Method	Endpoint	Description
POST	/api/quizzes	Create a quiz
PUT	/api/quizzes/{id}	Update quiz
DELETE	/api/quizzes/{id}	Delete quiz
GET	/api/quizzes	List all quizzes

## Quiz Attempts (User)
Method	Endpoint	Description
POST	/api/quiz/attempt/{quizId}	Attempt quiz
GET	/api/quiz/result/{userId}	View past results

## Security Implementation
Passwords are hashed using BCrypt before saving to the database.

JWT token is generated upon successful login.

Every API endpoint is protected via Spring Security.

Role-based access control ensures admins manage quizzes, while users can only attempt them.

## Documentation
## Setup Guide

Configure MySQL in application.properties.

Run the application using Maven or your IDE.

Test endpoints using Postman.

## User Guide
Signup using /api/user/signup

Login using /api/user/login

Use the returned JWT token in the Authorization header:

Authorization: Bearer <your_token_here>
Access secured endpoints accordingly.

## Assumptions & Limitations
JWT tokens expire after a fixed time (can be adjusted in JWTService).

No frontend implemented (backend-only project).

Currently supports one correct answer per question.

Pagination and filtering are not yet implemented.

## Contributing
Fork the repo

Create your feature branch (git checkout -b feature/new-feature)

Commit your changes (git commit -m "Added new feature")

Push to branch (git push origin feature/new-feature)

Create a Pull Request

## Author
Mayank Kumar
📧 Email: themayank8@gmail.com
💼 GitHub: github.com/Mayank-mk170

📜 License
This project is licensed under the MIT License.
You’re free to use, modify, and distribute this project for educational purposes.
