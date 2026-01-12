package fakeklass.processor.templates

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import fakeklass.processor.models.VarDeclareModel
import fakeklass.processor.utils.toVarDeclare
import kotlin.collections.mapNotNull
import kotlin.collections.orEmpty

fun KSClassDeclaration.generateFakeClassBuilder() = generateFakeClassBuilder(
    packageName = packageName.asString(),
    declarations = primaryConstructor?.parameters.orEmpty()
        .mapNotNull { kSValueParameter -> kSValueParameter.toVarDeclare() },
    sourceClassName = simpleName.asString()
)

fun generateFakeClassBuilder(
    packageName: String,
    sourceClassName: String,
    declarations: List<VarDeclareModel>
): String {
    val builderClass = generateBuilderClass(
        packageName = packageName,
        sourceClassName = sourceClassName,
        declarations = declarations
    )
    val builderFunction = generateBuilderFunction(
        actualClassName = sourceClassName,
        declarations = declarations
    )
    return buildString {
        appendLine(builderClass)
        appendLine(builderFunction)
    }
}


