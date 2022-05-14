package ktcodeshift

import kastree.ast.Writer

class FlattenListTransform : Transform {
    override fun transform(fileInfo: FileInfo, api: Api): String {
        val file = api.parse(fileInfo.source)
        return Writer.write(file)
    }
}