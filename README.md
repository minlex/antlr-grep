
# About

Antlr-grep is grep based on contex-free grammar and not regular expression. Could be used for fast, flexible, parsing.
Antlr-grep is using [Antlr](antlr.org) for generating lexer and parser.

# Installation

# Usage Example

[This grammars](https://github.com/antlr/grammars-v4) could be used for parsing.

For example:

 1. We want to find all clasess which extend specific class

        antlr-grep -g Java.g4 -p "classDeclaration: type=Foo*"






