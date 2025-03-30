# ktcodeshift [![Java CI](https://github.com/orangain/ktcodeshift/actions/workflows/java_ci.yaml/badge.svg)](https://github.com/orangain/ktcodeshift/actions/workflows/java_ci.yaml)

ktcodeshift is a toolkit for running codemods over multiple Kotlin files inspired
by [jscodeshift](https://github.com/facebook/jscodeshift). It provides:

- A runner, which executes the provided transform for each file passed to it. It also outputs a summary of how many
  files have (not) been transformed.
- A wrapper around [ktast](https://github.com/orangain/ktast), providing a different API. ktast is a Kotlin AST library
  and also tries to preserve the style of original code as much as possible.

## Setup

### Prerequisites

- Java 11 or later is required.

### macOS

```
brew install orangain/tap/ktcodeshift
```

### Other platforms

Download the latest archive from [releases](https://github.com/orangain/ktcodeshift/releases) and extract
it. `ktcodeshift` command is available in the `bin` directory.

## Usage

```
Usage: ktcodeshift [-dhV] [--extensions=EXT] -t=TRANSFORM_PATH PATH...

Apply transform logic in TRANSFORM_PATH (recursively) to every PATH.

      PATH...            Search target files in these paths.
  -d, --dry              dry run (no changes are made to files)
      --extensions=EXT   Target file extensions to be transformed (comma
                           separated list)
                         (default: kt)
  -h, --help             Show this help message and exit.
  -t, --transform=TRANSFORM_PATH
                         Transform file
  -V, --version          Print version information and exit.
```

For example:

```
ktcodeshift -t RenameVariable.transform.kts src/main/kotlin
```

## Transform file

A transform file is a Kotlin script file that defines a lambda function `transform: (FileInfo) -> String?`.
The `transform` function will be called for each file on the target paths by the ktcodeshift.

The `transform` function takes an
argument [FileInfo](https://orangain.github.io/ktcodeshift/latest/api/ktcodeshift-dsl/ktcodeshift/-file-info/index.html)
,
which has `source: String` and `path: java.nio.file.Path` of the target file, and must return the modified source code
or null. When the transform function return the null or the same source code as the input, the ktcodeshift does not
modify the target file.

The script filename should end with `.transform.kts`.

```kts
import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .find<Node.Expression.NameExpression>()
        .filter { n ->
            parent is Node.Variable && n.text == "foo"
        }
        .replaceWith { n ->
            n.copy(text = "bar")
        }
        .toSource()
}
```

The following API documents will be helpful to write a transform file.

- [API document of ktcodeshift](https://orangain.github.io/ktcodeshift/latest/api/ktcodeshift-dsl/ktcodeshift/index.html)
- [API document of ktast](https://orangain.github.io/ktast/latest/api/ast/ktast.ast/index.html)

### Annotations

The following annotations can be used in the transform file:

| Annotation         | Description                                                                                                                                                                    |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `@file:Repository` | URL of the repository where the library specified in `@file:DependsOn` is hosted.<br>e.g.: `@file:Repository("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")` |
| `@file:DependsOn`  | Dependent library of the transform file. <br>e.g.: `@file:DependsOn("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")`                                                           |
| `@file:Import`     | Script file path(s) to import into the transform file. <br>e.g.: `@file:Import("./common.transform.kts")`                                                                      |


## Examples

Example transform files are available
under the [ktcodeshift-cli/src/test/resources/examples/](ktcodeshift-cli/src/test/resources/examples/) directory. The
[\_\_testfixtures\_\_](ktcodeshift-cli/src/test/resources/examples/__testfixtures__) directory also contains pairs of
their input and output.

## Development Tips

### Dumping AST

You can dump the AST of a Kotlin file using the [ktast.ast.Dumper](https://orangain.github.io/ktast/latest/api/ast/ktast.ast/-dumper/index.html). This is useful to understand the structure of the AST. For example:

```kts
import ktast.ast.*
import ktcodeshift.*

transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .also { println(Dumper.dump(it)) } // This line dumps the AST.
        .toSource()
}
```

### Builder Functions

The [ktcodeshift](https://orangain.github.io/ktcodeshift/latest/api/ktcodeshift-dsl/ktcodeshift/index.html) package provides a number of builder functions to create AST nodes. The function name corresponds to the class name of the AST node, i.e. `Node.Expression.NameExpression` is created by `nameExpression()` function. Unlike the parameters of the constructor of the AST node class, many of the parameters of the builder functions are optional and have sensible default values.

## Internal

### How to release

1. Update the version in `{ktcodeshift-cli,ktcodeshift-dsl}/build.gradle.kts` on main branch.
2. Create and push a tag with the version, e.g. `0.1.0`.
3. CI will publish a release note to GitHub and update the Homebrew formula in the [orangain/homebrew-tap](https://github.com/orangain/homebrew-tap) repository.
