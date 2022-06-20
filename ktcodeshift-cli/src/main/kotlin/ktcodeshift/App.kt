package ktcodeshift

import picocli.CommandLine
import java.io.File
import kotlin.io.path.extension
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val exitCode = CommandLine(CLI()).execute(*args)
    exitProcess(exitCode)
}

fun process(transformFile: File, targetDirs: List<File>, extensions: Set<String>) {
    println("Loading script $transformFile")
    val transform = evalScriptSource(transformFile.toScriptSource())

    targetDirs.forEach { targetDir ->
        targetDir.walk()
            .filter { it.isFile && extensions.contains(it.toPath().extension) }
            .forEach { targetFile ->
                println("Applying transform to $targetFile")
                val changedSource = applyTransform(transform, object : FileInfo {
                    override val path = targetFile.absolutePath
                    override val source = targetFile.readText(Charsets.UTF_8)
                })
                println(changedSource) // TODO: Modify target file.
            }
    }
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
