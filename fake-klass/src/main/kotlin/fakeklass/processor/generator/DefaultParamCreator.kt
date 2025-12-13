package fakeklass.processor.generator

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Nullability
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ksp.toClassName


internal fun defaultParamCreator(
    resolver: Resolver,
    type: KSType,
    propName: String
): CodeBlock? {
    if (type.nullability == Nullability.NULLABLE) {
        return CodeBlock.of("null")
    }
    val nonNullDeclaration = type.declaration
    return when (val qualifiedName = nonNullDeclaration.qualifiedName?.asString()) {
        "kotlin.String" -> CodeBlock.of("%S", "${propName}_1")
        "kotlin.Int" -> CodeBlock.of("1")
        "kotlin.Long" -> CodeBlock.of("1L")
        "kotlin.Double" -> CodeBlock.of("1.0")
        "kotlin.Float" -> CodeBlock.of("1f")
        "kotlin.Boolean" -> CodeBlock.of("false")
        else -> {
            if (nonNullDeclaration is KSClassDeclaration && nonNullDeclaration.classKind == ClassKind.ENUM_CLASS) {
                nonNullDeclaration.defaultEnumCreator()
            } else if (qualifiedName == "kotlin.collections.List") {
                type.defaultListCreator(resolver = resolver, propName = propName)
            } else if (qualifiedName == "kotlin.collections.Set") {
                type.defaultSetCreator(resolver = resolver, propName = propName)
            } else if (qualifiedName == "kotlin.collections.Map") {
                type.defaultMapCreator(resolver = resolver, propName = propName)
            } else if (nonNullDeclaration is KSClassDeclaration && Modifier.DATA in nonNullDeclaration.modifiers) {
                CodeBlock.of("fake%L()", nonNullDeclaration.simpleName.asString())
            } else {
                null
            }
        }
    }
}

internal fun KSClassDeclaration.defaultEnumCreator(): CodeBlock? {
    val first = declarations.filterIsInstance<KSClassDeclaration>()
        .firstOrNull { it.classKind == ClassKind.ENUM_ENTRY } ?: return null
    return CodeBlock.of("%T.%L", this.toClassName(), first.simpleName.asString())
}

internal fun KSType.defaultListCreator(
    resolver: Resolver,
    propName: String
): CodeBlock {
    val argType = this.arguments.firstOrNull()?.type?.resolve()
        ?: return CodeBlock.of("emptyList()")
    val elementExpression = defaultParamCreator(resolver = resolver, type = argType, propName = propName)
        ?: return CodeBlock.of("emptyList()")
    return CodeBlock.of("listOf(%L)", elementExpression)
}

internal fun KSType.defaultSetCreator(
    resolver: Resolver,
    propName: String
): CodeBlock {
    val argType = this.arguments.firstOrNull()?.type?.resolve()
        ?: return CodeBlock.of("emptySet()")
    val elementExpression = defaultParamCreator(resolver = resolver, type = argType, propName = propName)
        ?: return CodeBlock.of("emptySet()")
    return CodeBlock.of("setOf(%L)", elementExpression)
}

internal fun KSType.defaultMapCreator(
    resolver: Resolver,
    propName: String
): CodeBlock {
    val keyType = this.arguments.firstOrNull()?.type?.resolve()
        ?: return CodeBlock.of("emptyMap()")
    val valueType = this.arguments.getOrNull(1)?.type?.resolve()
        ?: return CodeBlock.of("emptyMap()")
    val keyExpression = defaultParamCreator(resolver = resolver, type = keyType, propName = "${propName}Key")
        ?: return CodeBlock.of("emptyMap()")
    val valueExpression = defaultParamCreator(resolver = resolver, type = valueType, propName = "${propName}Value")
        ?: return CodeBlock.of("emptyMap()")
    return CodeBlock.of("mapOf(%L to %L )", keyExpression, valueExpression)
}