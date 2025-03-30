@file:Import("./common.transform.kts")

transform { fileInfo ->
    "\"\"\"\n" + renderHtml() + "\"\"\"\n"
}
