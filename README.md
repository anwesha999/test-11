# Employee API

A secure REST API for employee management that provides CRUD operations with authentication, authorization, input validation, rate limiting, and performance optimizations.

## Project Overview

This application serves as a proxy/adapter to a mock employee service, implementing enterprise-level security features and optimized algorithms for data processing. The API follows RESTful principles and includes comprehensive documentation via Swagger/OpenAPI.

## Technology Stack

- **Spring Boot 3.x** - Main application framework
- **Spring Security 6.x** - Authentication and authorization
- **Spring Web** - REST API implementation
- **Jakarta Validation** - Input validation
- **Lombok** - Code reduction and boilerplate elimination
- **RestTemplate** - HTTP client for external API integration
- **SpringDoc OpenAPI** - API documentation and Swagger UI
- **JUnit 5 & Mockito** - Unit and integration testing
- **Gradle** - Build automation and dependency management

## Architecture

The application follows a layered architecture with clear separation of concerns:

### Controller Layer
- Handles HTTP requests and responses
- Implements facade pattern for unified interface
- Contains security annotations for authorization
- Performs input validation

### Service Layer
- Contains business logic
- Implements repository pattern for data access abstraction
- Acts as adapter to external mock API
- Handles HTTP communication with RestTemplate

### Model Layer
- Employee entity with JSON property mapping
- Input validation models with constraints
- Generic response wrapper for API responses

### Security Layer
- Authentication configuration
- Authorization rules and role-based access control
- Rate limiting implementation
- Global exception handling

### Utility Layer
- Optimized algorithms for data processing
- Strategy pattern for different operations
- Performance-focused implementations

## Features

### Security
- Basic HTTP authentication with encrypted passwords
- Role-based access control (ADMIN and USER roles)
- Method-level security with PreAuthorize annotations
- Input validation with Jakarta validation constraints
- Rate limiting (100 requests per IP address)
- Global exception handling with secure error responses

### Performance Optimizations
- Min-heap algorithm for top K elements (O(n log k) complexity)
- Parallel processing for search operations
- Stream optimization for aggregation operations
- Efficient data structures and algorithms

### API Documentation
- Interactive Swagger UI for API testing
- Comprehensive OpenAPI specification
- Request/response examples and schema documentation
- Authentication integration in documentation

### Monitoring and Health
- Spring Boot Actuator endpoints
- Health checks and metrics
- Structured logging with security event tracking
- Error handling without sensitive data exposure

## API Endpoints

### Employee Management
- `GET /api/v1/employee` - Retrieve all employees
- `GET /api/v1/employee/search/{searchString}` - Search employees by name fragment
- `GET /api/v1/employee/{id}` - Get employee by ID
- `GET /api/v1/employee/highestSalary` - Get highest salary among all employees
- `GET /api/v1/employee/topTenHighestEarningEmployeeNames` - Get top 10 highest earning employee names
- `POST /api/v1/employee` - Create new employee (Admin only)
- `DELETE /api/v1/employee/{id}` - Delete employee by ID (Admin only)

### System Endpoints
- `GET /actuator/health` - Application health status
- `GET /actuator/metrics` - Application metrics
- `GET /swagger-ui.html` - Interactive API documentation

## Authentication

The API uses Basic HTTP authentication with two predefined user accounts:

### Admin User
- Username: admin
- Password: secure123
- Roles: ADMIN (full access to all operations)

### Regular User
- Username: user
- Password: user123
- Roles: USER (read-only access)

## Setup and Installation

### Prerequisites
- Java 17 or higher
- Gradle 7.6 or higher

### Running the Application

1. **Start the Mock Employee Server**
   ```bash
   ./gradlew server:bootRun
   ```
   The mock server will start on http://localhost:8112

2. **Start the Employee API**
   ```bash
   ./gradlew api:bootRun
   ```
   The API will start on http://localhost:8111

3. **Access Swagger Documentation**
   Navigate to http://localhost:8111/swagger-ui.html

### Configuration

The application uses environment variables for secure configuration:

```bash
export ADMIN_USERNAME=admin
export ADMIN_PASSWORD=your-secure-password
export SERVER_PORT=8111
```

For local development, copy the template configuration:
```bash
cp api/src/main/resources/application-template.yml api/src/main/resources/application.yml
```

## Testing

### Running Tests
```bash
./gradlew api:test
```

### Test Coverage
- Unit tests for all service and controller methods
- Integration tests for security configuration
- Mock-based testing for external API integration
- Validation testing for input constraints

### Manual Testing

#### Using curl
```bash
# Get all employees
curl -u admin:secure123 http://localhost:8111/api/v1/employee

# Create employee
curl -u admin:secure123 -X POST http://localhost:8111/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","salary":75000,"age":30,"title":"Developer"}'

# Search employees
curl -u admin:secure123 http://localhost:8111/api/v1/employee/search/John
```

#### Using Swagger UI
1. Navigate to http://localhost:8111/swagger-ui.html
2. Click "Authorize" and enter credentials (admin/secure123)
3. Test any endpoint using the interactive interface

## Security Features

### Input Validation
- Name: Required, not blank
- Salary: Required, positive number
- Age: Required, range 16-75
- Title: Required, not blank

### Rate Limiting
- Limit: 100 requests per IP address
- Response: 429 Too Many Requests when exceeded
- Reset: Application restart

### Error Handling
- Structured error responses
- No sensitive information leakage
- Comprehensive logging for security events
- Proper HTTP status codes

## Performance Characteristics

### Algorithm Complexity
- Get All Employees: O(1) - Direct API call
- Search by Name: O(n) - Linear search with parallel processing
- Get by ID: O(1) - Direct lookup
- Max Salary: O(n) - Single pass through data
- Top 10 Earners: O(n log k) where k=10 - Min-heap approach
- Create/Delete: O(1) - Direct API operations

### Scalability Considerations
- Stateless design for horizontal scaling
- Efficient algorithms for large datasets
- Connection pooling ready
- Caching strategy implementation ready

## Development

### Code Quality
- Spotless plugin for consistent code formatting
- Comprehensive Javadoc documentation
- Clean code principles and SOLID design patterns
- Lombok for boilerplate reduction

### Build and Deployment
```bash
# Build application
./gradlew api:build

# Run tests
./gradlew api:test

# Apply code formatting
./gradlew api:spotlessApply
```

## External Dependencies

The application integrates with a mock employee API that provides:
- Employee data retrieval
- Employee creation and deletion
- Search functionality
- Data persistence simulation

## Monitoring and Observability

### Health Checks
- Application health endpoint
- Dependency health verification
- Database connectivity status

### Metrics
- Request/response metrics
- Error rate tracking
- Performance counters
- Security event metrics

### Logging
- Structured logging format
- Security event logging
- Error tracking and debugging
- Request correlation IDs

## Production Considerations

### Security
- Environment-based configuration
- Secret management integration
- HTTPS enforcement
- Security headers implementation

### Performance
- Connection pooling configuration
- Caching strategy implementation
- Database optimization
- Load balancing support

### Monitoring
- Application performance monitoring
- Error tracking and alerting
- Log aggregation and analysis
- Health check automation

## Contributing

1. Follow existing code style and formatting
2. Write comprehensive tests for new features
3. Update documentation for API changes
4. Ensure security best practices are followed
5. Run all tests before submitting changes

## License

This project is developed as part of a coding assessment and follows enterprise development standards and best practices.