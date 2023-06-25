import ktcodeshift.*

transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .toSource()
}
