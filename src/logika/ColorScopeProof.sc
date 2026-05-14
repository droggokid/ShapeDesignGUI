// #Sireum #Logika
import org.sireum._

val BLACK: Z = 0

@pure def drawInnerColor(outerColor: Z, drawColor: Z): Z = {
  Contract(
    Ensures(Res == drawColor)
  )
  return drawColor
}

@pure def drawSiblingColor(outerColor: Z, drawColor: Z): Z = {
  Contract(
    Ensures(Res == outerColor)
  )
  return outerColor
}

@pure def fillInnerColor(outerColor: Z, fillColor: Z): Z = {
  Contract(
    Ensures(Res == fillColor)
  )
  return fillColor
}

@pure def fillSiblingColor(outerColor: Z, fillColor: Z): Z = {
  Contract(
    Ensures(Res == outerColor)
  )
  return outerColor
}

@pure def drawScopeDoesNotLeak(outerColor: Z, drawColor: Z): Unit = {
  Contract(
    Ensures(
      drawInnerColor(outerColor, drawColor) == drawColor,
      drawSiblingColor(outerColor, drawColor) == outerColor
    )
  )
}

@pure def fillScopeDoesNotLeak(outerColor: Z, fillColor: Z): Unit = {
  Contract(
    Ensures(
      fillInnerColor(outerColor, fillColor) == fillColor,
      fillSiblingColor(outerColor, fillColor) == outerColor
    )
  )
}

@pure def defaultColorOutsideDraw(drawColor: Z): Unit = {
  Contract(
    Ensures(drawSiblingColor(BLACK, drawColor) == BLACK)
  )
}
