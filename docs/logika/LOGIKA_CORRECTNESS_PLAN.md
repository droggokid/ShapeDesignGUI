# Sireum Logika Correctness Track

This folder is the coursework correctness package for **Sireum Logika**.

## Scope

The Logika part focuses on formal arguments for core semantic properties:

1. **Top-level parser validity**
   - first command is `BOUNDING-BOX`,
   - exactly one top-level `BOUNDING-BOX`.
2. **Clipping safety**
   - rendered points/text are inside bounding box when one is active.
3. **Color scope**
   - `DRAW`/`FILL` do not leak color outside lexical scope.
4. **Geometry algorithm invariants**
   - Bresenham step bounds and progress,
   - Midpoint circle symmetry obligations.

## Artifacts in this directory

- `LOGIKA_EXECUTION_GUIDE.md`: how to execute/collect evidence for the demo.
- `proofs/PARSER_TOP_LEVEL_PROOF.md`
- `proofs/CLIPPING_SAFETY_PROOF.md`
- `proofs/COLOR_SCOPE_PROOF.md`
- `proofs/GEOMETRY_INVARIANTS_PROOF.md`

## Executable Logika source

The proof-oriented Logika source files live outside the Maven source tree:

- `src/logika/ParserTopLevelProof.sc`
- `src/logika/ClippingSafetyProof.sc`
- `src/logika/ColorScopeProof.sc`
- `src/logika/GeometryInvariantsProof.sc`

These files model the proof-relevant behavior from the production Scala code in a
Sireum-friendly subset. The Markdown files in `proofs/` explain the mapping from the
production implementation to these proof models.

Local execution note: this workspace did not have `sireum` installed, so run the files
with Sireum/Logika before reporting them as tool-checked.

## Delivery note

Highlighting of the currently drawn object is intentionally out-of-scope and must be presented as a known limitation in report/demo.
