import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
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

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.html.core)
                implementation(compose.runtime)
                implementation("io.ktor:ktor-client-js:3.3.2")
                implementation("io.ktor:ktor-client-content-negotiation:3.3.2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.2")
            }
        }
    }
}

compose {
    html {
        contentServer {
            // port = 8080
        }
    }
}
