package ktcodeshift.script

import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.*
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.host.FileBasedScriptSource
import kotlin.script.experimental.host.FileScriptSource
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@Suppress("unused")
@KotlinScript(
    // File extension for the script type
    fileExtension = "transform.kts",
    // Compilation configuration for the script type
    compilationConfiguration = TransformScriptCompilationConfiguration::class
)
abstract class TransformScript

object TransformScriptCompilationConfiguration : ScriptCompilationConfiguration({
    // Implicit imports for all scripts of this type
    defaultImports(DependsOn::class, Repository::class, Import::class)
    jvm {
        // Extract the whole classpath from context classloader and use it as dependencies
        dependenciesFromCurrentContext(wholeClasspath = true)
    }
    // Callbacks
    refineConfiguration {
        // Process specified annotations with the provided handler
        onAnnotations(DependsOn::class, Repository::class, Import::class, handler = ::configureMavenDepsOnAnnotations)
    }
})

object TransformScriptEvaluationConfiguration : ScriptEvaluationConfiguration({
})

// Handler that reconfigures the compilation on the fly
fun configureMavenDepsOnAnnotations(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
    val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
        ?: return context.compilationConfiguration.asSuccess()
    val (importAnnotations, otherAnnotations) = annotations.partition { it.annotation is Import }

    val scriptBaseDir = (context.script as? FileBasedScriptSource)?.file?.parentFile
    val importedSources = importAnnotations.flatMap {
        (it.annotation as Import).paths.map { sourceName ->
            FileScriptSource(scriptBaseDir?.resolve(sourceName) ?: File(sourceName))
        }
    }

    return runBlocking {
        resolver.resolveFromScriptSourceAnnotations(otherAnnotations)
    }.onSuccess {
        context.compilationConfiguration.with {
            dependencies.append(JvmDependency(it))
            if (importedSources.isNotEmpty()) importScripts.append(importedSources)
        }.asSuccess()
    }
}

private val resolver = CompoundDependenciesResolver(FileSystemDependenciesResolver(), MavenDependenciesResolver())
