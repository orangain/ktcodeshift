transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .also { println(Dumper.dump(it)) }
        .toSource()
}
