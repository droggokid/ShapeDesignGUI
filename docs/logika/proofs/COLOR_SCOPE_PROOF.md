# Color scope property (Logika artifact)

## Property

Color state updates are lexical:

1. `DRAW c (...)` applies `c` only to its inner command list.
2. `FILL c g` applies `c` only while evaluating `g`.
3. Sibling commands outside the scope retain prior color.

## Code target

- `src/main/scala/types/DrawingEngine.scala`
  - `renderCommands`
  - `fillCommand`
  - `collectTexts`

## Logika source

- `src/logika/ColorScopeProof.sc`

## Argument outline

1. In `renderCommands`:
   - for `DrawCommand(color, innerCommands)`:
     - recurse on `innerCommands` with `state.copy(currentColor = color)`,
     - recurse on `rest` with original `state`.
   - This structurally restores color for siblings.

2. For `Fill(color, command)`:
   - evaluate `fillCommand(command, state.copy(currentColor = color))`,
   - then recurse on `rest` with original `state`.

3. The same scoped pattern is used in `collectTexts`.

Hence color does not leak outside the recursive subtree where it is introduced.

## Logika-style obligation summary

- **Requires:** rendering state `s`.
- **Ensures:** color in output for subtree `t` is based on `s.currentColor` overridden only by explicit scoped constructs inside `t`.

## Concrete witness example

Program:

1. `(DRAW red (LINE ...))`
2. `(LINE ...)`

Expected:
- first line points have color `red`,
- second line points have color `black` (default outer state).
