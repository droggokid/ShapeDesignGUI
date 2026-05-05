package types

import types.AST._

object Parser {

  def parse(program: String): List[Command] = {
    val tokens = tokenize(program)
    val (commands, rest) = parseMany(tokens)

    if (rest.nonEmpty)
      throw new RuntimeException("Unexpected tokens: " + rest.mkString(" "))

    commands
  }

  private def tokenize(input: String): List[String] = {
    val tokenPattern = "\"[^\"]*\"|\\(|\\)|[^\\s()]+".r
    tokenPattern.findAllIn(input).toList
  }

  private def parseMany(tokens: List[String]): (List[Command], List[String]) = {
    var rest = tokens
    var commands = List.empty[Command]

    while (rest.nonEmpty) {
      val (command, newRest) = parseCommand(rest)
      commands = commands :+ command
      rest = newRest
    }

    (commands, rest)
  }

  private def parseCommand(tokens: List[String]): (Command, List[String]) = {
    tokens match {
      case "(" :: "BOUNDING-BOX" :: rest =>
        val ((x1, y1), rest1) = parsePoint(rest)
        val ((x2, y2), rest2) = parsePoint(rest1)
        expectClose(rest2) match {
          case rest3 => (BoundingBox(x1, y1, x2, y2), rest3)
        }

      case "(" :: "LINE" :: rest =>
        val ((x1, y1), rest1) = parsePoint(rest)
        val ((x2, y2), rest2) = parsePoint(rest1)
        val rest3 = expectClose(rest2)
        (Line(x1, y1, x2, y2), rest3)

      case "(" :: "RECTANGLE" :: rest =>
        val ((x1, y1), rest1) = parsePoint(rest)
        val ((x2, y2), rest2) = parsePoint(rest1)
        val rest3 = expectClose(rest2)
        (Rectangle(x1, y1, x2, y2), rest3)

      case "(" :: "CIRCLE" :: rest =>
        val ((x, y), rest1) = parsePoint(rest)
        val r :: rest2 = rest1
        val rest3 = expectClose(rest2)
        (Circle(x, y, r.toInt), rest3)

      case "(" :: "TEXT-AT" :: rest =>
        val ((x, y), rest1) = parsePoint(rest)
        val rawText :: rest2 = rest1
        val rest3 = expectClose(rest2)
        (TextAt(x, y, unquote(rawText)), rest3)

      case "(" :: "DRAW" :: color :: rest =>
        val (innerCommands, rest1) = parseUntilClose(rest)
        (DrawCommand(color, innerCommands), rest1)

      case "(" :: "FILL" :: color :: rest =>
        val (command, rest1) = parseCommand(rest)
        val rest2 = expectClose(rest1)
        (Fill(color, command), rest2)

      case other =>
        throw new RuntimeException("Could not parse command near: " + other.take(10).mkString(" "))
    }
  }

  private def parseUntilClose(tokens: List[String]): (List[Command], List[String]) = {
    var rest = tokens
    var commands = List.empty[Command]

    while (rest.nonEmpty && rest.head != ")") {
      val (command, newRest) = parseCommand(rest)
      commands = commands :+ command
      rest = newRest
    }

    val restAfterClose = expectClose(rest)
    (commands, restAfterClose)
  }

  private def parsePoint(tokens: List[String]): ((Int, Int), List[String]) = {
    tokens match {
      case "(" :: x :: y :: ")" :: rest =>
        ((x.toInt, y.toInt), rest)

      case other =>
        throw new RuntimeException("Expected point near: " + other.take(10).mkString(" "))
    }
  }

  private def expectClose(tokens: List[String]): List[String] = {
    tokens match {
      case ")" :: rest => rest
      case other =>
        throw new RuntimeException("Expected ')' near: " + other.take(10).mkString(" "))
    }
  }

  private def unquote(token: String): String = {
    if (token.length >= 2 && token.startsWith("\"") && token.endsWith("\"")) {
      token.substring(1, token.length - 1)
    } else token
  }
}
