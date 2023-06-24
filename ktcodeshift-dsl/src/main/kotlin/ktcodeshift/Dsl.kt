package ktcodeshift

typealias TransformFunction = (FileInfo) -> String?

var transformFunction: TransformFunction? = null

/**
 * Defines the transform function. This function will be called for each target file.
 *
 * @param fn the transform function. It takes a [FileInfo] and returns the transformed source code. When the function returns null, the target file will be left unmodified.
 */
@Suppress("unused")
fun transform(fn: TransformFunction) {
    if (transformFunction != null) {
        error("You cannot call transform function more than once.")
    }

    transformFunction = fn
}
