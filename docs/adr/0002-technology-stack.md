# ADR-0002: Technology stack (lean proof of concept)

- **Status:** Accepted (cloud target still open)
- **Date:** 2026-11-22

## Context
Proof of concept on a limited budget. Minimise dependencies, generated code and AI token use.

## Decision
| Concern | Choice |
|---|---|
| Language | Java 21 |
| Build | Maven |
| HTTP | JDK built-in `com.sun.net.httpserver.HttpServer` (no framework) |
| Tests | JUnit 5 only; one test per AC, named after the AC ID |
| Packaging | Docker image |
| CI/CD | GitHub Actions |
| Cloud | Open: Google Cloud Run (recommended) or Azure Container Apps |

## Consequences
- Near-zero dependencies; small, fast build.
- No OpenAPI contract or Cucumber; traceability comes from test names.
- Can move to Spring Boot / Cucumber later via a new ADR if the PoC grows.

## Alternatives considered
- Spring Boot + Cucumber + OpenAPI: richer, but far more code and tokens for a PoC.
- Javalin: small, but still a dependency the JDK server makes unnecessary.
