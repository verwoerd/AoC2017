package day19.part1

import Coordinate
import FourDirections.*
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/11/2021
 */
fun part1(input: BufferedReader): Any {
  return walkThePath(input).first
}

fun walkThePath(input: BufferedReader): Pair<String, Int> {
  val map = input.lineSequence()
    .map { it.toCharArray() }
    .flatMapIndexed { y, chars ->
      chars.mapIndexed { x, char -> Coordinate(x, y) to char }
    }.toMap()
  var current = map.filter { it.key.y == 0 }.firstNotNullOf { entry -> entry.key.takeIf { entry.value == '|' } }
  var path = ""
  var direction = UP
  var steps = 0
  while (true) {
    steps++
    current = direction + current
    when (val next = map[current]!!) {
      ' ' -> break
      '|', '-' -> continue
      '+' -> direction = when (direction) {
        UP, DOWN -> when (map[LEFT + current]) {
          ' ', null -> RIGHT
          else -> LEFT
        }
        LEFT, RIGHT -> when (map[DOWN + current]) {
          ' ', null -> UP
          else -> DOWN
        }
      }
      else -> path += next
    }
  }
  return path to steps
}
