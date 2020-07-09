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
  - [x] Lexer
  - [x] Parser
  - [ ] Semantic analysis
- Backend
  - [ ] Interpreter
  
## How to test?
You can test frontend CLI by running `:frontend:run` gradle task.
