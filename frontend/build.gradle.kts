import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    api(project(":domain"))

    testImplementation("io.kotest:kotest-runner-junit5:4.1.1")
}
