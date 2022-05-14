package ktcodeshift

fun applyTransform(transform: Transform, fileInfo: FileInfo): String {
    val api = Api()
    return transform.transform(fileInfo, api)
}

