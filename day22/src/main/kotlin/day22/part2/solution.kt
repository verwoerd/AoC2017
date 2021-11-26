package day22.part2

import Coordinate
import FourDirections
import java.io.BufferedReader
import kotlin.math.sqrt

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part2(input: BufferedReader, bursts: Int = 10000000): Any {
  val state = input.lineSequence()
    .flatMapIndexed { y, line ->
      line.mapIndexed { x, char ->
        Coordinate(x, y) to when (char) {
          '#' -> NodeState.INFECTED
          else -> NodeState.CLEAN
        }
      }
    }
    .toMap(mutableMapOf()).withDefault { NodeState.CLEAN }
  val middle = sqrt(state.size.toDouble()).toInt() / 2
  var current = Coordinate(middle, middle)
  var direction = FourDirections.DOWN
  var infected = 0
  repeat(bursts) {
    val isInfected = state.getValue(current)
    direction = when (isInfected) {
      NodeState.CLEAN -> direction.turnRight()
      NodeState.WEAKEND -> direction.also { infected++ }
      NodeState.INFECTED -> direction.turnLeft()
      NodeState.FLAGGED -> direction.turnAround()
    }
    state[current] = isInfected.transition()
    current = direction + current
  }
  return infected
}

enum class NodeState {
  CLEAN, WEAKEND, INFECTED, FLAGGED;

  fun transition() = when (this) {
    CLEAN -> WEAKEND
    WEAKEND -> INFECTED
    INFECTED -> FLAGGED
    FLAGGED -> CLEAN
  }
}
