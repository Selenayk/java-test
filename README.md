## Student Management Service

### Features Implemented:
- `StudentService` class for handling CRUD operations on students (get, add, delete).
- `StudentRepository` class with methods for checking if a student's email exists in the database.
- `Student` model class with necessary fields (e.g., name, email, gender).
- Custom exceptions such as `StudentNotFoundException` and `BadRequestException` for error handling.
- `StudentController` to handle HTTP requests for student operations (GET, POST, DELETE).

### Testing:
- **Unit Tests**: 
  - `StudentServiceTest`: Tests for `StudentService` methods, mocking `StudentRepository` to isolate the service layer.
- **Integration Tests**: 
  - `StudentRepositoryTest`: Verifies the functionality of `StudentRepository` methods, including checking if a student's email exists, using a real in-memory database for testing.
