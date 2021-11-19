package day07.part2

import day07.part1.Node
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part2(input: BufferedReader): Any {
  val map = input.lineSequence()
    .map { Node.parseFromString(it) }.associateBy { it.label }
  val root = map.keys.first { label -> map.values.none { label in it.children } }
  return map.findUnbalancedNode(map[root]!!).second
}


tailrec fun Map<String, Node>.findUnbalancedNode(current: Node, diff: Long = 0): Pair<Node, Long> {
  val weights = current.children.map { it to findWeight(it) }
  return if (weights.map { it.second }.distinct().size == 1) {
    current to current.weight + diff
  } else {
    val unbalanced = weights.first { (_, weight) -> weights.count { it.second == weight } == 1 }
    findUnbalancedNode(
      get(unbalanced.first)!!,
      weights.first { it.second != unbalanced.second }.second - unbalanced.second
    )
  }
}

fun Map<String, Node>.findWeight(current: String): Long {
  val root = get(current) ?: error("no such node $current")
  return when {
    root.children.isEmpty() -> root.weight
    else -> root.weight + root.children.sumOf { findWeight(it) }
  }
}
