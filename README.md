# Boa
Boa - my own programming language created for fun

## Syntax

### Variable declaration
```
let|var IDENTIFIER[: IDENTIFIER] = EXPRESSION
```

### Invocation
```
IDENTIFIER([EXPRESSION[,EXPRESSION[...]])
```

### Assignment
```
IDENTIFIER = EXPRESSION
```

### Expression
```
INVOCATION|EXPRESSION|LITERAL|IDENTIFIER [OPERATOR INVOCATION|EXPRESSION|LITERAL|IDENTIFIER]
```

### Import
```
import STRING_LITERAL
```

### If statement
```
if(EXPRESSION)
    STATEMENT
[else if(EXPRESSION)
    STATEMENT
]
[else STATEMENT]
```

### Statement
```
IMPORT|VARIABLE|ASSIGNMENT|IF|EXPRESSION
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
