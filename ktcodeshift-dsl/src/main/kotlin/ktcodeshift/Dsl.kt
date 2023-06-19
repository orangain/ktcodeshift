package ktcodeshift

typealias TransformFunction = (FileInfo) -> String?

var transformFunction: TransformFunction? = null

@Suppress("unused")
fun transform(fn: TransformFunction) {
    if (transformFunction != null) {
        error("You cannot call transform function more than once.")
    }

    transformFunction = fn
}
