package day04.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/11/2021
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .count { line ->
      val words = line.split(" ")
      words.size == words.distinct().size &&
          words.groupBy { it.length }.values.none { list ->
            list.any { word ->
              list.filter { it != word }.any { other -> word.isAnagram(other) }
            }
          }
    }
}

val String.countMap: Map<Char, Int>
  get() = groupBy { it }.mapValues { it.value.size }

fun String.isAnagram(other: String) =
  countMap == other.countMap && all { it in other }
