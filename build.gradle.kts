plugins {
    kotlin("jvm") version "1.7.0"
}

allprojects {
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
        maven("https://jitpack.io")
    }
}
