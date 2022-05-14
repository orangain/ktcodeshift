package ktcodeshift

class FlattenListTransform : Transform {
    override fun transform(fileInfo: FileInfo, api: Api): String {
        val ast = api.parse(fileInfo.source)
        return ast.toString()
    }
}