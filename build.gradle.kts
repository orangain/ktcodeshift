plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.dokka") version "2.0.0"
    // Add support for generating version number from Git status.
    id("com.palantir.git-version") version "3.2.0"
}

val gitVersion: groovy.lang.Closure<String> by extra

allprojects {
    group = "com.github.orangain.ktcodeshift"
    version = gitVersion()

    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
        maven("https://jitpack.io")
    }
}
