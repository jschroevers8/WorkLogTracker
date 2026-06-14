plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    alias(libs.plugins.ktlint)
    kotlin("plugin.serialization")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "webapp.js"
            }
        }
        binaries.executable()
    }

    ktlint {
        ignoreFailures.set(true)
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))

                // Compose Web
                implementation(compose.runtime)
                implementation(compose.html.core)
                implementation(compose.web.core)

                // Ktor JS client
                implementation("io.ktor:ktor-client-js:3.3.2")
                implementation("io.ktor:ktor-client-content-negotiation:3.3.2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.2")

                // Koin
                implementation("io.insert-koin:koin-core:4.1.1")
                implementation("io.insert-koin:koin-compose:4.1.1")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
            }
        }
    }
}
