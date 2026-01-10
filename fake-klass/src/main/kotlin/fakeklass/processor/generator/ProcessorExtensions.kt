package fakeklass.processor.generator

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.Modifier
import fakeklass.processor.constants.PACKAGES_ARG


fun Resolver.getFilesInsidePackages(
    packages: Set<String>
) = getAllFiles().filter { file ->
    val pckge = file.packageName.asString()
    packages.any { trget -> trget.contains(trget) || trget == pckge }
}

fun Sequence<KSFile>.filterIsDataClasses() =
    flatMap { it.declarations }
        .filterIsInstance<KSClassDeclaration>()
        .filter { it.isDataClass() }


fun KSClassDeclaration.isDataClass() = classKind == ClassKind.CLASS && Modifier.DATA in modifiers


internal fun Map<String, String>.fakeKlassPackages() = (this[PACKAGES_ARG] ?: "").split(".")
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .toSet()
