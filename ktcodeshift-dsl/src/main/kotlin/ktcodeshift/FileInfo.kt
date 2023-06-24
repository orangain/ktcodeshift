package ktcodeshift

import java.nio.file.Path

/**
 * Information about a file to be transformed.
 *
 * @property source the source code of the file
 * @property path the path of the file
 */
interface FileInfo {
    val source: String
    val path: Path
}
