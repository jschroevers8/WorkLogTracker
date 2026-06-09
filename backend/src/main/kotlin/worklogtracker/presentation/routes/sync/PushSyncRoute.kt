package worklogtracker.presentation.routes.sync
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import worklogtracker.application.usecases.sync.AddPendingSyncUseCase
import worklogtracker.infrastructure.plugins.getUserId
import worklogtracker.shared.dto.sync.SyncPushRequest
import worklogtracker.shared.dto.sync.SyncPushResponse
import worklogtracker.shared.dto.sync.SyncResult
import io.ktor.server.response.respond
import java.util.UUID
fun Route.pushSyncRoute(addPendingSyncUseCase: AddPendingSyncUseCase) {
    authenticate {
        post("/api/v1/sync/push") {
            val userId = call.getUserId()
            val request = call.receive<SyncPushRequest>()
            val results = request.items.map {
                try {
                    addPendingSyncUseCase(userId, it.entityType, UUID.fromString(it.entityId), it.operation, it.payload)
                    SyncResult(it.id, "SYNCED", null)
                } catch (e: Exception) {
                    SyncResult(it.id, "FAILED", e.message)
                }
            }
            call.respond(HttpStatusCode.OK, SyncPushResponse(results))
        }
    }
}
