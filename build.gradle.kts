plugins {
    kotlin("jvm") version "1.6.21"
}

allprojects {
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
        maven("https://jitpack.io")
    }
}
