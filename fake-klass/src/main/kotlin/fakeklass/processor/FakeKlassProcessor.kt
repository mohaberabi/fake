package fakeklass.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import fakeklass.processor.generator.fakeKlassPackages
import fakeklass.processor.generator.filterIsDataClasses
import fakeklass.processor.generator.getFilesInsidePackages
import fakeklass.processor.models.VarDeclareModel
import fakeklass.processor.models.VarDeclareType
import fakeklass.processor.templates.generateFakeClassBuilder
import fakeklass.processor.utils.isNull
import fakeklass.processor.utils.isObjectOfData
import fakeklass.processor.utils.toVarDeclare
import java.io.OutputStreamWriter


class FakeKlassProcessor(
    private val env: SymbolProcessorEnvironment,
) : SymbolProcessor {
    private val generated = mutableSetOf<String>()
    private val codeGenerator = env.codeGenerator
    private val options = env.options

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val packages = options.fakeKlassPackages().toSet()
        resolver
            .getFilesInsidePackages(packages)
            .filterIsDataClasses()
            .forEach { generateForDataClass(it) }

        return emptyList()
    }

    fun generateForDataClass(klass: KSClassDeclaration) {
        val primaryConstructor = klass.primaryConstructor ?: return
        val declares = primaryConstructor.parameters.mapNotNull {
            val resolved = it.type.resolve()
            if (resolved.isObjectOfData()) {
                //     (resolved.declaration as KSClassDeclaration).generateFakeClassBuilder()
                VarDeclareModel(
                    isNull = resolved.isNull(),
                    name = it.name?.asString() ?: return@mapNotNull null,
                    type = VarDeclareType.ObjectVar(it.type.resolve().declaration.simpleName.asString()),
                )
            } else {
                it.toVarDeclare()
            }
        }
        val pk = klass.packageName.asString()
        val originalName = klass.simpleName.asString()
        val generatedFileName = "${originalName}Fake"
        val containingFile = klass.containingFile
        val key = "$pk.$generatedFileName"
        if (!generated.add(key)) return
        val dependencies = if (containingFile != null) {
            Dependencies(aggregating = false, containingFile)
        } else {
            Dependencies(aggregating = false)
        }
        val file = codeGenerator.createNewFile(
            dependencies = dependencies,
            packageName = pk,
            fileName = generatedFileName
        )
        OutputStreamWriter(file, Charsets.UTF_8).use { output ->
            val generated = generateFakeClassBuilder(
                packageName = pk,
                sourceClassName = originalName,
                declarations = declares
            )
            output.write(generated)
        }
        generated.add(key)
    }
}


