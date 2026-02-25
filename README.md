# MCP Server - Walking Skeleton

A minimal **Model Context Protocol (MCP) Server** implementation using Spring Boot 3.x, Java 21, and Maven.

## 🎯 Overview

This is a walking skeleton that demonstrates:
- OAuth2-based JWT authentication
- Protected MCP endpoint at `/v1/mcp`
- Domain verification for OAuth2 provider configuration
- Simple ping-pong logic for proof of concept

## 🚀 Quick Start

### Prerequisites
- Java 21
- Maven 3.6+

### Build and Run

```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

The server will start on `http://localhost:8080`

## 📡 API Endpoints

### 1. Domain Verification Endpoint
```
GET /.well-known/mcp-configuration
```
Returns the verification token for OAuth2 provider configuration.

**Response:**
```json
{
  "verification_token": "mcp-server-verification-token-12345"
}
```

### 2. MCP Endpoint (Protected)
```
POST /v1/mcp
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

**Request Body:**
```json
{
  "message": "ping"
}
```

**Response:**
```json
{
  "message": "pong",
  "timestamp": 1708901234567
}
```

### 3. Health Check (Protected)
```
GET /v1/mcp/health
Authorization: Bearer <JWT_TOKEN>
```

## 🔐 Authentication

This server uses OAuth2 JWT authentication. For this walking skeleton:
- JWT tokens must be signed with HMAC-SHA256
- The secret key is derived from a hardcoded string (for prototype purposes)
- In production, configure with a real OAuth2 provider's JWK Set URI

## ✅ Acceptance Criteria

- [x] Application starts successfully
- [x] Domain verification endpoint returns correct token
- [x] Calling `/v1/mcp` without token → 401 Unauthorized
- [x] Calling `/v1/mcp` with valid JWT → 200 OK
- [x] Returns "pong" when input is "ping"

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn clean verify
```

### Manual Testing

**1. Test Domain Verification:**
```bash
curl http://localhost:8080/.well-known/mcp-configuration
```

**2. Test Protected Endpoint Without Token (should return 401):**
```bash
curl -X POST http://localhost:8080/v1/mcp \
  -H "Content-Type: application/json" \
  -d '{"message": "ping"}'
```

**3. Test Protected Endpoint With Mock Token:**
For testing with a valid JWT, you'll need to generate a token signed with the same secret key configured in the application.

## 📦 Project Structure

```
mcp-server/
├── src/
│   ├── main/
│   │   ├── java/com/adrianlim83/mcpserver/
│   │   │   ├── McpServerApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── DomainVerificationController.java
│   │   │   │   └── McpController.java
│   │   │   └── model/
│   │   │       ├── McpRequest.java
│   │   │       └── McpResponse.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/adrianlim83/mcpserver/
│           ├── McpServerApplicationTests.java
│           └── controller/
│               ├── DomainVerificationControllerTest.java
│               └── McpControllerTest.java
├── pom.xml
└── README.md
```

## 🔧 Configuration

Key configuration properties in `application.properties`:

- `server.port=8080` - Server port
- `mcp.domain.verification.token` - Domain verification token for OAuth2 providers

## ⚠️ Limitations (By Design)

This is a **walking skeleton** with intentional limitations:
- ❌ No database
- ❌ No refresh tokens
- ❌ No multi-provider support
- ❌ No advanced scope handling
- ❌ No complex DDD aggregates
- ❌ No resilience/retry logic

It exists solely to prove: **ChatGPT → OAuth → MCP Endpoint → Response** works end-to-end.

## 🔮 Future Enhancements

- Multi-provider OAuth support
- Client registration management
- Token storage and refresh
- Rate limiting
- Provider-specific MCP adapters
- Auditing and logging
- Domain events

## 📄 License

This is a prototype/walking skeleton for demonstration purposes.
