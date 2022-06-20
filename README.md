# ktcodeshift [![Java CI](https://github.com/orangain/ktcodeshift/actions/workflows/java_ci.yaml/badge.svg)](https://github.com/orangain/ktcodeshift/actions/workflows/java_ci.yaml)

ktcodeshift is a toolkit for running codemods over multiple Kotlin files inspired
by [jscodeshift](https://github.com/facebook/jscodeshift).

## Setup

### macOS

```
brew install orangain/tap/ktcodeshift
```

### Other platforms

Download the latest archive from [releases](https://github.com/orangain/ktcodeshift/releases) and extract
it. `ktcodeshift` command is available in the `bin` directory.

## Usage

```
Usage: ktcodeshift [-hV] [--extensions=EXT] -t=TRANSFORM_PATH PATH...

Apply transform logic in TRANSFORM_PATH (recursively) to every PATH.

      PATH...            Search target files in these paths.
      --extensions=EXT   Target file extensions to be transformed (comma
                           separated list)
                         (default: kt)
  -h, --help             Show this help message and exit.
  -t, --transform=TRANSFORM_PATH
                         Transform file
  -V, --version          Print version information and exit.
```

## Examples

Example transforms and their inputs/outputs are available
under [ktcodeshift-cli/src/test/resources/examples/](ktcodeshift-cli/src/test/resources/examples/) directory.
