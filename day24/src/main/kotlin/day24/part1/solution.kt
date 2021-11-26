package day24.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part1(input: BufferedReader): Any {
  val chips = input.lineSequence().mapIndexed { id, line ->
    val (a, b) = line.split("/").map { it.toInt() }
    Chip(id, a, b)
  }.toSet()
  return connectChips(chips).maxOf { list -> list.sumOf { it.strength } }
}

fun connectChips(
  chips: Set<Chip>,
  bridge: List<Chip> = emptyList(),
  pins: Int = 0
): List<List<Chip>> {
  val options = chips.filter { it.hasPins(pins) }
  return when (options.size) {
    0 -> listOf(bridge)
    else ->
      options.flatMap { current ->
        connectChips(chips - current, bridge + current, current.otherSide(pins))
      }
  }
}

data class Chip(
  val id: Int,
  val leftPins: Int,
  val rightPins: Int
) {
  val strength = leftPins + rightPins


  fun otherSide(pins: Int) = when (pins) {
    leftPins -> rightPins
    else -> leftPins
  }

  fun hasPins(pins: Int) = leftPins == pins || rightPins == pins
}
