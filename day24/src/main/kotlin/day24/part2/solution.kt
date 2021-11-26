package day24.part2

import day24.part1.Chip
import day24.part1.connectChips
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part2(input: BufferedReader): Any {
  val chips = input.lineSequence().mapIndexed { id, line ->
    val (a, b) = line.split("/").map { it.toInt() }
    Chip(id, a, b)
  }.toSet()
  val bridges = connectChips(chips)
  val longest = bridges.maxOf { it.size }
  return bridges.filter { it.size == longest }.maxOf { list -> list.sumOf { it.strength } }
}
