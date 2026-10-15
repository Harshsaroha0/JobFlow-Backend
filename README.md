
# JobFlow — AI-Assisted Job Application Management

JobFlow is a full-stack job application management project built with Java, Spring Boot microservices, MySQL, and a responsive HTML/CSS/JavaScript frontend. It brings job listings, application tracking, resume management, skills, notifications, and local resume/job-description analysis into one application.

> **Project status:** Backend and frontend are combined in this repository. Verify each service and its routes locally before treating every feature as production-ready.

## Features

- **Job management:** Backend service for job-related operations.
- **Application tracking:** Organize and track job application workflows.
- **Resume management:** Dedicated resume service.
- **Skills management:** Skills and user-skill routes.
- **Notifications:** Dedicated notification service.
- **Resume analysis:** Extract skills and resume sections from supplied resume text.
- **Job description analysis:** Detect known skills and extract recognized responsibilities and qualification sections.
- **Microservices architecture:** Separate Spring Boot services, service discovery, centralized configuration, and API Gateway.
- **Web frontend:** HTML, CSS, and JavaScript.
- **Manual API testing:** Test endpoints with Postman.

## Technology Stack

| Area | Technologies |
|---|---|
| Backend | Java 21, Spring Boot 3.5.x |
| Microservices | Spring Cloud, Spring Cloud Gateway |
| Service discovery | Netflix Eureka |
| Configuration | Spring Cloud Config Server |
| Database | MySQL |
| Persistence | Spring Data JPA where configured |
| Frontend | HTML, CSS, JavaScript |
| AI-related analysis | Local Java-based text and skill extraction |
| Build | Maven |
| API testing | Postman |
| Version control | Git and GitHub |

## Architecture

The frontend sends API requests through the API Gateway. The Gateway uses Eureka service discovery to route requests to the appropriate registered backend service. Services obtain their configuration from the Config Server.

```text
                  Web Frontend
              HTML / CSS / JavaScript
                         |
                         v
                  API Gateway
                    :8080
                         |
             +-----------+-----------+
             |           |           |
             v           v           v
        User Service  Job Service  Resume Service
             |
      +------+-------------------------------+
      |              |             |         |
      v              v             v         v
 Application      Skill       Notification  AI
 Tracking         Service       Service    Service
 Service

       Eureka Discovery: :8761
       Config Server:    :8888
       Database:         MySQL
```

*The diagram is a logical overview. Actual service communication and database ownership depend on each service's configuration.*

## Repository Structure

```text
JobFlow-Backend/
├── ai-service/
├── api-gateway/
├── applicationTracking-service/
├── config-server/
├── eureka-server/
├── frontend/
│   ├── css/
│   ├── js/
│   ├── pages/
│   └── index.html
├── job-service/
├── jobflow-config/
├── notification-service/
├── resume-service/
├── skill-service/
├── storage/
├── user-service/
└── README.md
```

### Service responsibilities

| Component | Responsibility |
|---|---|
| `config-server` | Centralized application configuration |
| `eureka-server` | Service registration and discovery |
| `api-gateway` | Routes client requests to backend services |
| `user-service` | User-related operations |
| `job-service` | Job-related operations |
| `resume-service` | Resume-related operations |
| `applicationTracking-service` | Job application tracking |
| `skill-service` | Skills and user-skill operations |
| `notification-service` | Notification-related operations |
| `ai-service` | Resume and job description text analysis |
| `frontend` | Browser-based user interface |
| `storage` | Storage-related project resources; inspect its implementation for exact behavior |

## Prerequisites

Install and configure:

- Java Development Kit (JDK) 21
- Maven, or use the Maven Wrapper if available
- MySQL Server
- Git
- IntelliJ IDEA or another Java IDE
- Postman for API testing
- A modern browser

## Getting Started

