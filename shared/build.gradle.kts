plugins {
    kotlin("multiplatform")
    alias(libs.plugins.ktlint)
    kotlin("plugin.serialization")
}

//TODO misschien nog even kijken naar de packages
group = "worklogtracker"
version = "0.0.1"

kotlin {
    jvm()

    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
    }
}