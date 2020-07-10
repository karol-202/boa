# Boa
Boa - my own programming language created for fun

## Syntax

### Variable declaration
```
let|var identifier = expression
```

### Invocation
```
identifier([expression[,expression[...]])
```

### Assignment
```
identifier = expression
```

### Expression
```
invocation|expression|literal|identifier [operator invocation|expression|literal|identifier]
```

More features will be added.

## Implementation stages:
- Frontend
  - [X] Lexer
  - [X] Parser
  - [ ] Semantic analysis
- Backend
  - [X] Interpreter
  
## How to test?
You can test Boa using CLI by running `:cli:run` gradle task.
