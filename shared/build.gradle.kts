plugins {
    kotlin("multiplatform")
    alias(libs.plugins.ktlint)
    kotlin("plugin.serialization")
}

group = "worklogtracker"
version = "0.0.1"

kotlin {
    jvm()
    // Toevoegen van JS/Wasm target voor de webapp later indien nodig
    // js(IR) { browser() } 

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