@file:Import("./common.transform.kts")

import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    "\"\"\"\n" + renderHtml() + "\"\"\"\n"
}
