# ShapeDesignGUI

Java + Scala IDE for a coursework graphics language (`BOUNDING-BOX`, `LINE`, `RECTANGLE`, `CIRCLE`, `TEXT-AT`, `DRAW`, `FILL`).

## Run

1. Ensure Java and Maven are installed.
2. Run:

```bash
mvn test
mvn exec:java -Dexec.mainClass=Main
```

## Current behavior

- Left panel: grid-based drawing area with bounding-box visualization.
- Right panel: editor, draw button, and error output area.
- Example loaders:
  - `docs/examples/pie-chart.shape`
  - `docs/examples/bar-chart.shape`
- Parser now enforces:
  - first command must be `BOUNDING-BOX`,
  - exactly one top-level `BOUNDING-BOX` command.

## Known limitation (intentional)

- **Highlighting of the currently drawn object is not implemented** and is out-of-scope for this delivery.

## Coursework correctness

- Runtime validation is covered by tests under `src/test`.
- Formal correctness track is documented in:
  - `docs/logika/LOGIKA_CORRECTNESS_PLAN.md`
- Proof-oriented Logika source files are under:
  - `src/logika/`
