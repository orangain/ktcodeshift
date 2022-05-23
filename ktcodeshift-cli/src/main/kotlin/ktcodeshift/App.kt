package ktcodeshift

import kotlinx.cli.*
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.extension
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

fun main(args: Array<String>) {
    val parser = ArgParser("ktcodeshift")
    val version by parser.option(
        ArgType.Boolean,
        shortName = "v",
        fullName = "version",
        description = "Print version"
    ).default(false)
    val transformPath by parser.option(
        ArgType.String,
        shortName = "t",
        fullName = "transform",
        description = "Transform file"
    ).default("tarnsform.kts")
    val extensions by parser.option(
        ArgType.String,
        fullName = "extensions",
        description = "Target file extensions to be transformed (comma separated list)"
    ).default("kt").delimiter(",")
    val targetPaths by parser.argument(ArgType.String, fullName = "PATH").vararg()

    parser.parse(args)

    if (version) {
        println(object {}::class.java.getPackage().implementationVersion ?: "unknown")
        return
    }

    process(
        transformFile = Path(transformPath).absolute().toFile(),
        targetDirs = targetPaths.map { Path(it).absolute().toFile() },
        extensions = extensions.toSet(),
    )
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
                println(changedSource)
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
