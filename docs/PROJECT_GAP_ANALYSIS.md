# ShapeDesignGUI — Project Status, Gaps, and Delivery Plan

## 1. Project summary
This project is a mixed **Java (Swing GUI)** + **Scala (parser + drawing engine)** application for a small graphics language.  
Current architecture already follows the core split from the coursework: Java handles UI, while Scala owns parsing/rendering state (bounding box + color context).

## 2. What is already implemented
Based on `docs/Coursework.pdf` requirements and the current codebase:

| Requirement | Status | Notes |
|---|---|---|
| GUI with viewer (left) + editor (right) | ✅ Implemented | `Main.java` uses `JSplitPane` with `GridBackgroundPanel` + `JTextArea` |
| Grid display in viewer | ✅ Implemented | `GridBackgroundPanel.drawGrid` |
| Language parser for `BOUNDING-BOX`, `LINE`, `RECTANGLE`, `CIRCLE`, `TEXT-AT`, `DRAW`, `FILL` | ✅ Implemented | `Parser.scala` |
| Bresenham line algorithm | ✅ Implemented | `Draw.drawLineSc` |
| Midpoint circle algorithm | ✅ Implemented | `Draw.drawCircleSc` |
| Clipping to bounding box | ✅ Mostly implemented | Points/text are clipped in `DrawingEngine` |
| `DRAW` and `FILL` color behavior | ✅ Implemented | `DrawingState.currentColor` + recursive render logic |
| Program passed directly from editor to Scala without rewriting | ✅ Implemented | `editor.getText()` is passed directly into Scala methods |
| Demo examples (pie/bar style) | ✅ Implemented | Added reusable files in `docs/examples/` and UI buttons to load both |

## 3. What is still missing (important gaps)

### A) “Currently drawn object should be highlighted”
**Status:** 🚫 Intentionally not implemented  
This feature is currently treated as out-of-scope for delivery. We will document this explicitly as a known limitation in README/report/demo notes.

### B) Error reporting panel/flow
**Status:** ✅ Implemented  
The UI now includes a dedicated error panel and surfaces parser/runtime errors directly during draw.

### C) “Bounding box must be first command” validation
**Status:** ✅ Implemented  
Parser now enforces that `BOUNDING-BOX` is the first command and appears exactly once at top level.

### D) Coursework correctness/testing strategy (Sireum Logika + tests)
**Status:** ⚠️ Authored, execution pending  
Initial unit-test scaffolding is in place, and Logika proof-oriented source files are now under `src/logika/`. They still need to be run on a machine with Sireum/Logika installed before claiming tool-checked proof results.

### E) Demonstration scenarios as reproducible assets
**Status:** ⚠️ Incomplete  
There are embedded editor examples, but no dedicated demo scripts/files for:
1. one bar chart with text labels,
2. one pie chart with text labels (3 bars / 5 segments wording in document should be clarified before demo).

## 4. How to finish the project (concrete execution plan)

## Phase 1 — Validation + UX hardening
1. ✅ Enforced grammar rule: first command must be `BOUNDING-BOX`.
2. ✅ Added explicit parser/engine error propagation to Java.
3. ✅ Added error panel in right side UI (below editor) to display parse/runtime errors clearly.
4. ✅ Draw now exits safely with explicit error feedback when program is invalid.

## Phase 2 — Scope declaration (highlight excluded)
1. Mark highlight support as intentionally out-of-scope in `README.md`.
2. Include the same limitation in report/demo talking points to avoid ambiguity during assessment.

## Phase 3 — Demo completeness
1. ✅ Added ready-to-load example programs under `docs/examples/`:
   - `bar-chart.shape`
   - `pie-chart.shape`
2. ✅ Added UI buttons to load these examples quickly.
3. ⏳ Rendering confirmation still pending in an environment with Maven/runtime available.

## Phase 4 — Coursework correctness evidence (critical)
1. ⚠️ Sireum Logika artifacts:
   - ✅ define formal properties/invariants for parser and geometry primitives,
   - ✅ add Logika-oriented checks/proofs for selected core functions (e.g., line/circle stepping invariants, bbox clipping conditions, command-order constraints),
   - ⏳ run the proof files with Sireum/Logika and keep the output as demo/report evidence.
2. ✅ Runtime test scaffolding added:
   - parser success/failure cases,
   - bbox-first rule,
   - clipping behavior,
   - `DRAW`/`FILL` color scope.
3. ✅ Java-focused component tests added:
   - coordinate transform behavior in `GridBackgroundPanel`.
4. ⏳ Add at least one end-to-end smoke test path (valid script -> non-empty rendered output).

## Phase 5 — Hand-in readiness
1. Update `README.md` with:
   - ✅ how to run,
   - ✅ known limitations,
   - ✅ correctness track references.
2. ⏳ Produce short report content from the test strategy and implementation decisions.

## 5. Immediate blockers / setup issues
- The repository currently has no Maven wrapper script (`mvnw`), and both Maven/Scala CLI tools were unavailable in this environment (`mvn` and `scalac` not found), so automated test/build execution could not be run here.
- Add `mvnw` (recommended) or ensure Maven + Scala toolchain are installed on demo machines for reproducible runs.
