import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Add support for Kotlin.
    kotlin("jvm")
    // Add support for building a CLI application in Java.
    application
    // Add support for generating document.
    id("org.jetbrains.dokka")
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
    implementation("info.picocli:picocli:4.7.4")
    implementation(project(":ktcodeshift-dsl")) // the script definition module
    implementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("com.github.orangain.ktast:ast-psi:0.9.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
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

tasks.jar {
    manifest {
        attributes(
            mapOf("Implementation-Version" to project.version.toString())
        )
    }

    // Include LICENSE file in jar.
    into("META-INF") {
        from("../LICENSE")
    }
}

application {
    // Define the main class for the application.
    mainClass.set("ktcodeshift.AppKt")

    applicationName = "ktcodeshift"
}

// Inherit current directory when executed by `gradle run`
tasks.run.get().workingDir = File(System.getProperty("user.dir"))

// Create .tar.gz instead of .tar
tasks.distTar {
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
}
