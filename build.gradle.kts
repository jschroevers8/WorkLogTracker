plugins {
    // Backend
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ktor) apply false
    kotlin("plugin.serialization") version "2.0.0" apply false

    // Frontend
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Multiplatform (for Web/Shared)
    kotlin("multiplatform") version "2.2.21" apply false
    id("org.jetbrains.compose") version "1.11.1" apply false
}
