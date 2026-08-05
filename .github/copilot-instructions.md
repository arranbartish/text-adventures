# Copilot Instructions for text-adventures

This repository is a **Java multi-module Maven project** with these modules:
- `GameComponents` (shared engine/components)
- `TheDarkOfDarkness` (game implementation)
- `TheDarkDuck` (game implementation)

## Project conventions

1. Keep shared, reusable mechanics in `GameComponents`; keep game-specific story/world logic in the relevant game module.
2. Maintain package conventions under `au.bartish.game`.
3. Preserve Java 8 compatibility in code changes (project compiles with source/target 1.8).
4. Prefer constructor-injected collaborators (for example `Scanner`, `PrintStream`) and keep wiring in `main(...)`.
5. Follow existing domain patterns (`Item`, `Location`, `Inventory`, `Artifact`, `Navigation`) rather than introducing parallel abstractions.
6. Keep changes small and local; avoid unrelated refactors.

## Validation

Run tests from the repository root with Maven:

```bash
mvn test
```

If the shell environment does not expose Maven/Java on PATH, use the configured SDKMAN paths.
