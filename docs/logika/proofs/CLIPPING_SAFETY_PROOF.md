# Clipping safety property (Logika artifact)

## Property

Assume active bounding box `b = (x1, y1, x2, y2)`.
Every emitted point `(x, y)` from rendering satisfies:

- `x1 <= x <= x2`
- `y1 <= y <= y2`

Likewise for emitted text coordinates.

## Code target

- `src/main/scala/types/DrawingEngine.scala`
  - `drawWithState`
  - `collectTexts`
- `src/main/scala/types/AST.scala`
  - `insideBox`

## Logika source

- `src/logika/ClippingSafetyProof.sc`

## Argument outline

1. `drawWithState`:
   - If no bounding box, no clipping claim is required.
   - If bounding box exists, `clippedPoints = points.filter(p => insideBox(p, box))`.
   - Mapped output points are built only from `clippedPoints`.
   - Hence each output point satisfies `insideBox`.

2. `collectTexts`:
   - On `TextAt(x, y, t)`, branch:
     - `Some(box) if !insideBox((x, y), box) => Nil`
     - else emit text.
   - Therefore emitted text in bounded state satisfies `insideBox`.

## Logika-style obligation summary

- **Requires:** `box` is active in state.
- **Ensures:** all returned coordinates satisfy `insideBox(_, box)`.

## Concrete witness example

`BOUNDING-BOX (0 0) (2 2)` with line `(0,0)->(4,0)`:

- generated line points include x in `[0..4]`,
- emitted points only keep x in `[0..2]`,
- therefore clipping obligation holds for outputs.
