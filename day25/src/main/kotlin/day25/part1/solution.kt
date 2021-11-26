package day25.part1

import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part1(input: BufferedReader): Any {
  var currentState = input.readLine().dropLast(1).last()
  val steps = input.readLine().split(" ")[5].toInt()
  val lines = input.readLines().toCollection(LinkedList())
  val states = mutableMapOf<Char, State>()
  var position = 0
  val tape = mutableMapOf<Int, Int>().withDefault { 0 }
  while (lines.isNotEmpty()) {
    lines.removeFirst()
    val name = lines.removeFirst().dropLast(1).last()
    lines.removeFirst()
    val falseWrite = if (lines.removeFirst().dropLast(1).last() == '1') 1 else 0
    val falseMove = if (lines.removeFirst().endsWith("right.")) 1 else -1
    val falseState = lines.removeFirst().dropLast(1).last()
    lines.removeFirst()
    val trueWrite = if (lines.removeFirst().dropLast(1).last() == '1') 1 else 0
    val trueMove = if (lines.removeFirst().endsWith("right.")) 1 else -1
    val trueState = lines.removeFirst().dropLast(1).last()
    states[name] = State(name, falseWrite, falseMove, falseState, trueWrite, trueMove, trueState)
  }
  repeat(steps) {
    val state = states[currentState]!!
    if (tape.getValue(position) == 0) {
      tape[position] = state.falseWrite
      position += state.falseDiff
      currentState = state.falseState
    } else {
      tape[position] = state.trueWrite
      position += state.trueDiff
      currentState = state.trueState
    }
  }
  return tape.count { it.value == 1 }
}

data class State(
  val name: Char,
  val falseWrite: Int,
  val falseDiff: Int,
  val falseState: Char,
  val trueWrite: Int,
  val trueDiff: Int,
  val trueState: Char,
)
