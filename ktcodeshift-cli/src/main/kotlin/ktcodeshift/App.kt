package ktcodeshift

import picocli.CommandLine
import kotlin.io.path.Path
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

private fun process(args: CLIArgs) {
    println("Loading transform script ${args.transformFile}")
    val transform = evalScriptSource(args.transformFile.toScriptSource(), onError = { exitProcess(1) })
    if (args.dryRun) {
        println("Running in dry mode, no files will be written!")
    }
    val transformResults = args.targetDirs.flatMap { targetDir ->
        targetDir.walk()
            .filter { it.isFile && args.extensions.contains(it.toPath().extension) }
            .map { targetFile ->
                println("Processing $targetFile")
                val charset = Charsets.UTF_8
                val originalSource = targetFile.readText(charset)
                val changedSource = try {
                    applyTransform(transform, object : FileInfo {
                        override val path = Path(targetFile.absolutePath)
                        override val source = originalSource
                    })
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    return@map TransformResult.FAILED
                }

                if (changedSource == originalSource) {
                    TransformResult.UNMODIFIED
                } else {
                    if (!args.dryRun) {
                        targetFile.writeText(changedSource, charset)
                    }
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

private enum class TransformResult {
    SUCCEEDED, UNMODIFIED, FAILED
}

internal fun evalScriptSource(sourceCode: SourceCode, onError: () -> Unit = {}): TransformFunction {
    transformFunction = null

    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<TransformScript>()
    val res = BasicJvmScriptingHost().eval(sourceCode, compilationConfiguration, null)
    res.reports
        .asSequence()
        .filter { d -> d.severity >= ScriptDiagnostic.Severity.WARNING }
        .forEach { d ->
            val source = d.sourcePath?.let { "$it:" }.orEmpty()
            val locationString = d.location?.start?.let { "${it.line}:${it.col}: " }.orEmpty()
            val extra = d.exception?.let { ": $it" }.orEmpty()
            val messageLine = "$source$locationString${d.severity}: ${d.message}$extra"
            when (d.severity) {
                ScriptDiagnostic.Severity.ERROR,
                ScriptDiagnostic.Severity.FATAL -> println(CommandLine.Help.Ansi.AUTO.string("@|bold,red $messageLine |@"))
                ScriptDiagnostic.Severity.WARNING -> println(CommandLine.Help.Ansi.AUTO.string("@|yellow $messageLine |@"))
                else -> println(messageLine)
            }
        }

    val transform = transformFunction
    if (transform == null && res.reports.any { it.severity >= ScriptDiagnostic.Severity.ERROR }) {
        onError()
    }
    checkNotNull(transform) { "transform is not defined." }
    return transform
}

internal fun applyTransform(transform: TransformFunction, fileInfo: FileInfo): String {
    return transform(fileInfo) ?: fileInfo.source
}
