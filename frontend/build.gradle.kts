plugins {
    kotlin("jvm") version "1.3.72"
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":domain"))
    implementation(project(":common"))

    testImplementation("io.kotest:kotest-runner-junit5:4.1.1")
}
