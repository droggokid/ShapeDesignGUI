package types

object AST {
  sealed trait Command

  case class BoundingBox(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
  case class Line(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
  case class Rectangle(x1: Int, y1: Int, x2: Int, y2: Int) extends Command
  case class Circle(x: Int, y: Int, r: Int) extends Command
  case class TextAt(x: Int, y: Int, text: String) extends Command
  case class DrawCommand(color: String, commands: List[Command]) extends Command
  case class Fill(color: String, command: Command) extends Command

  case class DrawingState(boundingBox: Option[BoundingBox], currentColor: String = "black")

  def insideBox(p: (Int, Int), box: BoundingBox): Boolean = {
    val (x,y) = p
    x >= box.x1 && x <=  box.x2 && y >= box.y1 && y <= box.y2
  }
}