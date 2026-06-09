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
}