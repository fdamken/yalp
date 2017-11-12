## Compilation Steps

The following steps are executed in the displayed order:

| Name | Consumes | Produces |
| --- | --- | --- |
| Token Tree Parsing | Code | Token Tree (TT) |
| Syntax Tree Parsing | TT | Syntax Tree (ST) |
| Syntax Analysis | ST | Abstract Syntax Tree (AST) |
| Context Analysis | AST | Decorated Abstract Syntax Tree (DAST) |
| Code Generation | DAST | Assembly |

Code generation may be replaced with execution if YALP does not work as a compiler but as an interpreter.
