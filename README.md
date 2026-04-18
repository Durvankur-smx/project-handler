Enterprise Project & Task Management Backend

This is an enterprise-style Project and Task Management backend system built using Java and Spring Boot. The project is designed to simulate real-world backend behavior such as ownership enforcement, lifecycle control, authorization without security frameworks, and safe data handling.

The focus of this project is backend correctness, maintainability, and predictable system behavior rather than adding excessive frameworks or buzzwords.

PROJECT GOALS

* Demonstrate enterprise backend design principles
* Enforce authorization and ownership at the service layer
* Handle data lifecycle safely using archive and restore instead of hard deletes
* Build clean REST APIs with pagination, sorting, and DTO-based contracts
* Ensure the application never crashes and always fails gracefully
* Show complete backend-to-UI flow with a minimal and resilient UI


ARCHITECTURE OVERVIEW

The application follows a layered architecture with clear separation of responsibilities.

Flow:
Controller → Service → Repository → Database

Key architectural decisions:

* Controllers are thin and contain no business or authorization logic
* Service layer is the single authority for validation and authorization
* Repositories are responsible only for data access
* DTOs are used to prevent entity exposure and unsafe client input
* Global exception handling ensures predictable system behavior

TECH STACK

Backend:
Java 17
Spring Boot
Spring MVC
Spring Data JPA

Database:
MySQL

UI:
Thymeleaf
Bootstrap

Tools:
Postman (API testing)
IntelliJ IDEA
Git

DOMAIN MODEL

User:

* Represents an application user
* Used for ownership and authorization logic
* No authentication framework involved

Project:

* Owned by a single user
* Has ACTIVE or ARCHIVED state
* Archived projects are read-only

Task:

* Belongs to a project
* Inherits authorization from its parent project
* Lifecycle controlled through explicit states


AUTHORIZATION AND SECURITY DESIGN (WITHOUT FRAMEWORKS)

This project intentionally avoids authentication frameworks and focuses on business-level authorization.

Authorization rules:

* Only project owners can modify, archive, or restore projects
* Only project owners can create or modify tasks under the project
* Archived projects and tasks are immutable
* User state is validated before every mutating operation

Reasoning:
Authorization rules should remain secure regardless of how authentication is implemented (UI, API, or batch jobs). This ensures the core business logic stays safe even if the entry point changes.


DATA LIFECYCLE MANAGEMENT

No hard deletes are used in the system.

Behavior:

* Projects and tasks are archived instead of deleted
* Archived data is excluded from default queries
* Restore operations are explicit and controlled

Benefits:

* Prevents accidental data loss
* Preserves historical context
* Matches real enterprise system behavior

REST API DESIGN

The backend exposes RESTful endpoints following standard HTTP semantics.

Characteristics:

* Proper HTTP methods (GET, POST, PUT)
* Meaningful status codes (200, 201, 400, 403, 404)
* DTO-based request and response models
* Pagination and sorting support for list endpoints

All APIs are tested using Postman.


UI PAGES (MINIMAL AND RESILIENT)

The UI is intentionally minimal and exists only to demonstrate end-to-end backend flow.

Pages:

* Landing Page: Entry point with navigation
* Dashboard: Lists owned projects with summary information
* Project Page: Displays project details and associated tasks
* Task View: Manage task status and priority

Error handling:

* Custom error pages for 403 (Access Denied)
* Custom error pages for 404 (Resource Not Found)
* Custom error pages for 500 (Internal Server Error)

The application never crashes or exposes internal technical details to the user.

EXCEPTION HANDLING

All exceptions are handled centrally using a global exception handler.

Design principles:

* No stack traces exposed to users
* Consistent and safe error messages
* Predictable fallback UI behavior
* Clear separation between technical errors and user-facing messages


TESTING

* APIs tested using Postman
* Edge cases covered:

  * Unauthorized access attempts
  * Invalid state transitions
  * Operations on archived entities
  * Missing or invalid input

KEY LEARNINGS AND TAKEAWAYS

This project demonstrates:

* Ownership and authorization thinking
* Enterprise-grade data safety practices
* Clean REST API design
* Defensive programming mindset
* Clear separation of concerns
* Predictable and maintainable backend behavior


FUTURE ENHANCEMENTS (OPTIONAL)

* Authentication layer using JWT or Spring Security
* Role-based access control
* Audit logging
* Integration testing

These can be added without changing core business logic due to the current design.



AUTHOR

Samyak Kedari
Backend-focused Java Developer
MIT ADT University, Pune
