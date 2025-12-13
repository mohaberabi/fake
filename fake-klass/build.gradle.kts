plugins {
    kotlin("jvm") version "2.2.20"
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "io.github.mohaberabi"
version = "0.0.6"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.devtools.ksp:symbol-processing-api:2.2.20-2.0.2")
    implementation("com.squareup:kotlinpoet:1.18.1")
    implementation("com.squareup:kotlinpoet-ksp:1.18.1")
}

tasks.test { useJUnitPlatform(); }
kotlin { jvmToolchain(21); }



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
