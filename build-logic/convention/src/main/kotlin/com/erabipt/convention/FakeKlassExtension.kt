package com.erabipt.convention

import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

open class FakeKlassExtension @Inject constructor(
    objects: ObjectFactory
) {
    private val _packages = mutableSetOf<String>()

    val packages = _packages.toSet()
    fun allowedPackages(vararg allowedPackage: String) {
        _packages.addAll(allowedPackage)
    }
}