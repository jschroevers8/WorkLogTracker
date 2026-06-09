//package worklogtracker.presentation.project
//
//import worklogtracker.presentation.framework.viewmodel.BaseViewModel
//import worklogtracker.repositories.ProjectRepository
//import kotlinx.serialization.json.Json
//import kotlinx.serialization.json.jsonArray
//import kotlinx.serialization.json.jsonObject
//import kotlinx.serialization.json.jsonPrimitive
//
//class ProjectViewModel(
//    private val projectRepository: ProjectRepository
//) : BaseViewModel<ProjectUiState>(ProjectUiState()) {
//
//    init {
//        loadProjects()
//    }
//
//    fun loadProjects() {
//        launchWithErrorHandling {
//            val projectsJson = projectRepository.getProjects()
//            val json = Json { ignoreUnknownKeys = true }
//            val element = json.parseToJsonElement(projectsJson)
//
//            val projectList = element.jsonArray.map {
//                val obj = it.jsonObject
//                ProjectItem(
//                    id = obj["id"]?.jsonObject?.get("value")?.jsonPrimitive?.content ?: "",
//                    name = obj["name"]?.jsonPrimitive?.content ?: "",
//                    description = obj["description"]?.jsonPrimitive?.content ?: "",
//                    status = obj["status"]?.jsonPrimitive?.content ?: ""
//                )
//            }
//
//            _uiState = uiState.copy(projects = projectList)
//        }
//    }
//
//    override fun setLoading(value: Boolean) {
//        _uiState = uiState.copy(loading = value)
//    }
//
//    override fun setError(message: String?) {
//        _uiState = uiState.copy(error = message)
//    }
//}
