package ktcodeshift

import picocli.CommandLine
import kotlin.io.path.extension
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val exitCode = CommandLine(CLI(::process)).execute(*args)
    exitProcess(exitCode)
}

fun process(args: CLIArgs) {
    println("Loading transform script ${args.transformFile}")
    val transform = evalScriptSource(args.transformFile.toScriptSource())
    val transformResults = args.targetDirs.flatMap { targetDir ->
        targetDir.walk()
            .filter { it.isFile && args.extensions.contains(it.toPath().extension) }
            .map { targetFile ->
                println("Processing $targetFile")
                val charset = Charsets.UTF_8
                val originalSource = targetFile.readText(charset)
                val changedSource = try {
                    applyTransform(transform, object : FileInfo {
                        override val path = targetFile.absolutePath
                        override val source = originalSource
                    })
                } catch (ex: Exception) {
                    return@map TransformResult.FAILED
                }

                if (changedSource == originalSource) {
                    TransformResult.UNMODIFIED
                } else {
                    targetFile.writeText(changedSource, charset)
                    TransformResult.SUCCEEDED
                }
            }
    }
        .groupingBy { it }
        .eachCount()

    println("Results:")
    println("${transformResults[TransformResult.FAILED] ?: 0} errors")
    println("${transformResults[TransformResult.UNMODIFIED] ?: 0} unmodified")
    println("${transformResults[TransformResult.SUCCEEDED] ?: 0} ok")
}

enum class TransformResult {
    SUCCEEDED, UNMODIFIED, FAILED
}

fun evalScriptSource(sourceCode: SourceCode): TransformFunction {
    transformFunction = null

    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<TransformScript>()
    val res = BasicJvmScriptingHost().eval(sourceCode, compilationConfiguration, null)
    res.reports.forEach {
        if (it.severity >= ScriptDiagnostic.Severity.WARNING) {
            println("[${it.severity}] : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
        }
    }

    val transform = transformFunction
    checkNotNull(transform) { "transform is not defined." }
    return transform
}

fun applyTransform(transform: TransformFunction, fileInfo: FileInfo): String {
    return transform(fileInfo)
}
