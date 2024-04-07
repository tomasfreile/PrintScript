
# Printscript

### Pre-commit Script
To copy pre-commit script to .git/hooks run:
```bash
./gradlew installGitHook
```

### Linter check
To check linter:
```bash
./gradlew check
```

## Run Code
You can run PrintScript code using:
```bash
./print-script execute <source>
```
Usage: print-script <operation> <source> /n
operations: execute - validate - format - analyze /n
options: -v <version>, -c <config file path>
