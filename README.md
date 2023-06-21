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

## Transform file

A transform file is a Kotlin script file that defines a lambda function `transform: (FileInfo) -> String?`.
The `transform` function takes an
argument [FileInfo](https://orangain.github.io/ktcodeshift/main/api/ktcodeshift-dsl/ktcodeshift/-file-info/index.html),
which has `source` and `path` property, and returns the modified source code or null. When the transform function return
the null, the file will be unmodified. The script filename should end with `.transform.kts`.

```kts
import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
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

- [API document of ktcodeshift](https://orangain.github.io/ktcodeshift/main/api/ktcodeshift-dsl/ktcodeshift/index.html)
- [API document of ktast](https://orangain.github.io/ktast/latest/api/ast/ktast.ast/index.html)

## Examples

Example transform files are available
under the [ktcodeshift-cli/src/test/resources/examples/](ktcodeshift-cli/src/test/resources/examples/) directory. The
[\_\_testfixtures\_\_](ktcodeshift-cli/src/test/resources/examples/__testfixtures__) directory also contains pairs of
their input and output.
