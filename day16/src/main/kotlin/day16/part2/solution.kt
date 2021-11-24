package day16.part2

import day16.part1.executeDance
import day16.part1.parseDance
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 24/11/2021
 */
fun part2(input: BufferedReader): Any {
  val dance = parseDance(input)
  var position = (0 until 16).map { ('a'.code + it).toChar() }.toCollection(LinkedList())
  val positions = mutableSetOf<String>(position.joinToString(""))
  do {
    position = dance.executeDance(position)
  } while (positions.add(position.joinToString("")))

  val finishString = position.joinToString("")
  val stepsToFirstRepetition = positions.indexOf(finishString)
  val repetitionSize = positions.size - stepsToFirstRepetition
  val target = (1_000_000_000 - stepsToFirstRepetition) % repetitionSize

  return positions.drop(target + stepsToFirstRepetition).first()
}
