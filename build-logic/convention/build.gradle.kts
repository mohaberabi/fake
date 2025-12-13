import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    `kotlin-dsl`
}

group = "io.github.mohaberabi.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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