import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    application
}

application {
    mainClassName = "pl.karol202.boa.cli.MainKt"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.ExperimentalStdlibApi")
    }
    "run"(JavaExec::class) {
        standardInput = System.`in`
        args("../test/test.boa")
    }
}

dependencies {
    implementation(project(":frontend"))
    implementation(project(":middleend"))
    implementation(project(":interpreter"))

    implementation("com.github.ajalt:clikt:2.6.0")
}
