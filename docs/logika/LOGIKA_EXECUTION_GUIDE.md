# Logika execution guide (coursework)

This document defines exactly what to run and what evidence to keep for the demo/report.

## 0. Logika source artifacts

The executable/proof-oriented Logika artifacts are under:

- `src/logika/ParserTopLevelProof.sc`
- `src/logika/ClippingSafetyProof.sc`
- `src/logika/ColorScopeProof.sc`
- `src/logika/GeometryInvariantsProof.sc`

These files are small Sireum/Logika models of the proof-relevant behavior in the
production Scala implementation. They are kept outside `src/main/scala` so Maven does
not try to compile Sireum-specific annotations and contracts.

Run them in a Sireum environment with Logika enabled, for example:

```bash
sireum logika src/logika/ParserTopLevelProof.sc
sireum logika src/logika/ClippingSafetyProof.sc
sireum logika src/logika/ColorScopeProof.sc
sireum logika src/logika/GeometryInvariantsProof.sc
```

If your Sireum setup verifies scripts as a folder/project, open `src/logika`
in the Sireum VS Code plugin and run Logika on each file.

Note: this environment did not have `sireum` installed, so proof execution still needs
to be performed on a machine with Sireum available before claiming tool-checked results.

## 1. Proof targets and code mapping

1. Parser top-level rules  
   - Code: `src/main/scala/types/Parser.scala` (`validateTopLevel`)
   - Logika model: `src/logika/ParserTopLevelProof.sc`
   - Property: accepted program starts with `BOUNDING-BOX` and contains exactly one top-level box.

2. Clipping safety  
   - Code: `src/main/scala/types/DrawingEngine.scala` (`drawWithState`, `collectTexts`, `insideBox`)
   - Logika model: `src/logika/ClippingSafetyProof.sc`
   - Property: every emitted point/text is within active bounding box.

3. Color scope  
   - Code: `src/main/scala/types/DrawingEngine.scala` (`renderCommands`, `fillCommand`, `collectTexts`)
   - Logika model: `src/logika/ColorScopeProof.sc`
   - Property: scoped color changes (`DRAW`, `FILL`) do not affect sibling commands.

4. Geometry invariants  
   - Code: `src/main/scala/types/Draw.scala`
   - Logika model: `src/logika/GeometryInvariantsProof.sc`
   - Property: line stepping is bounded/progressive; circle generation preserves 8-way symmetry.

## 2. Demo evidence checklist

For each proof document in `proofs/`:

1. Keep the formal statement (pre/post/invariant).
2. Keep the argument steps (Logika-style proof outline).
3. Keep the code reference (file + function).
4. Keep one concrete example trace used in the explanation.

## 3. Submission packaging

Include:

- this guide,
- the four proof files under `proofs/`,
- the Logika source files under `src/logika/`,
- runtime test outputs (separate from Logika) as supporting evidence.
