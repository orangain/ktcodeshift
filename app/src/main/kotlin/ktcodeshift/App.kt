package ktcodeshift

import java.io.File
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

fun main(vararg args: String) {
    if (args.size != 2) {
        println("usage: <app> <script file> <target file>")
        return
    }

    val scriptFile = File(args[0])
    val targetFile = File(args[1])
    println("Loading script $scriptFile")

    val transform = evalScriptSource(scriptFile.toScriptSource())

    println("Applying transform to $targetFile")
    val changedSource = applyTransform(transform, object : FileInfo {
        override val path = targetFile.absolutePath
        override val source = targetFile.readText(Charsets.UTF_8)
    })
    println(changedSource)
}

fun evalScriptSource(sourceCode: SourceCode): TransformFunction {
    transformFunction = null

    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<TransformScript>()
    val res = BasicJvmScriptingHost().eval(sourceCode, compilationConfiguration, null)
    res.reports.forEach {
        println("[${it.severity}] : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
    }

    val transform = transformFunction
    checkNotNull(transform) { "transform is not defined." }
    return transform
}

fun applyTransform(transform: TransformFunction, fileInfo: FileInfo): String {
    return transform(fileInfo)
}
