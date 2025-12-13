import com.erabipt.convention.FakeKlassExtension
import com.erabipt.convention.utils.configureKspForFake
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class FakeKlassConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) { apply("com.google.devtools.ksp"); }
            val fakeKlass = extensions.create<FakeKlassExtension>(name = "fakeKlass", objects)
            configureKspForFake(fakeKlass = fakeKlass)
        }
    }
}

