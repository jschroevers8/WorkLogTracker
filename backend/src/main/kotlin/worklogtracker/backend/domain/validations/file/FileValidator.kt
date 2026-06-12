package worklogtracker.backend.domain.validations.file

import worklogtracker.backend.domain.exceptions.InvalidFileTypeException
import worklogtracker.backend.domain.exceptions.FileSizeExceededException

class FileValidator {
    
    companion object {
        private val ALLOWED_EXTENSIONS = setOf("pdf", "docx", "xlsx", "jpg", "jpeg", "png", "gif", "txt")
        private const val MAX_FILE_SIZE = 10 * 1024 * 1024  // 10MB
    }
    
    fun validateFileType(fileName: String) {
        val extension = fileName.substringAfterLast('.', "").lowercase()
        if (extension !in ALLOWED_EXTENSIONS) {
            throw InvalidFileTypeException(fileName)
        }
    }
    
    fun validateFileSize(sizeInBytes: Int) {
        if (sizeInBytes > MAX_FILE_SIZE) {
            throw FileSizeExceededException(sizeInBytes)
        }
    }
}

