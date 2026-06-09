plugins {
    kotlin("jvm")
    alias(libs.plugins.ktlint)
    kotlin("plugin.serialization")
}

group = "worklogtracker"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(24)
}