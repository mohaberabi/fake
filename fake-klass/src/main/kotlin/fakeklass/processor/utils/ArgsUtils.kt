package fakeklass.processor.utils

import fakeklass.processor.OUTPUT_PACKAGE_ARG
import fakeklass.processor.PACKAGES_ARG
import fakeklass.processor.SOURCE_SET_ARG


internal fun Map<String, String>.fakeKlassSourceSet() = this[SOURCE_SET_ARG]

internal fun Map<String, String>.fakeKlassPackages() = (this[PACKAGES_ARG] ?: "").split(".")
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .toSet()


internal fun Map<String, String>.fakeKlassOutput() =
    this[OUTPUT_PACKAGE_ARG]
        ?.trim()
        ?.takeIf { it.isNotBlank() } ?: "fakeklass"
