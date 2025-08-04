# Secure Employee API Guide

## Security Features Implemented

### Authentication & Authorization
- **Basic Authentication** - Username/password required
- **Role-Based Access** - ADMIN vs USER permissions
- **Method-Level Security** - @PreAuthorize annotations

### User Accounts
```
Admin User:
- Username: admin
- Password: secure123
- Roles: ADMIN (full access)

Regular User:
- Username: user  
- Password: user123
- Roles: USER (read-only)
```

### Security Controls
- **Input Validation** - Jakarta validation constraints
- **Rate Limiting** - 100 requests per IP
- **Error Handling** - No sensitive data exposure
- **CSRF Protection** - Disabled for API usage

### Monitoring & Health
- **Actuator Endpoints** - /actuator/health, /actuator/metrics
- **Security Logging** - Access attempts and failures
- **Health Checks** - System status monitoring

## Testing Secure API

### 1. Start Servers
```bash
./gradlew server:bootRun  # Mock server
./gradlew api:bootRun     # Secure API
```

### 2. Test Authentication
```bash
# Without auth (401 Unauthorized)
curl http://localhost:8111/api/v1/employee

# With admin auth (200 OK)
curl -u admin:secure123 http://localhost:8111/api/v1/employee

# With user auth (200 OK - read only)
curl -u user:user123 http://localhost:8111/api/v1/employee
```

### 3. Test Authorization
```bash
# Admin can create (200 OK)
curl -u admin:secure123 -X POST http://localhost:8111/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","salary":75000,"age":30,"title":"Developer"}'

# User cannot create (403 Forbidden)
curl -u user:user123 -X POST http://localhost:8111/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","salary":75000,"age":30,"title":"Developer"}'
```

### 4. Test Validation
```bash
# Invalid data (400 Bad Request)
curl -u admin:secure123 -X POST http://localhost:8111/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{"name":"","salary":-1000,"age":10,"title":""}'
```

### 5. Health Check
```bash
# Public health endpoint
curl http://localhost:8111/actuator/health

# Detailed health (requires auth)
curl -u admin:secure123 http://localhost:8111/actuator/health
```

## 🔧 Security Configuration

### Endpoint Access Control
- **Public**: /actuator/health
- **Authenticated**: /api/v1/employee/** (all users)
- **Admin Only**: POST, DELETE operations

### Validation Rules
- **Name**: Required, not blank
- **Salary**: Required, positive number
- **Age**: Required, 16-75 range
- **Title**: Required, not blank

### Rate Limiting
- **Limit**: 100 requests per IP
- **Response**: 429 Too Many Requests
- **Reset**: Application restart

## 🚨 Security Best Practices Applied

1. **Principle of Least Privilege** - Users get minimum required access
2. **Defense in Depth** - Multiple security layers
3. **Input Validation** - All user input validated
4. **Error Handling** - No sensitive information leaked
5. **Logging** - Security events tracked
6. **Rate Limiting** - DoS attack prevention