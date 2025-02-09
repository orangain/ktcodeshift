plugins {
    kotlin("jvm") version "2.1.10"
    id("org.jetbrains.dokka") version "1.9.20"
    // Add support for generating version number from Git status.
    id("com.palantir.git-version") version "3.1.0"
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
