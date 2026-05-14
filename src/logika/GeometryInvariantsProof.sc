// #Sireum #Logika
import org.sireum._

@pure def boundedAxisStep(current: Z, sign: Z, enabled: B): Z = {
  Contract(
    Requires(sign == 1 | sign == -1),
    Ensures(Res == current | Res == current + sign)
  )

  if (enabled) {
    return current + sign
  } else {
    return current
  }
}

@pure def bresenhamStepX(x: Z, sx: Z, stepX: B): Z = {
  Contract(
    Requires(sx == 1 | sx == -1),
    Ensures(Res == x | Res == x + sx)
  )
  return boundedAxisStep(x, sx, stepX)
}

@pure def bresenhamStepY(y: Z, sy: Z, stepY: B): Z = {
  Contract(
    Requires(sy == 1 | sy == -1),
    Ensures(Res == y | Res == y + sy)
  )
  return boundedAxisStep(y, sy, stepY)
}

@pure def isCircleSymmetryPoint(cx: Z, cy: Z, x: Z, y: Z, px: Z, py: Z): B = {
  Contract(
    Ensures(
      Res == (
        (px == cx + x & py == cy + y) |
          (px == cx - x & py == cy + y) |
          (px == cx + x & py == cy - y) |
          (px == cx - x & py == cy - y) |
          (px == cx + y & py == cy + x) |
          (px == cx - y & py == cy + x) |
          (px == cx + y & py == cy - x) |
          (px == cx - y & py == cy - x)
      )
    )
  )

  return (px == cx + x & py == cy + y) |
    (px == cx - x & py == cy + y) |
    (px == cx + x & py == cy - y) |
    (px == cx - x & py == cy - y) |
    (px == cx + y & py == cy + x) |
    (px == cx - y & py == cy + x) |
    (px == cx + y & py == cy - x) |
    (px == cx - y & py == cy - x)
}

@pure def circleSymmetry0(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx + x, cy + y))
  )
}

@pure def circleSymmetry1(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx - x, cy + y))
  )
}

@pure def circleSymmetry2(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx + x, cy - y))
  )
}

@pure def circleSymmetry3(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx - x, cy - y))
  )
}

@pure def circleSymmetry4(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx + y, cy + x))
  )
}

@pure def circleSymmetry5(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx - y, cy + x))
  )
}

@pure def circleSymmetry6(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx + y, cy - x))
  )
}

@pure def circleSymmetry7(cx: Z, cy: Z, x: Z, y: Z): Unit = {
  Contract(
    Ensures(isCircleSymmetryPoint(cx, cy, x, y, cx - y, cy - x))
  )
}
