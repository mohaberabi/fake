package fakeklass.processor.models

import fakeklass.processor.constants.SIMPLE_BOOLEAN
import fakeklass.processor.constants.SIMPLE_DOUBLE
import fakeklass.processor.constants.SIMPLE_FLOAT
import fakeklass.processor.constants.SIMPLE_INT
import fakeklass.processor.constants.SIMPLE_LIST
import fakeklass.processor.constants.SIMPLE_LONG
import fakeklass.processor.constants.SIMPLE_MAP
import fakeklass.processor.constants.SIMPLE_MUTABLE_LIST
import fakeklass.processor.constants.SIMPLE_MUTABLE_MAP
import fakeklass.processor.constants.SIMPLE_MUTABLE_SET
import fakeklass.processor.constants.SIMPLE_SET
import fakeklass.processor.constants.SIMPLE_STRING

sealed class VarDeclareType(
    val varName: String
) {
    data object LongVar : VarDeclareType(SIMPLE_LONG)
    data object IntVar : VarDeclareType(SIMPLE_INT)
    data object DoubleVar : VarDeclareType(SIMPLE_DOUBLE)
    data object FloatVar : VarDeclareType(SIMPLE_FLOAT)
    data object BooleanVar : VarDeclareType(SIMPLE_BOOLEAN)
    data object StringVar : VarDeclareType(SIMPLE_STRING)
    data class MapVar(val keyType: String, val valueType: String) :
        VarDeclareType("${SIMPLE_MAP}<${keyType},${valueType}>")

    data class SetVar(val ofType: String) : VarDeclareType("$SIMPLE_SET<${ofType}>")
    data class ListVar(val ofType: String) : VarDeclareType("$SIMPLE_LIST<${ofType}>")
    data class ObjectVar(val name: String) : VarDeclareType(name)

    data class MutableMapVar(val keyType: String, val valueType: String) :
        VarDeclareType("$SIMPLE_MUTABLE_MAP<${keyType},${valueType}>")

    data class MutableSetVar(val ofType: String) : VarDeclareType("$SIMPLE_MUTABLE_SET<${ofType}>")
    data class MutableListVar(val ofType: String) : VarDeclareType("$SIMPLE_MUTABLE_LIST<${ofType}>")
}