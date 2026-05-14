# Geometry invariants (Logika artifact)

## A) Bresenham line stepping

### Property

For each loop iteration in `drawLineSc`:

1. The emitted point changes by at most one unit per axis from the previous emitted point.
2. Progress is monotonic toward target endpoint using `sx` and `sy`.
3. Termination occurs when `(x, y) == (x1, y1)`.

### Code target

- `src/main/scala/types/Draw.scala`
  - `drawLineSc`

## Logika source

- `src/logika/GeometryInvariantsProof.sc`

### Argument outline

1. `sx` and `sy` are each either `+1` or `-1`.
2. In one loop step:
   - `x` is unchanged or incremented by `sx`,
   - `y` is unchanged or incremented by `sy`.
3. Therefore per-step deltas satisfy `|Δx| <= 1`, `|Δy| <= 1`.
4. Error term `err` steers updates so the path converges to endpoint.
5. Exact endpoint check is explicit branch condition for return.

## B) Midpoint circle symmetry

### Property

Each iteration of `drawCircleSc` emits 8 symmetric points around center `(x0, y0)`:

- `(±x, ±y)` and `(±y, ±x)` translated by center.

### Code target

- `src/main/scala/types/Draw.scala`
  - `drawCircleSc`

### Argument outline

1. Every loop body appends exactly the 8 transformed tuples.
2. These tuples represent the orbit of one octant point under circle symmetry.
3. `x` and `y` updates preserve traversal from 45° boundary to axis while maintaining midpoint decision logic.

## Logika-style obligation summary

- line: bounded incremental movement + eventual endpoint hit,
- circle: per-iteration 8-way symmetric emission.
