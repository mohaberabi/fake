package fakeklass.processor.utils

import fakeklass.processor.constants.KOTLIN_BOOLEAN
import fakeklass.processor.constants.KOTLIN_DOUBLE
import fakeklass.processor.constants.KOTLIN_FLOAT
import fakeklass.processor.constants.KOTLIN_INT
import fakeklass.processor.constants.KOTLIN_LIST
import fakeklass.processor.constants.KOTLIN_LONG
import fakeklass.processor.constants.KOTLIN_MAP
import fakeklass.processor.constants.KOTLIN_MUTABLE_LIST
import fakeklass.processor.constants.KOTLIN_MUTABLE_MAP
import fakeklass.processor.constants.KOTLIN_MUTABLE_SET
import fakeklass.processor.constants.KOTLIN_SET
import fakeklass.processor.constants.KOTLIN_STRING
import fakeklass.processor.constants.STRING_ANY
import fakeklass.processor.models.VarDeclareType

fun String.typeVar(
    typeParams: List<String> = listOf(),
) = when (this) {
    KOTLIN_LONG -> VarDeclareType.LongVar
    KOTLIN_INT -> VarDeclareType.IntVar
    KOTLIN_FLOAT -> VarDeclareType.FloatVar
    KOTLIN_DOUBLE -> VarDeclareType.DoubleVar
    KOTLIN_BOOLEAN -> VarDeclareType.BooleanVar
    KOTLIN_STRING -> VarDeclareType.StringVar
    KOTLIN_MUTABLE_MAP -> VarDeclareType.MutableMapVar(
        typeParams.firstOrNull() ?: STRING_ANY,
        typeParams.getOrNull(1) ?: STRING_ANY
    )

    KOTLIN_MUTABLE_LIST -> VarDeclareType.MutableListVar(typeParams.firstOrNull() ?: STRING_ANY)
    KOTLIN_MUTABLE_SET -> VarDeclareType.MutableSetVar(typeParams.firstOrNull() ?: STRING_ANY)
    KOTLIN_MAP -> VarDeclareType.MapVar(
        typeParams.firstOrNull() ?: STRING_ANY,
        typeParams.getOrNull(1) ?: STRING_ANY
    )

    KOTLIN_SET -> VarDeclareType.SetVar(typeParams.firstOrNull() ?: STRING_ANY)
    KOTLIN_LIST -> VarDeclareType.ListVar(typeParams.firstOrNull() ?: STRING_ANY)

    else -> null
}


