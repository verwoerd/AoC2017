package day03.part1

import java.io.BufferedReader
import kotlin.math.abs

/**
 * @author verwoerd
 * @since 18/11/2021
 */
fun part1(input: BufferedReader): Any {
  val target = input.readLine().toLong()
  val spiral = generateSequence(1) { it + 2 }
    .first { target <= it * it }
  val middle = spiral / 2
  val bottomRightCorner = spiral * spiral
  val bottomLeftCorner = bottomRightCorner - spiral + 1
  val topLeftCorner = bottomLeftCorner - spiral + 1
  val topRightCorner = topLeftCorner - spiral + 1
  return middle + abs(
    target - (when (target) {
      in 0..topRightCorner -> topRightCorner
      in topRightCorner..topLeftCorner -> topLeftCorner
      in topLeftCorner..bottomLeftCorner -> bottomLeftCorner
      in bottomLeftCorner..bottomRightCorner -> bottomRightCorner
      else -> error("you are doing it wrong")
    } - middle)
  )
}
