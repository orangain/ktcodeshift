package ktcodeshift

import picocli.CommandLine.*
import java.io.File
import java.util.concurrent.Callable

@Command(
    name = "ktcodeshift",
    versionProvider = VersionProvider::class,
    mixinStandardHelpOptions = true,
    description = ["Apply transforms."]
)
class CLI : Callable<Int?> {
    @Parameters(arity = "1..*", description = ["The file whose checksum to calculate."])
    lateinit var targetDirs: Array<File>

    @Option(names = ["-t", "--transform"], required = true, description = ["Transform file"])
    lateinit var transformFile: File

    @Option(names = ["--extensions"], description = ["Target file extensions to be transformed (comma separated list)"])
    var extensions: String = "kt"

    override fun call(): Int {
        process(
            transformFile,
            targetDirs.toList(),
            extensions.split(",").toSet()
        )
        return 0
    }
}

class VersionProvider : IVersionProvider {
    override fun getVersion(): Array<String> {
        return arrayOf(this::class.java.getPackage().implementationVersion ?: "unknown")
    }
}
