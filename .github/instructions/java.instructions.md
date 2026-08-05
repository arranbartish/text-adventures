---
applyTo: "**/*.java"
---

## Java coding instructions

1. Keep code Java 8 compatible. Do not introduce APIs requiring newer language/runtime levels.
2. Place reusable gameplay mechanics in `GameComponents`; keep adventure-specific behavior in `TheDarkDuck` or `TheDarkOfDarkness`.
3. Prefer constructor injection for collaborators and IO surfaces; keep `main(...)` focused on object wiring.
4. Keep command/action matching centralized through existing searchable patterns (for example `Searchable` / `ArrayTermedSearchable`) instead of ad-hoc parsing.
5. Use the existing factory and value patterns (for example `Item.create(...)`) instead of bypassing them.
6. Avoid broad exception handling and silent failure paths; surface errors explicitly.
7. If file IO is introduced, prefer `java.nio.file` APIs over `java.io.File`.
8. Keep new code in the existing package style `au.bartish.game...` and align naming with existing domain terms.
