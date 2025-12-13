package fakeklass.processor.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class FakeKlassGenerator(
    private val resolver: Resolver,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator
) {

    fun generate(
        declaration: KSClassDeclaration,
        outputPackage: String
    ) {
        val className = declaration.simpleName.asString()
        val qualifiedName = requireNotNull(declaration.qualifiedName).asString()
        val primaryConstructor = declaration.primaryConstructor
        if (primaryConstructor == null) {
            logger.warn(message = "$qualifiedName has no primary constructor")
            return
        }

        val params = primaryConstructor.parameters
        val builderName = "${className}FakeBuilder"
        val functionName = "fake${className}"
        val typeName = declaration.toClassName()
        val builderType = ClassName(outputPackage, builderName)
        val builder = TypeSpec.classBuilder(builderName)
            .addKdoc("Generated fake builder for %L.\n", qualifiedName)
            .addModifiers(KModifier.PUBLIC)
        val assigns = mutableListOf<CodeBlock>()
        for (param in params) {
            val paramName = param.name?.asString() ?: run {
                logger.warn("Unnamed param in ${functionName},skipping")
                return
            }
            val paramType = param.type.resolve()
            val paramTypeName = paramType.toTypeName()
            val defaultExpression = defaultParamCreator(
                resolver = resolver,
                type = paramType,
                propName = paramName
            ) ?: run {
                logger.warn("Can not generate a default param in ${functionName}${paramName}")
                return
            }
            builder.addProperty(
                PropertySpec.builder(paramName, paramTypeName)
                    .mutable(true)
                    .initializer(defaultExpression)
                    .build()
            )
            assigns += CodeBlock.of("%L = b.%L", paramName, paramName)
        }
        val fakeFun = FunSpec.builder(functionName)
            .addKdoc("Generated fake for %L.\n", qualifiedName)
            .addParameter(
                ParameterSpec.builder(
                    "block",
                    LambdaTypeName.get(receiver = builderType, returnType = UNIT)
                ).defaultValue("{}").build()
            )
            .returns(typeName)
            .addCode(
                buildCodeBlock {
                    addStatement("val b = %T().apply(block)", builderType)
                    add("return %T(\n", typeName)
                    indent()
                    assigns.forEachIndexed { index, cb ->
                        add(cb)
                        add(if (index != assigns.lastIndex) ",\n" else "\n")
                    }
                    unindent()
                    add(")\n")
                }
            ).build()
        val file = FileSpec.builder(outputPackage, "${className}Fakes")
            .addType(builder.build())
            .addFunction(fakeFun)
            .build()

        file.writeTo(
            codeGenerator,
            aggregating = false,
            originatingKSFiles = listOfNotNull(declaration.containingFile)
        )
    }
}