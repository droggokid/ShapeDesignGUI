package types

import types.AST._
import types.Draw._
import scala.jdk.CollectionConverters._

case class DrawablePoint(x: Int, y: Int, color: String)
case class DrawableText(x: Int, y: Int, text: String, color: String)

object DrawingEngine {
  def render(commands: List[Command]): List[DrawablePoint] = {
    val initialState = DrawingState(None)
    renderCommands(commands, initialState)
  }

  private def renderCommands(
      commands: List[Command],
      state: DrawingState
  ): List[DrawablePoint] = {

    commands match {
      case Nil => Nil

      case (box: BoundingBox) :: rest =>
        renderCommands(rest, state.copy(boundingBox = Some(box)))

      case (line: Line) :: rest =>
        drawWithState(
          drawLineSc(line.x1, line.y1, line.x2, line.y2).reverse,
          state
        ) ::: renderCommands(rest, state)

      case (rect: Rectangle) :: rest =>
        drawWithState(
          drawRectangleSc(rect.x1, rect.y1, rect.x2, rect.y2).reverse,
          state
        ) ::: renderCommands(rest, state)

      case (circle: Circle) :: rest =>
        drawWithState(
          drawCircleSc(circle.x, circle.y, circle.r).reverse,
          state
        ) ::: renderCommands(rest, state)

      case DrawCommand(color, innerCommands) :: rest =>
        renderCommands(innerCommands, state.copy(currentColor = color)) :::
          renderCommands(rest, state)

      case Fill(color, command) :: rest =>
        fillCommand(command, state.copy(currentColor = color)) :::
          renderCommands(rest, state)

      case TextAt(_, _, _) :: rest =>
        renderCommands(rest, state)
    }
  }

  private def drawWithState(
      points: List[(Int, Int)],
      state: DrawingState
  ): List[DrawablePoint] = {
    val clippedPoints = state.boundingBox match {
      case Some(box) => points.filter(p => insideBox(p, box))
      case None      => points
    }

    clippedPoints.map { case (x, y) =>
      DrawablePoint(x, y, state.currentColor)
    }
  }

  private def fillCommand(
      command: Command,
      state: DrawingState
  ): List[DrawablePoint] = {
    command match {
      case Rectangle(x1, y1, x2, y2) =>
        val minX = math.min(x1, x2)
        val maxX = math.max(x1, x2)
        val minY = math.min(y1, y2)
        val maxY = math.max(y1, y2)
        val points = (for {
          x <- minX to maxX
          y <- minY to maxY
        } yield (x, y)).toList
        drawWithState(points, state)

      case Circle(cx, cy, r) =>
        val points = (for {
          y <- (cy - r) to (cy + r)
          dy = y - cy
          dx = math.sqrt((r * r - dy * dy).toDouble).toInt
          x <- (cx - dx) to (cx + dx)
        } yield (x, y)).toList
        drawWithState(points, state)

      case Line(x1, y1, x2, y2) =>
        drawWithState(drawLineSc(x1, y1, x2, y2).reverse, state)

      case DrawCommand(_, innerCommands) =>
        innerCommands.flatMap(inner => fillCommand(inner, state))

      case Fill(innerColor, innerCommand) =>
        fillCommand(innerCommand, state.copy(currentColor = innerColor))

      case _ =>
        Nil
    }
  }

  def interpret(program: String): java.util.List[DrawablePoint] = {
    val commands = Parser.parse(program)
    render(commands).asJava
  }

  def interpretTexts(program: String): java.util.List[DrawableText] = {
    val commands = Parser.parse(program)
    collectTexts(commands, DrawingState(None)).asJava
  }

  private def collectTexts(
      commands: List[Command],
      state: DrawingState
  ): List[DrawableText] = {
    commands match {
      case Nil => Nil

      case (box: BoundingBox) :: rest =>
        collectTexts(rest, state.copy(boundingBox = Some(box)))

      case DrawCommand(color, innerCommands) :: rest =>
        collectTexts(innerCommands, state.copy(currentColor = color)) :::
          collectTexts(rest, state)

      case Fill(color, command) :: rest =>
        collectTexts(List(command), state.copy(currentColor = color)) :::
          collectTexts(rest, state)

      case TextAt(x, y, text) :: rest =>
        val currentText = state.boundingBox match {
          case Some(box) if !insideBox((x, y), box) => Nil
          case _ => List(DrawableText(x, y, text, state.currentColor))
        }
        currentText ::: collectTexts(rest, state)

      case _ :: rest =>
        collectTexts(rest, state)
    }
  }

  def getBoundingBox(program: String): java.util.Optional[AST.BoundingBox] = {
    val commands = Parser.parse(program)

    commands.collectFirst {
      case box: BoundingBox => box
    } match {
      case Some(box) => java.util.Optional.of(box)
      case None      => java.util.Optional.empty()
    }
  }
}
