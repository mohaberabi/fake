import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    `kotlin-dsl`
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "io.github.mohaberabi.fakeklassplugin"
version = "0.0.5"
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    google()
    mavenCentral()
}
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.4")
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("fakeKlassPlugin") {
            id = "io.github.mohaberabi.fakeklass.plugin"
            implementationClass = "FakeKlassConventionPlugin"
            displayName = "FakeKlass"
            description = "Generates type-safe fake builders for Kotlin data classes using KSP"
        }
    }
}
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "fake", version.toString())
    pom {
        name = "Fake"
        description = """
         Fake is a KSP-based code generator that creates type-safe fake builders for Kotlin data classes, 
         designed for testing in Kotlin projects
        """.trimIndent()
        inceptionYear = "2025"
        url = "https://github.com/mohaberabi/fake"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "mohaberabi"
                name = "Mohab Erabi"
                url = "https://github.com/mohaberabi"
            }
        }
        scm {
            url = "https://github.com/mohaberabi/fake"
            connection = "scm:git:https://github.com/mohaberabi/fake.git"
            developerConnection = "scm:git:ssh://git@github.com:mohaberabi/fake.git"
        }
    }
}