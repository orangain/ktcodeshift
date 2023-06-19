plugins {
    kotlin("jvm") version "1.8.22"
    id("org.jetbrains.dokka") version "1.8.20"
}

allprojects {
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
        maven("https://jitpack.io")
    }
}
