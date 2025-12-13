package fakeklass.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import fakeklass.processor.generator.FakeKlassGenerator
import fakeklass.processor.utils.allDataClasses
import fakeklass.processor.utils.fakeKlassOutput
import fakeklass.processor.utils.fakeKlassPackages
import fakeklass.processor.utils.fakeKlassSourceSet

class FakeKlassProcessor(
    private val env: SymbolProcessorEnvironment,
) : SymbolProcessor {
    private val generated = mutableSetOf<String>()
    private val log = env.logger
    private val codeGenerator = env.codeGenerator
    private val options = env.options

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val packages = options.fakeKlassPackages()
        val outputPackage = options.fakeKlassOutput()
        val allFiles = resolver.getAllFiles().toList()
        val allDataClasses = allFiles.allDataClasses(allowedPackages = packages)
        val fakeKlassGenerator = FakeKlassGenerator(resolver = resolver, logger = log, codeGenerator = codeGenerator)
        allDataClasses.forEach { declaration ->
            val qName = declaration.qualifiedName?.asString()
            qName?.let {
                if (generated.contains(it).not()) {
                    fakeKlassGenerator.generate(declaration = declaration, outputPackage = outputPackage)
                    generated += it
                }
            }
        }
        return emptyList()
    }
}