package fakeklass.processor.templates

import fakeklass.processor.models.VarDeclareModel
import fakeklass.processor.utils.stringify
import kotlin.collections.forEach


fun generateBuilderClass(
    packageName: String,
    sourceClassName: String,
    declarations: List<VarDeclareModel>
): String {
    return buildString {
        appendLine("package $packageName")
        appendLine("public class ${sourceClassName}Builder {")
        declarations.forEach {
            appendLine(it.stringify())
        }
        appendLine("}")
    }
}