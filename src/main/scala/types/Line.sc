def drawLine(x0: Int, y0: Int, x1: Int, y1: Int): List[(Int, Int)] = {
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
      return points.reverse

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
  points.reverse
}