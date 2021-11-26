package day22.part1

import Coordinate
import FourDirections.DOWN
import xRange
import yRange
import java.io.BufferedReader
import kotlin.math.sqrt

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part1(input: BufferedReader, bursts: Int = 10_000): Any {
  val state = input.lineSequence()
    .flatMapIndexed { y, line -> line.mapIndexed { x, char -> Coordinate(x, y) to (char == '#') } }
    .toMap(mutableMapOf()).withDefault { false }
  val middle = sqrt(state.size.toDouble()).toInt() / 2
  var current = Coordinate(middle, middle)
  var direction = DOWN
  var infected = 0
  repeat(bursts) {
    val isInfected = state.getValue(current)
    direction = when (isInfected) {
      true -> direction.turnLeft()
      else -> direction.turnRight().also { infected++ }
    }
    state[current] = !isInfected
    current = direction + current
  }
  return infected
}


fun Map<Coordinate, Boolean>.printState(current: Coordinate) {
  val yRange = yRange()
  val xRange = xRange()
  println((yRange.first..yRange.second).map { y ->
    (xRange.first..xRange.second).map { x ->
      val coordinate = Coordinate(x, y)
      when {
        coordinate == current && getValue(coordinate) -> '*'
        coordinate == current -> ':'
        getValue(coordinate) -> '#'
        else -> '.'
      }
    }.joinToString("")
  }.joinToString("\n"))
}
