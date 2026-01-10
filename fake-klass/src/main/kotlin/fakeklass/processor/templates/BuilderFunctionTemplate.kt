package fakeklass.processor.templates

import fakeklass.processor.models.VarDeclareModel
import kotlin.collections.forEach


fun generateBuilderFunction(
    actualClassName: String,
    declarations: List<VarDeclareModel>
): String {
    val builderName = "${actualClassName}Builder"
    return buildString {
        appendLine("fun fake${actualClassName}(")
        appendLine("block:$builderName.()->Unit,")
        appendLine("): $actualClassName {")
        appendLine("val builder = $builderName().apply(block)")
        appendLine("val result = $actualClassName(")
        declarations.forEach { declare ->
            appendLine("${declare.name} = builder.${declare.name},")
        }
        appendLine(")")
        appendLine("return result")
        appendLine("}")
    }
}