### 1. Clone the repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd JobFlow-Backend
```

Replace the placeholder with the actual GitHub repository URL.

### 2. Configure MySQL

Start MySQL and create the databases required by the services. The shared project configuration has used the `jobflow` database; inspect the service configuration files to confirm whether every service uses it or has a separate schema.

Example:

```sql
CREATE DATABASE jobflow;
```

Configure the database URL, username, and password in the relevant configuration files or environment variables. Use your own local credentials and do not commit passwords or secrets.

### 3. Start the backend services

Start services in this order:

1. MySQL
2. Config Server
3. Eureka Server
4. Business services, including User, Job, Resume, Application Tracking, Skill, Notification, and AI
5. API Gateway

The Config Server is configured to use the local configuration repository at:

```text
file:///D:/JobFlow/jobflow-config
```

If you clone the project to a different location, update the configuration repository path accordingly.

The expected local ports confirmed in the project configuration include:

| Component | Port |
|---|---:|
| Config Server | 8888 |
| Eureka Server | 8761 |
| API Gateway | 8080 |
| AI Service | 8087 |

Check each service's configuration for its actual port and startup requirements.

### 4. Verify service discovery

Open the Eureka dashboard:

```text
http://localhost:8761
```

Confirm that the required services are registered before testing requests through the Gateway.

### 5. Start the frontend

The frontend is located in the `frontend/` directory.

Open `frontend/index.html` in a browser, or serve the folder using a local static web server. If the browser blocks requests when opening the file directly, use a local development server.

The frontend configuration is located in:

```text
frontend/js/config.js
```

Its current API base URL is:

```javascript
API_BASE_URL: "http://localhost:8080"
```

Keep the frontend pointed at the API Gateway rather than calling individual microservices directly.

## API Routes

The following Gateway route prefixes are configured in `jobflow-config/api-gateway.yaml`.

| Service | Gateway route prefix |
|---|---|
| User Service | `/api/users/**` |
| Job Service | `/api/jobs/**` |
| Resume Service | `/api/resumes/**` |
| Application Tracking | `/api/applications/**` |
| Skill Service | `/api/skills/**` |
| User Skills | `/api/user-skills/**` |
| Notification Service | `/api/notifications/**` |
| AI Service | `/api/ai/**` |

These are route prefixes, not a complete list of individual endpoints. Check each controller for supported HTTP methods, request bodies, validation rules, and response formats.

### Resume analysis

**Direct service URL**

```http
POST http://localhost:8087/api/ai/resume/analyze
```

**Through the API Gateway**

```http
POST http://localhost:8080/api/ai/resume/analyze
```

Example JSON request:

```json
{
  "resumeText": "Java developer skilled in Java, Spring Boot, REST APIs and MySQL. Built a Banking Management System using Java and JDBC. B.Tech in Computer Science."
}
```

The response contains:

- `skills`
- `experience`
- `education`
- `projects`

### Job description analysis

**Direct service URL**

```http
POST http://localhost:8087/api/ai/job-description/analyze
```

**Through the API Gateway**

```http
POST http://localhost:8080/api/ai/job-description/analyze
```

Example JSON request:

```json
{
  "jobDescription": "Java Backend Engineer\n\nResponsibilities:\nDevelop REST APIs\nMaintain backend services\n\nQualifications:\nB.Tech in Computer Science\nKnowledge of Java and SQL"
}
```

The response contains:

- `requiredSkills`
- `responsibilities`
- `qualifications`

Use Postman with `Content-Type: application/json` to test these endpoints.

## About the AI Service

The current AI service performs **local, rule-based text analysis in Java**. It does not require an OpenAI API key or an external AI provider.

- Skills are detected by matching text against a predefined technology list.
- Resume sections are extracted using recognized headings.
- Job-description responsibilities and qualifications are extracted when supported headings are present.

This is a lightweight text-processing implementation, not a generative large language model. Results depend on the wording, formatting, and supported skills/headings. Validate extracted information before relying on it.

## Manual Testing Checklist

- [ ] MySQL is running and the required databases exist.
- [ ] Config Server starts successfully.
- [ ] Eureka dashboard opens and required services register.
- [ ] Each backend service starts without configuration or database errors.
- [ ] Gateway starts and loads its route configuration.
- [ ] Resume analysis works directly on port `8087`.
- [ ] Job description analysis works directly on port `8087`.
- [ ] AI endpoints work through Gateway port `8080`.
- [ ] User, job, resume, application, skill, and notification endpoints are checked against their controllers.
- [ ] Frontend loads and its API requests use the Gateway.
- [ ] No secrets or local-only credentials are committed.

## Troubleshooting

**Service cannot connect to Config Server**

- Confirm Config Server is running on port `8888`.
- Check the `spring.config.import` value.
- Verify the local configuration repository path.

**Service is missing from Eureka**

- Confirm Eureka is running on port `8761`.
- Check the service's Eureka client configuration and startup logs.

**Gateway returns 503 or cannot find a service**

- Check that the target service is registered in Eureka.
- Confirm the service ID in the Gateway route matches the registered service name.
- Check the service logs and route configuration.

**AI endpoint works directly but not through Gateway**

- Confirm the `AI-SERVICE` registration in Eureka.
- Confirm the `/api/ai/**` route exists in the active Gateway configuration.
- Restart or refresh the Gateway if configuration is not being reloaded.

**Frontend cannot call the backend**

- Confirm the API base URL in `frontend/js/config.js`.
- Check that the Gateway is running.
- Inspect the browser console and network tab for CORS, connection, or HTTP errors.

## Security and Configuration

- Do not commit real database passwords, API keys, access tokens, or production secrets.
- Keep local configuration and environment-specific values outside version control where appropriate.
- Verify authentication and authorization in the relevant controllers and security configuration before exposing services publicly.
- The configured local URLs and ports are for development, not a production deployment.

## Future Improvements

Possible future improvements include:

- Automated unit and integration tests
- Improved resume parsing for different formats and headings
- Resume-to-job matching with a transparent match score and missing-skill analysis
- Authentication and authorization verification across all routes
- Containerized deployment and CI/CD
- Production configuration, logging, monitoring, and API documentation

These are potential improvements, not claims that every item is already implemented.

## Author

**Harsh Saroha**

Java Backend Engineer aspirant

- GitHub: [Harshsaroha0](https://github.com/Harshsaroha0)
- Portfolio: [harshsaroha0.github.io](https://harshsaroha0.github.io)

---

Built as a full-stack Java backend project to practice microservices, REST API design, service discovery, centralized configuration, database integration, and frontend-to-backend integration.
