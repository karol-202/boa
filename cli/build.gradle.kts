import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
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
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":frontend"))
    implementation(project(":backend"))
}
