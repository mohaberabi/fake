package fakeklass.processor.utils

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.Modifier


internal fun List<KSFile>.allDataClasses(
    allowedPackages: Set<String>
) = flatMap { it.declarations.filterIsInstance<KSClassDeclaration>() }
    .filter { it.classKind == ClassKind.CLASS }
    .filter { Modifier.DATA in it.modifiers }
    .filter { it.qualifiedName != null }
    .filter { classDeclaration ->
        val includedInPackages = allowedPackages
            .any { pckage ->
                classDeclaration.packageName.asString().startsWith(pckage)
            }
        allowedPackages.isEmpty() || includedInPackages
    }