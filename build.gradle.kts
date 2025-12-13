plugins {
    kotlin("jvm") version "2.2.20"
    id("com.google.devtools.ksp") version "2.2.20-2.0.4"
}

group = "org.mohaberabi.testideplugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
    ksp(project(":fake-klass"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

ksp {
    arg(
        "fake.outputPackage",
        "org.mohaberabi.testideplugin.com.mohaberabi.models.fakes"
    )
    arg(
        "fake.packages",
        "org.mohaberabi.testideplugin.com.mohaberabi.models"
    )
}
