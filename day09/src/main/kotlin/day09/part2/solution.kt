package day09.part2

import day09.part1.GarbageState
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/11/2021
 */
fun part2(input: BufferedReader): Any {
  return input.readLine().fold(GarbageState()) { state, c ->
    when {
      state.ignore -> state.copy(ignore = false)
      c == '!' -> state.copy(ignore = true)
      state.garbage && c == '>' -> state.copy(garbage = false)
      state.garbage -> state.copy(garbageCount = state.garbageCount + 1)
      c == '<' -> state.copy(garbage = true)
      c == '{' -> state.copy(score = state.score + state.depth, depth = state.depth + 1)
      c == '}' -> state.copy(depth = state.depth - 1)
      c == ',' -> state
      else -> error("weird situation: c=$c, state=$state")
    }
  }.garbageCount
}
