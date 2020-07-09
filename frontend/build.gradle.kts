import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    application
}

application {
    mainClassName = "pl.karol202.boa.frontend.CLIKt"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<Test> {
        useJUnitPlatform()
    }
    "run"(JavaExec::class) {
        standardInput = System.`in`
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":domain"))
    implementation(project(":common"))

    testImplementation("io.kotest:kotest-runner-junit5:4.1.1")
}
