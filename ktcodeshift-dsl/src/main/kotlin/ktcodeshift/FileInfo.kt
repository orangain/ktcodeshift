package ktcodeshift

import java.nio.file.Path

interface FileInfo {
    val source: String
    val path: Path
}
