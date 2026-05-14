# Parser top-level property (Logika artifact)

## Property

For any program `p`, if `Parser.parse(p)` returns normally (no exception), then:

1. `commands.nonEmpty`
2. `commands.head` is `BoundingBox`
3. `commands.count(isBoundingBox) == 1`

## Code target

- `src/main/scala/types/Parser.scala`
- `parse`
- `validateTopLevel`

## Logika source

- `src/logika/ParserTopLevelProof.sc`

## Argument outline

1. `parse` computes `commands`, then calls `validateTopLevel(commands)` before returning.
2. In `validateTopLevel`:
   - if `commands.isEmpty`, exception is thrown.
   - if `commands.head` is not `BoundingBox`, exception is thrown.
   - if `boxCount != 1`, exception is thrown.
3. Therefore, normal return from `parse` implies all three checks passed.

## Logika-style obligation summary

- **Requires:** none (input is any `String`).
- **Ensures on normal return:**
  - `commands.size > 0`
  - `commands(0)` is `BoundingBox`
  - `countBoundingBox(commands) = 1`

## Concrete witness examples

- Valid:
  - `(BOUNDING-BOX (0 0) (10 10)) (LINE (0 0) (1 1))`
- Invalid (fails condition 2):
  - `(LINE (0 0) (1 1)) (BOUNDING-BOX (0 0) (10 10))`
- Invalid (fails condition 3):
  - `(BOUNDING-BOX (0 0) (10 10)) (BOUNDING-BOX (1 1) (2 2))`
