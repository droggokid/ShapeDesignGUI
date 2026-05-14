// #Sireum #Logika
import org.sireum._

@pure def insideBox(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): B = {
  Contract(
    Ensures(Res == (px >= x1 & px <= x2 & py >= y1 & py <= y2))
  )
  return px >= x1 & px <= x2 & py >= y1 & py <= y2
}

@pure def keepPointAfterClip(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): B = {
  Contract(
    Ensures(Res == insideBox(px, py, x1, y1, x2, y2))
  )
  return insideBox(px, py, x1, y1, x2, y2)
}

@pure def emittedPointX(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): Z = {
  Contract(
    Requires(keepPointAfterClip(px, py, x1, y1, x2, y2)),
    Ensures(
      Res == px,
      px >= x1,
      px <= x2
    )
  )
  return px
}

@pure def emittedPointY(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): Z = {
  Contract(
    Requires(keepPointAfterClip(px, py, x1, y1, x2, y2)),
    Ensures(
      Res == py,
      py >= y1,
      py <= y2
    )
  )
  return py
}

@pure def emittedTextX(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): Z = {
  Contract(
    Requires(insideBox(px, py, x1, y1, x2, y2)),
    Ensures(
      Res == px,
      px >= x1,
      px <= x2
    )
  )
  return px
}

@pure def emittedTextY(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): Z = {
  Contract(
    Requires(insideBox(px, py, x1, y1, x2, y2)),
    Ensures(
      Res == py,
      py >= y1,
      py <= y2
    )
  )
  return py
}

@pure def droppedPointIsOutside(px: Z, py: Z, x1: Z, y1: Z, x2: Z, y2: Z): Unit = {
  Contract(
    Requires(!keepPointAfterClip(px, py, x1, y1, x2, y2)),
    Ensures(!insideBox(px, py, x1, y1, x2, y2))
  )
}

@pure def clippingWitness(): Unit = {
  Contract(
    Ensures(
      keepPointAfterClip(0, 0, 0, 0, 2, 2),
      keepPointAfterClip(2, 0, 0, 0, 2, 2),
      !keepPointAfterClip(4, 0, 0, 0, 2, 2)
    )
  )
}
