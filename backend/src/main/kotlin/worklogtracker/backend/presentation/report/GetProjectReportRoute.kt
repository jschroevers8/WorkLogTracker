package worklogtracker.backend.presentation.report

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import worklogtracker.backend.domain.valueobjects.project.ProjectId
import worklogtracker.backend.domain.repositories.TaskRepositoryInterface
import worklogtracker.shared.dto.report.ProjectReportResponse
import io.ktor.server.response.respond

//TODO
fun Route.getProjectReportRoute(taskRepository: TaskRepositoryInterface) {
    authenticate {
        get("/api/reports/project/{projectId}") {
            val projectId = ProjectId(call.parameters["projectId"]?.toInt() ?: error("Project ID required"))
            val tasks = taskRepository.findByProject(projectId)
            val completed = tasks.count { it.status.name == "COMPLETED" }
            val estimated = tasks.sumOf { it.estimatedHours }.toDouble()
            val actual = tasks.sumOf { it.actualHours }.toDouble()

            call.respond(HttpStatusCode.OK,
                ProjectReportResponse(
                    projectId.value,
                    "Project ${projectId.value}",
                    tasks.size,
                    completed,
                    estimated,
                    actual,
                    tasks.firstOrNull()?.status?.name ?: "UNKNOWN",
                    if (tasks.isNotEmpty()) (completed.toDouble() / tasks.size) * 100 else 0.0
                )
            )
        }
    }
}
