package fakeklass.processor.models


/**
 * public var [name] : [type] = [value]
 */
data class VarDeclareModel(
    val name: String,
    val type: VarDeclareType,
    val isNull: Boolean = false
)

