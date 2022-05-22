import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Add support for Kotlin.
    kotlin("jvm")
    // Add support for building a CLI application in Java.
    application
    // Add support for generating version number from Git status.
    id("com.palantir.git-version") version "0.15.0"
    // Add support for list licenses of dependencies.
    id("com.github.jk1.dependency-license-report") version "2.0"
}

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.orangain.kastree:ast-psi:0.5.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
    implementation(project(":ktcodeshift-dsl")) // the script definition module
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest()
        }
    }
}

application {
    // Define the main class for the application.
    mainClass.set("ktcodeshift.AppKt")

    applicationName = "ktcodeshift"

    // Inherit current directory when executed by `gradle run`
    tasks.run.get().workingDir = File(System.getProperty("user.dir"))

    // Create .tar.gz instead of .tar
    tasks.distTar {
        compression = Compression.GZIP
        archiveExtension.set("tar.gz")
    }
}

distributions {
    main {
        contents {
            into("") {
                from("../LICENSE", "../README.md")
            }
            into("docs/dependency-license") {
                from(tasks.generateLicenseReport)
            }
        }
    }
}
