// #Sireum #Logika
import org.sireum._

val BOUNDING_BOX: Z = 0

@pure def isBoundingBox(commandTag: Z): B = {
  Contract(
    Ensures(Res == (commandTag == BOUNDING_BOX))
  )
  return commandTag == BOUNDING_BOX
}

@pure def countBoundingBoxes(commands: ISZ[Z]): Z = {
  Contract(
    Ensures(T)
  )

  var i: Z = 0
  var count: Z = 0

  while (i < commands.size) {
    Invariant(
      Modifies(i, count),
      i >= 0,
      i <= commands.size,
      count >= 0,
      count <= i
    )

    if (isBoundingBox(commands(i))) {
      count = count + 1
    }
    i = i + 1
  }

  return count
}

@pure def validTopLevel(commands: ISZ[Z]): B = {
  if (commands.size <= 0) {
    return F
  } else {
    return isBoundingBox(commands(0)) & countBoundingBoxes(commands) == 1
  }
}

@pure def parserAccepts(sizePositive: B, firstIsBox: B, oneBox: B): B = {
  Contract(
    Ensures(Res == (sizePositive & firstIsBox & oneBox))
  )
  return sizePositive & firstIsBox & oneBox
}

@pure def acceptedImpliesNonEmpty(sizePositive: B, firstIsBox: B, oneBox: B): Unit = {
  Contract(
    Requires(
      parserAccepts(sizePositive, firstIsBox, oneBox),
      sizePositive
    ),
    Ensures(sizePositive)
  )
}

@pure def acceptedImpliesFirstBox(sizePositive: B, firstIsBox: B, oneBox: B): Unit = {
  Contract(
    Requires(
      parserAccepts(sizePositive, firstIsBox, oneBox),
      firstIsBox
    ),
    Ensures(firstIsBox)
  )
}

@pure def acceptedImpliesOneBox(sizePositive: B, firstIsBox: B, oneBox: B): Unit = {
  Contract(
    Requires(
      parserAccepts(sizePositive, firstIsBox, oneBox),
      oneBox
    ),
    Ensures(oneBox)
  )
}
