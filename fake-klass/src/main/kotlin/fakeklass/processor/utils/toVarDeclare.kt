package fakeklass.processor.utils

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Nullability
import fakeklass.processor.constants.STRING_ANY
import fakeklass.processor.models.VarDeclareModel
import fakeklass.processor.models.VarDeclareType

fun KSValueParameter.toVarDeclare(): VarDeclareModel? {
    val resolved = type.resolve()
    val isNull = resolved.isNull()
    if (resolved.isObjectOfData()) {
        val simpleName = resolved.declaration.simpleName.asString()
        return VarDeclareModel(
            isNull = isNull,
            name = this.name?.asString() ?: return null,
            type = VarDeclareType.ObjectVar(simpleName),
        )
    }
    val qName = resolved.declaration.qualifiedName?.asString() ?: return null
    return VarDeclareModel(
        name = this.name?.asString() ?: return null,
        isNull = isNull,
        type = qName.typeVar(typeParams = resolved.arguments.map { it.stringify() }) ?: return null,
    )
}

fun KSType.isObjectOfData(): Boolean {
    return declaration is KSClassDeclaration && Modifier.DATA in declaration.modifiers
}

fun KSTypeArgument.stringify(): String {
    return type?.resolve()?.declaration?.simpleName?.asString() ?: STRING_ANY
}

fun KSType.isNull() = nullability == Nullability.NULLABLE