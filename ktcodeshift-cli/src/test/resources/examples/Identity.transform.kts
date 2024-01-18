import ktast.ast.Dumper
import ktcodeshift.*

transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .also { println(Dumper.dump(it)) }
        .toSource()
}
