plugins {
    kotlin("jvm") version "2.2.20"
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
}

group = "org.mohaberabi.testideplugin"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

