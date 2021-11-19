package day07.part1

import day07.part1.Node.Companion.parseFromString
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part1(input: BufferedReader): Any {
  val map = input.lineSequence()
    .map { parseFromString(it) }.associateBy { it.label }
  return map.keys.first { label -> map.values.none { label in it.children } }
}

val ENTRY_REGEX = Regex("(\\w+) \\((\\d+)\\)( -> ([\\w, ]+))?")

data class Node(
  val label: String,
  val weight: Long,
  val children: Set<String>
) {
  companion object {
    fun parseFromString(line: String): Node {
      val (_, label, weight, _, kids) = ENTRY_REGEX.matchEntire(line)!!.groupValues
      return Node(label, weight.toLong(), kids.split(", ").filter { it.isNotBlank() }.toSet())
    }
  }
}
