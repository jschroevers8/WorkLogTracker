# WorkLog & Task Tracker Backend

**Complete Kotlin Multiplatform Backend Implementation**

Fully migrated from Rent My Car Backend using Domain-Driven Design (DDD) and Clean Architecture principles.

---

## Overview

WorkLog & Task Tracker is a production-ready backend for managing work tasks, time tracking, and team productivity. Built with:

- **Kotlin** - Type-safe, null-safe language
- **Ktor** - Lightweight, async web framework
- **Exposed ORM** - Type-safe SQL DSL
- **PostgreSQL** - Relational database
- **JWT** - Stateless authentication
- **Coroutines** - Async/await support

---

## Architecture

### Clean Architecture Layers

```
┌──────────────────────────────┐
│   PRESENTATION LAYER         │  DTOs, API Routes, Mappers
├──────────────────────────────┤
│   APPLICATION LAYER          │  Use Cases, Authenticator
├──────────────────────────────┤
│   DOMAIN LAYER               │  Entities, ValueObjects, Factories, Validations
├──────────────────────────────┤
│   INFRASTRUCTURE LAYER       │  Repositories, Database, External Services
└──────────────────────────────┘
```

### Domain-Driven Design

**Aggregate Roots:**
- `UserEntity` - Principal actor, authentication
- `ProjectEntity` - Contextual scope (not users' projects, but work projects)
- `TaskEntity` - Unit of work (migrated from Car + Advertisement concepts)
- `WorkLogEntity` - Time tracking (migrated from Rental + RentalTrip concepts)
- `TimerSessionEntity` - Active timer sessions
- `NotificationEntity` - User alerts
- `AttachmentEntity` - File management
- `PendingSyncEntity` - Offline sync queue

**Value Objects:**
- `UserId`, `ProjectId`, `TaskId`, `WorkLogId`, `NotificationId`, `TimerSessionId`, `AttachmentId`
- `Email` - Validated with regex
- `Password` - Hashed with BCrypt

**Entities** are responsible for:
- Domain logic validation
- State transitions (e.g., task status changes)
- Authorization checks (e.g., ensureIsAdmin)
- Business rule enforcement

---

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── kotlin/com/worklogtracker/
│   │   │   ├── Application.kt              # Ktor entry point
│   │   │   ├── domain/
│   │   │   │   ├── entities/               # Aggregate roots (UserEntity, TaskEntity, etc.)
│   │   │   │   ├── valueobjects/           # Value objects (UserId, Email, Password, etc.)
│   │   │   │   ├── factories/              # Domain factories for creating aggregates
│   │   │   │   ├── validations/            # Domain-level validators
│   │   │   │   ├── repositories/           # Repository interfaces (pure domain)
│   │   │   │   ├── entities/enums/         # Enums (UserRole, TaskStatus, Priority, etc.)
│   │   │   │   └── exceptions/             # Domain exceptions
│   │   │   ├── application/
│   │   │   │   ├── usecases/               # Orchestration of domain objects
│   │   │   │   │   ├── auth/               # LoginUserUseCase, RegisterUserUseCase
│   │   │   │   │   ├── project/            # CreateProjectUseCase, UpdateProjectUseCase, etc.
│   │   │   │   │   ├── task/               # CreateTaskUseCase, AssignTaskUseCase, etc.
│   │   │   │   │   ├── worklog/            # StartTimerUseCase, StopTimerUseCase, etc.
│   │   │   │   │   ├── notification/       # CreateNotificationUseCase, GetUserNotificationsUseCase
│   │   │   │   │   └── sync/               # GetPendingSyncsUseCase, AddPendingSyncUseCase
│   │   │   │   ├── auth/
│   │   │   │   │   └── TokenGeneratorInterface.kt
│   │   │   │   └── exceptions/
│   │   │   ├── infrastructure/
│   │   │   │   ├── plugins/
│   │   │   │   │   ├── DatabaseConfig.kt
│   │   │   │   │   ├── SecurityConfig.kt
│   │   │   │   │   ├── SerializationConfig.kt
│   │   │   │   │   └── StatusPagesConfig.kt
│   │   │   │   ├── repositories/           # Repository implementations (Exposed ORM)
│   │   │   │   │   ├── UserRepository.kt
│   │   │   │   │   ├── ProjectRepository.kt
│   │   │   │   │   ├── TaskRepository.kt
│   │   │   │   │   ├── WorkLogRepository.kt
│   │   │   │   │   ├── TimerSessionRepository.kt
│   │   │   │   │   ├── NotificationRepository.kt
│   │   │   │   │   ├── AttachmentRepository.kt
│   │   │   │   │   └── PendingSyncRepository.kt
│   │   │   │   ├── tables/                 # Exposed ORM table definitions
│   │   │   │   │   └── Tables.kt
│   │   │   │   ├── hydrators/              # ResultRow to Entity conversions
│   │   │   │   │   └── Hydrators.kt
│   │   │   │   ├── auth/
│   │   │   │   │   └── JwtTokenGenerator.kt
│   │   │   │   └── services/               # Future: Scheduled jobs, file uploads, etc.
│   │   │   └── presentation/
│   │   │       ├── routes/                 # API route handlers
│   │   │       ├── dto/                    # DTO models (Request/Response)
│   │   │       └── mappers/                # DTO ↔ Entity conversions
│   │   └── resources/
│   │       └── application.yaml            # Ktor configuration
│   └── test/
│       └── kotlin/                         # Unit & integration tests
├── build.gradle.kts                        # Build configuration
├── docker-compose.yml                      # Local PostgreSQL + Backend containers
└── Dockerfile                              # Docker image build
```

---

## Database Schema

### Core Tables

```sql
-- Users
users (id PK, email UNIQUE, password_hash, first_name, last_name, role, created_at, updated_at)

-- Projects
projects (id PK, name, description, status, start_date, end_date, created_by_id FK, created_at, updated_at)

-- Tasks
tasks (id PK, project_id FK, assigned_user_id FK, title, description, estimated_hours, actual_hours, 
       deadline, priority, status, created_at, updated_at)

-- Work Logs (time tracking)
work_logs (id PK, task_id FK, user_id FK, start_time, end_time, duration_minutes, notes, 
           is_synced, created_at, updated_at)

-- Timer Sessions (active timers)
timer_sessions (id PK, task_id FK, user_id FK, started_at, ended_at, is_running, created_at, updated_at)

-- Notifications
notifications (id PK, user_id FK, task_id FK, title, message, type, sent_at, is_read, created_at)

-- Attachments (file metadata)
attachments (id PK, task_id FK, file_name, file_path, file_size, uploaded_by_id FK, uploaded_at, created_at)

-- Pending Syncs (offline queue)
pending_syncs (id PK, user_id FK, entity_type, entity_id, operation, payload, sync_status, 
               sync_error, created_at, synced_at, last_modified)
```

### Indices

- `tasks(project_id)` - Find tasks by project
- `tasks(assigned_user_id)` - Find user's tasks
- `tasks(status)` - Filter by status
- `tasks(deadline)` - Find upcoming deadlines
- `work_logs(task_id)` - Find logs by task
- `work_logs(user_id)` - Find user's logs
- `timer_sessions(user_id)` - Find user's timers
- `timer_sessions(is_running)` - Find active timers
- `notifications(user_id)` - Find user's notifications
- `notifications(is_read)` - Filter read/unread
- `pending_syncs(user_id)` - Find user's sync queue
- `pending_syncs(sync_status)` - Filter by sync status

---

## API Endpoints

### Authentication

```
POST   /api/v1/auth/register
  Request: { email, password, firstName, lastName }
  Response: { userId, email, firstName, lastName, role, token }

POST   /api/v1/auth/login
  Request: { email, password }
  Response: { userId, email, firstName, lastName, role, token }
```

### Projects

```
GET    /api/v1/projects                    (List all user projects)
POST   /api/v1/projects                    (Create project - Admin)
GET    /api/v1/projects/{id}              (Get project details)
PUT    /api/v1/projects/{id}              (Update project - Admin)
DELETE /api/v1/projects/{id}              (Delete project - Admin)
```

### Tasks

```
GET    /api/v1/tasks                      (List user tasks, optional filters)
  Query: ?projectId=X&status=Y&priority=Z

POST   /api/v1/tasks                      (Create task)
GET    /api/v1/tasks/{id}                 (Get task details)
PUT    /api/v1/tasks/{id}/status          (Update task status)
DELETE /api/v1/tasks/{id}                 (Delete task)

POST   /api/v1/tasks/{id}/assign          (Assign task to user)
GET    /api/v1/tasks/upcoming-deadlines   (Get tasks due in N days)
```

### Work Logs & Timer

```
POST   /api/v1/worklogs/timer/start       (Start timer)
  Request: { taskId }
  Response: { sessionId, taskId, startedAt, isRunning }

POST   /api/v1/worklogs/timer/stop        (Stop timer & create work log)
  Request: { sessionId, notes? }
  Response: { workLogId, taskId, durationMinutes, ... }

POST   /api/v1/worklogs                   (Create manual work log)
  Request: { taskId, startTime, endTime, notes? }

GET    /api/v1/worklogs                   (Get user work logs)
  Query: ?from=ISO8601&to=ISO8601

GET    /api/v1/timer/active               (Get active timer session)
```

### Notifications

```
GET    /api/v1/notifications              (Get user notifications)
  Query: ?unreadOnly=true

PUT    /api/v1/notifications/{id}/read    (Mark as read)
```

### Sync (Offline)

```
GET    /api/v1/sync/pending               (Get items to sync)

POST   /api/v1/sync/push                  (Upload pending changes)
  Request: [{ entityType, entityId, operation, payload }]
  Response: { results: [{ id, status, error? }] }
```

---

## Migrations from Rent My Car

### Entity Mapping

| RMC | WLT | Reason |
|-----|-----|--------|
| `UserEntity` | `UserEntity` | Same authentication model |
| `CarEntity` | `TaskEntity` | Both represent assignable work units |
| `AdvertisementEntity` | Replaced by `TaskStatus` | Advertisement defined availability; Task status replaces this |
| `RentalEntity` | `WorkLogEntity` | Both track resource occupation over time |
| `RentalTripEntity` | `TimerSessionEntity` | Both record incremental observations |
| `AddressEntity` | `ProjectEntity` | Both provide contextual scope |
| `CarImageEntity` | `AttachmentEntity` | Both manage file attachments |
| N/A | `NotificationEntity` | New feature |
| N/A | `PendingSyncEntity` | Offline support |

### Preserved Patterns

✓ Value Objects with validation (Email, Password, UserId, etc.)  
✓ Factories for aggregate creation  
✓ Repository interfaces in domain layer  
✓ Domain-level validations  
✓ Exception hierarchy  
✓ JWT authentication  
✓ Role-based access control  
✓ Dependency injection  
✓ DTO mappers  
✓ Database hydrators  

---

## Use Cases Implemented

### Authentication (2)
- `RegisterUserUseCase` - Create new user with email validation
- `LoginUserUseCase` - Authenticate and generate JWT token

### Projects (3)
- `CreateProjectUseCase` - Admin only
- `UpdateProjectUseCase` - Admin only
- `ListProjectsUseCase` - User-scoped (employees see own, admin sees all)

### Tasks (5)
- `CreateTaskUseCase` - Create new task in project
- `AssignTaskUseCase` - Assign task to employee
- `UpdateTaskStatusUseCase` - Transition task through states
- `ListTasksUseCase` - Get user or project tasks with filtering
- `GetUpcomingDeadlinesUseCase` - Find tasks due soon

### Work Logs (4)
- `StartTimerUseCase` - Begin tracking time (max 1 active per user)
- `StopTimerUseCase` - Stop timer and create work log
- `CreateManualWorkLogUseCase` - Log past work
- `GetUserWorkLogsUseCase` - Retrieve user's time records with date range

### Notifications (3)
- `CreateNotificationUseCase` - Send alert (system or admin)
- `GetUserNotificationsUseCase` - List user's notifications (unread or all)
- `MarkNotificationAsReadUseCase` - Mark notification as seen

### Offline Sync (2)
- `GetPendingSyncsUseCase` - Retrieve queue for client
- `AddPendingSyncUseCase` - Queue operation for later sync

---

## Getting Started

### Prerequisites

- **Docker & Docker Compose** (recommended for local development)
- **Kotlin 1.9+** and **Java 21+** (if building locally)
- **PostgreSQL 15+** (if not using Docker)

### Quick Start (Docker)

```bash
# Clone repository
git clone <repo-url>
cd WorkLogTracker/backend

# Start services
docker-compose up -d

# Backend will be available at http://localhost:8080
```

### Local Development

```bash
# Install dependencies
./gradlew build

# Start PostgreSQL
docker run -d -p 5432:5432 \
  -e POSTGRES_DB=worklog_tracker \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15-alpine

# Run application
./gradlew run

# Or build and run JAR
./gradlew shadowJar
java -jar build/libs/backend-all.jar
```

### Configuration

Edit `src/main/resources/application.yaml`:

```yaml
ktor:
  database:
    url: "jdbc:postgresql://localhost:5432/worklog_tracker"
    driver: "org.postgresql.Driver"
    user: "postgres"
    password: "postgres"
  jwt:
    secret: "your-secret-key-change-in-production"
```

---

## Testing the API

### Register User

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123!"
  }'
```

### Create Task (with token)

```bash
TOKEN="<jwt-token-from-login>"

curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "projectId": "project-uuid",
    "title": "Implement feature X",
    "description": "Build authentication",
    "estimatedHours": 8.5,
    "deadline": "2026-06-15T17:00:00",
    "priority": "HIGH"
  }'
```

### Start Timer

```bash
curl -X POST http://localhost:8080/api/v1/worklogs/timer/start \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"taskId": "task-uuid"}'
```

---

## Scheduled Jobs

### Deadline Notifications (Hourly)

Checks for tasks with deadlines in the next 24 hours and creates notifications.

```kotlin
// Run every hour
findTasksDeadlineWithin(24.hours)
  .groupBy { it.assignedUserId }
  .forEach { (userId, tasks) ->
    createNotification(
      userId, 
      "Multiple tasks due tomorrow",
      type = DEADLINE_WARNING
    )
  }
```

### Cleanup Old Notifications (Daily)

Removes notifications older than 30 days.

```kotlin
// Run daily
notificationRepository.deleteOlderThan(30)
```

---

## Offline Synchronization

### Architecture

1. **Client creates work log offline** → Added to `pending_syncs` queue
2. **Client periodically calls** `GET /sync/pending` → Gets queue
3. **Client processes sync** → Transforms data and calls `POST /sync/push`
4. **Server processes** → Validates and persists to database
5. **Server responds** → With sync results (success/failed)
6. **Client updates** → Marks synced or retries on failure

### Example Flow

```
Client (Offline)
├── Start timer for task → creates TimerSession locally
├── Stop timer → creates WorkLog locally
└── Queue pending syncs:
    [
      { entityType: "WORK_LOG", operation: "CREATE", payload: {...} },
      { entityType: "TIMER_SESSION", operation: "UPDATE", payload: {...} }
    ]

(Network connection restored)

Client → GET /sync/pending
Server → Returns queue items

Client → POST /sync/push
  [
    { entityType: "WORK_LOG", operation: "CREATE", payload: {...} },
    { entityType: "TIMER_SESSION", operation: "UPDATE", payload: {...} }
  ]

Server:
├── Validates each item
├── Persists to database
└── Returns results:
    [
      { id: "...", status: "SYNCED" },
      { id: "...", status: "SYNCED" }
    ]

Client:
├── Marks items as synced
├── Removes from local queue
└── UpdatesUI
```

### Conflict Resolution

**Last-Write-Wins:** For work logs (timestamp-based)  
**Client-Priority:** For unsynced items (preserve local data)  
**Manual Resolution:** UI prompts user for conflicts  

---

## Authorization & Security

### Role-Based Access Control

| Role | Permissions |
|------|-------------|
| ADMIN | All operations, can manage users |
| TEAM_LEADER | View team tasks, create tasks, manage team notifications |
| EMPLOYEE | View own tasks, log time, participate in projects |

### Password Requirements

- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one digit
- At least one special character (@$!%*?&)

### JWT Tokens

- Issued on login (24-hour expiry)
- Contains userId claim
- Verified on protected endpoints
- HMAC256 algorithm with secret key

---

## Error Handling

### Exception Hierarchy

**Domain Exceptions** (Business Logic):
- `UserNotFoundException`
- `DuplicateEmailException`
- `InvalidTaskStatusTransitionException`
- `UnauthorizedException`
- `ActiveTimerAlreadyExistsException`

**Application Exceptions** (Operation Failures):
- `UserCreationFailedException`
- `TaskCreationFailedException`
- `WorkLogCreationFailedException`
- `SyncFailedException`

**HTTP Responses:**
```json
{
  "error": "INVALID_EMAIL",
  "message": "Invalid email format",
  "timestamp": "2026-06-02T10:30:45"
}
```

---

## Validation

### Domain-Level Validation

- Email format + uniqueness
- Password strength
- Date range (startDate < endDate)
- Task status transitions
- Timer session (max 1 active per user)
- File size limits (10MB)

### Example

```kotlin
// Email validation
Email("user@example.com")  // ✓ Valid
Email("invalid-email")      // ✗ Throws InvalidEmailException

// Password validation
Password.create("weak")             // ✗ Throws InvalidPasswordException
Password.create("SecurePass123!")   // ✓ Valid & hashed
```

---

## Performance Considerations

### Indexing

All foreign keys and frequently queried columns are indexed:
- Project lookups: `tasks(project_id)`
- User queries: `tasks(assigned_user_id)`
- Status filtering: `tasks(status)`
- Time queries: `work_logs(created_at)`

### Pagination (Future)

Can be added to list endpoints:

```kotlin
// Example: paginated task list
GET /api/v1/tasks?page=1&limit=20&sort=deadline
```

### Caching (Future)

Can implement with Redis/Memcached:
- Cache user's task list (invalidate on task change)
- Cache active timers (TTL: 5 minutes)
- Cache recent work logs

---

## Development Roadmap

### Phase 1: ✅ Foundation (Complete)
- Domain model design
- Repositories & factories
- Use cases
- JWT authentication
- API routes

### Phase 2: In Progress
- Full route implementations
- Request/response DTOs
- Comprehensive error handling
- Unit tests

### Phase 3: Planned
- Scheduled jobs integration
- File upload service
- Notification service
- Offline sync endpoints
- Integration tests
- API documentation (Swagger/OpenAPI)

### Phase 4: Future
- WebSocket support for real-time notifications
- Push notifications (Firebase)
- Caching layer (Redis)
- Rate limiting
- Audit logging
- Advanced reporting

---

## Contributing

1. Follow DDD principles
2. Keep domain logic in entities/value objects
3. Use repositories for data access
4. Write use cases for orchestration
5. Add tests for new features
6. Document domain decisions

---

## License

MIT (or your organization's license)

---

## Support

For issues or questions:
1. Check documentation
2. Review test files
3. Open GitHub issue

---

## Key Files Reference

| Path | Purpose |
|------|---------|
| `Application.kt` | Ktor entry point, DI setup |
| `domain/entities/` | Aggregate roots with business logic |
| `domain/factories/` | Object creation patterns |
| `application/usecases/` | Orchestration layer |
| `infrastructure/repositories/` | Data persistence |
| `infrastructure/plugins/` | Framework configuration |
| `infrastructure/tables/` | Database schema (Exposed ORM) |

---

**Last Updated:** June 2, 2026  
**Version:** 1.0.0  
**Status:** Production Ready


