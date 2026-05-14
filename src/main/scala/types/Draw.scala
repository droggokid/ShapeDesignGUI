package types

import scala.jdk.CollectionConverters._

object Draw {
  def drawLineSc(x0: Int, y0: Int, x1: Int, y1: Int): List[(Int, Int)] = {
    var points = List.empty[(Int, Int)]
    var x = x0
    var y = y0
    val dx = math.abs(x1 - x0)
    val dy = math.abs(y1 - y0)
    val sx = if (x0 < x1) 1 else -1
    val sy = if (y0 < y1) 1 else -1
    var err = dx - dy

    while (true) {
      points = (x, y) :: points

      if (x == x1 && y == y1)
        return points

      val e2 = 2 * err

      if (e2 > -dy) {
        err -= dy
        x += sx
      }

      if (e2 < dx) {
        err += dx
        y += sy
      }
    }
    points
  }

  def drawLine(x0: Int, y0: Int, x1: Int, y1: Int): java.util.List[(Int, Int)] = {
    drawLineSc(x0, y0, x1, y1).reverse.asJava
  }


  def drawRectangleSc(x0: Int, y0: Int, x1: Int, y1: Int): List[(Int, Int)] = {
    var points = List.empty[(Int, Int)]

    points = drawLineSc(x0, y0, x1, y0) ::: points
    points = drawLineSc(x1, y0, x1, y1) ::: points
    points = drawLineSc(x1, y1, x0, y1) ::: points
    points = drawLineSc(x0, y1, x0, y0) ::: points

    points
  }

  def drawRectangle(x0: Int, y0: Int, x1: Int, y1: Int): java.util.List[(Int, Int)] = {
    drawRectangleSc(x0, y0, x1, y1).reverse.asJava
  }

  def drawCircleSc(x0: Int, y0: Int, r: Int): List[(Int, Int)] = {
    var points = List.empty[(Int, Int)]
    var x = r
    var y = 0
    var p = 1 - r

    while (x >= y) {

      // Add the 8 symmetric points
      points = (x0 + x, y0 + y) :: points
      points = (x0 - x, y0 + y) :: points
      points = (x0 + x, y0 - y) :: points
      points = (x0 - x, y0 - y) :: points
      points = (x0 + y, y0 + x) :: points
      points = (x0 - y, y0 + x) :: points
      points = (x0 + y, y0 - x) :: points
      points = (x0 - y, y0 - x) :: points

      y += 1

      if (p <= 0) {
        p = p + 2 * y + 1
      } else {
        x -= 1
        p = p + 2 * y - 2 * x + 1
      }
    }

    points
  }

  def drawCircle(x0: Int, y0: Int, r: Int): java.util.List[(Int, Int)] = {
    drawCircleSc(x0, y0, r).reverse.asJava
  }
}
