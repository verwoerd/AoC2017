package day09.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/11/2021
 */
fun part1(input: BufferedReader): Any {
  return input.readLine().fold(GarbageState()) { state, c ->
    when {
      state.ignore -> state.copy(ignore = false)
      c == '!' -> state.copy(ignore = true)
      state.garbage && c == '>' -> state.copy(garbage = false)
      state.garbage -> state
      c == '<' -> state.copy(garbage = true)
      c == '{' -> state.copy(score = state.score + state.depth, depth = state.depth + 1)
      c == '}' -> state.copy(depth = state.depth - 1)
      c == ',' -> state
      else -> error("weird situation: c=$c, state=$state")
    }
  }.score
}

data class GarbageState(
  val score: Long = 0,
  val depth: Long = 1,
  val ignore: Boolean = false,
  val garbage: Boolean = false,
  val garbageCount: Long = 0
)
