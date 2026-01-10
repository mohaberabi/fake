package fakeklass.processor.utils

import fakeklass.processor.constants.LIST_DEFAULT
import fakeklass.processor.constants.MAP_DEFAULT
import fakeklass.processor.constants.MUTABLE_LIST_DEFAULT
import fakeklass.processor.constants.MUTABLE_MAP_DEFAULT
import fakeklass.processor.constants.MUTABLE_SET_DEFAULT
import fakeklass.processor.constants.NULL
import fakeklass.processor.constants.SET_DEFAULT
import fakeklass.processor.constants.TRUE
import fakeklass.processor.models.VarDeclareModel
import fakeklass.processor.models.VarDeclareType

fun VarDeclareModel.appendValue(): String {
    if (this.isNull) return NULL
    return when (this.type) {
        is VarDeclareType.MutableMapVar -> MUTABLE_MAP_DEFAULT
        is VarDeclareType.MutableSetVar -> MUTABLE_SET_DEFAULT
        is VarDeclareType.MutableListVar -> MUTABLE_LIST_DEFAULT
        is VarDeclareType.MapVar -> MAP_DEFAULT
        is VarDeclareType.SetVar -> SET_DEFAULT
        is VarDeclareType.ListVar -> LIST_DEFAULT
        VarDeclareType.BooleanVar -> TRUE
        VarDeclareType.StringVar -> "\"\""
        VarDeclareType.FloatVar -> "${0}f"
        VarDeclareType.IntVar -> "0"
        VarDeclareType.DoubleVar -> "0.0"
        VarDeclareType.LongVar -> "0L"
        else -> "fake${type.varName}{}"
    }
}

fun VarDeclareModel.stringify(): String {
    return buildString {
        append("public ")
        append("var ")
        append(name)
        append(" : ")
        if (isNull) {
            append("${type.varName}?")
        } else {
            append(type.varName)
        }
        append(" = ")
        append(appendValue())
    }


}

