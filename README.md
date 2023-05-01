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

A transform file is a Kotlin script file that defines a lambda function `transform`. The `transform` function takes
information about the file as an argument and returns the source code of the result of the transformation.

```kts
import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.Expression.Name>()
        .filter { v, parent ->
            parent is Node.Declaration.Property.Variable && v.name == "foo"
        }
        .replaceWith { v ->
            v.copy(name = "bar")
        }
        .toSource()
}
```

The following API documents will be helpful to write a transform file.

- https://orangain.github.io/ktcodeshift/main/api/ktcodeshift-dsl/ktcodeshift/index.html
- https://orangain.github.io/ktast/main/api/ast/ktast.ast/index.html

## Examples

Example transforms and their inputs/outputs are available
under [ktcodeshift-cli/src/test/resources/examples/](ktcodeshift-cli/src/test/resources/examples/) directory.
