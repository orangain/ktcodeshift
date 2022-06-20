package ktcodeshift

import picocli.CommandLine.*
import java.io.File
import java.util.concurrent.Callable

@Command(
    name = "ktcodeshift",
    versionProvider = VersionProvider::class,
    mixinStandardHelpOptions = true,
    description = ["", "Apply transform logic in TRANSFORM_PATH (recursively) to every PATH.", ""]
)
class CLI(private val process: (CLIArgs) -> Unit) :
    Callable<Int?> {
    @Parameters(
        arity = "1..*",
        paramLabel = "PATH",
        description = ["Search target files in these paths."]
    )
    lateinit var targetDirs: Array<File>

    @Option(
        names = ["-t", "--transform"],
        required = true,
        paramLabel = "TRANSFORM_PATH",
        description = ["Transform file"]
    )
    lateinit var transformFile: File

    @Option(
        names = ["--extensions"],
        paramLabel = "EXT",
        description = ["Target file extensions to be transformed (comma separated list)", "(default: kt)"]
    )
    var extensions: String = "kt"

    override fun call(): Int {
        process(
            CLIArgs(
                transformFile,
                targetDirs.toList(),
                extensions.split(",").toSet(),
            )
        )
        return 0
    }
}

data class CLIArgs(
    val transformFile: File,
    val targetDirs: List<File>,
    val extensions: Set<String>,
)

class VersionProvider : IVersionProvider {
    override fun getVersion(): Array<String> {
        return arrayOf(this::class.java.getPackage().implementationVersion ?: "unknown")
    }
}
