package com.erabipt.convention.utils

import com.erabipt.convention.FakeKlassExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureKspForFake(
    fakeKlass: FakeKlassExtension,
) {
    extensions.configure<KspExtension>() {
        configureKspArgs(allowedPackages = fakeKlass.packages)
    }
}

private fun KspExtension.configureKspArgs(
    allowedPackages: Set<String>
) {
    val packages = allowedPackages.joinToString(",")
    val outPutPackages = allowedPackages.joinToString(",") { "${it}.fakes" }
    arg("fake.packages", packages)
    arg("fake.outputPackage", outPutPackages)
}