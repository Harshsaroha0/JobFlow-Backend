# JobFlow — Job Application Management Platform

JobFlow is a full-stack portfolio project for organizing job-search activity. It combines a Java/Spring Boot backend, MySQL persistence, and a plain HTML/CSS/JavaScript frontend.

**Live demo**
- Frontend: https://jobflow-frontend-production-cacf.up.railway.app
- Backend health: https://jobflow-monolith-production.up.railway.app/actuator/health
- Repository: https://github.com/Harshsaroha0/JobFlow-Backend

> **Deployment note:** The original microservices implementation is preserved in this repository. For the current low-cost Railway deployment, a separate consolidated Spring Boot application reuses the existing service source packages. The deployment is a portfolio/demo environment; validate each user-facing workflow before relying on it as a production service.

## Features

- Job management and job search workflows
- Job application tracking
- Resume management and file upload/storage
- Skills and user-skill management
- Notifications
- Resume text analysis (skill and section extraction)
- Job-description text analysis (recognized skills, responsibilities, and qualifications)
- Browser-based frontend using HTML, CSS, and JavaScript

The AI-related analysis currently uses local Java text-processing/rule-based extraction; it is not a generative LLM integration.

## Technology stack

- **Language/runtime:** Java 21
- **Backend:** Spring Boot 3.5.4, Spring Web, Spring Data JPA, Bean Validation
- **Database:** MySQL
- **Frontend:** HTML, CSS, JavaScript
- **Build:** Maven
- **Deployment:** Railway, Docker
- **Original distributed architecture:** Spring Cloud Config, Eureka service discovery, Spring Cloud Gateway

## Deployment architecture

### Current Railway demo

```text
Browser
   |
   v
HTML / CSS / JavaScript frontend (Railway)
   |
   | HTTPS API requests
   v
Consolidated Spring Boot backend (Railway, port 8080)
   |
   v
MySQL database (Railway)

Persistent volume -> uploaded resume files
```

The consolidated application compiles the Java source packages from the existing user, job, resume, application-tracking, skill, notification, and AI service folders into one Spring Boot process. This reduces the number of deployed services for the demo while keeping the original service directories available for local microservices development.

The original architecture remains in the repository:

- `config-server/` — centralized configuration server
- `eureka-server/` — service discovery
- `api-gateway/` — gateway routing
- `user-service/` — user operations
- `job-service/` — job operations
- `resume-service/` — resume operations and file handling
- `applicationTracking-service/` — application tracking
- `skill-service/` — skills and user-skill operations
- `notification-service/` — notification operations
- `ai-service/` — resume and job-description text analysis
- `jobflow-config/` — configuration used by the original microservices
- `frontend/` — browser UI
- `jobflow-monolith/` — consolidated deployment entry point and build configuration

## Repository structure

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
├── jobflow-monolith/
│   ├── pom.xml
│   └── src/main/
├── notification-service/
├── resume-service/
├── skill-service/
├── user-service/
├── Dockerfile
├── .gitignore
└── README.md
```

## Run the consolidated backend locally

**Prerequisites:** JDK 21, Maven, and a running MySQL instance.

1. Clone the repository and enter its root directory.
2. Create a database named `jobflow`.
3. Set database environment variables if your local MySQL credentials differ from the defaults:
   - `MYSQLHOST` (default `localhost`)
   - `MYSQLPORT` (default `3306`)
   - `MYSQLDATABASE` (default `jobflow`)
   - `MYSQLUSER` (default `root`)
   - `MYSQLPASSWORD` (default empty)
4. Build and run:

```bash
mvn -f jobflow-monolith/pom.xml clean package
java -jar jobflow-monolith/target/jobflow-monolith-1.0.0.jar
```

The backend listens on port `8080` by default. The `PORT` environment variable can override this.

Optional configuration:
- `FRONTEND_ORIGIN` — allowed browser origin for API CORS.
- `FILE_STORAGE_LOCATION` — directory for uploaded resumes (default `/data/resumes` in the deployment configuration).

## Run the frontend locally

Serve the `frontend/` directory with a local static web server and open its index page. The API base URL is configured in `frontend/js/config.js`. For local development, point `API_BASE_URL` to `http://localhost:8080`; the deployment branch uses the Railway backend URL.

## Health check and API routes

- Health check: [`/actuator/health`](https://jobflow-monolith-production.up.railway.app/actuator/health)
- Job route prefix: `/api/jobs/**`
- User route prefix: `/api/users/**`
- Resume route prefix: `/api/resumes/**`
- Application tracking prefix: `/api/applications/**`
- Skills prefix: `/api/skills/**` and `/api/user-skills/**`
- Notifications prefix: `/api/notifications/**`
- AI analysis prefix: `/api/ai/**`

These are route prefixes, not an exhaustive endpoint contract. Check the relevant controllers for supported methods, payloads, authentication requirements, and response formats. Use Postman or browser developer tools to verify individual flows.

## Original microservices setup

Each original backend component has its own Maven project. For local microservices development, start MySQL, then the Config Server and Eureka Server, then the business services, and finally the API Gateway. Review `jobflow-config/` and each service's `application.yaml` before starting: replace machine-specific paths and local-only configuration with values suitable for your environment. The consolidated deployment does not require separate Config Server or Eureka processes to serve its API.

## Keep the repository clean and secure

- Never commit database passwords, access tokens, API keys, or `.env` files.
- Keep generated Maven output (`target/`), IDE metadata, logs, and local resume uploads out of Git.
- Use Railway environment variables for deployment-specific values.
- Rotate any credentials that have previously been committed to version control.
- Review authentication, authorization, CORS, file validation, and database configuration before using this as a production service.

## Current status and next steps

- [x] Consolidated Spring Boot artifact builds and starts on Railway.
- [x] Backend connects to Railway MySQL at startup.
- [x] Railway health endpoint is configured.
- [ ] Manually smoke-test each frontend workflow against the deployed API.
- [ ] Add automated unit and integration tests.
- [ ] Verify authentication/authorization and upload/download behavior in the deployed environment.
- [ ] Remove or rotate any previously committed credentials in the original microservice configuration.

## Author

**Harsh Saroha** — Java Backend Engineer aspirant

- GitHub: https://github.com/Harshsaroha0
- Portfolio: https://harshsaroha0.github.io

Built to practice Java backend development, REST APIs, Spring Boot, JPA, MySQL, microservice design, and deployment.
