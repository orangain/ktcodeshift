package ktcodeshift

interface Transform {
    fun transform(fileInfo: FileInfo, api: Api): String
}

interface FileInfo {
    val path: String
    val source: String
}
