package day21.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/11/2021
 */
fun part1(input: BufferedReader, iterations: Int = 5): Any {
  val rules = input.lineSequence().map { it.split(" => ").take(2) }
    .flatMap { (a, b) ->
      generateSequence(a) { it.rotateRule() }.take(4).map { it to b } +
          generateSequence(a.flipRule()) { it.rotateRule() }.take(4).map { it to b }
    }
    .toMap()
  var current = listOf(listOf('.', '#', '.'), listOf('.', '.', '#'), listOf('#', '#', '#'))
  repeat(iterations) {
    current = current.divide().map { rules[it]!! }.merge(current.first().size)
//    println(current.joinToString("\n") { it.joinToString("") })
  }
  return current.sumOf { list -> list.count { it == '#' } }
}

private fun List<String>.merge(oldSize: Int): List<List<Char>> {
  var factor = 3
  val width = when {
    oldSize % 2 == 0 -> oldSize / 2
    oldSize % 3 == 0 -> oldSize / 3.also { factor = 4 }
    else -> error("invalid value")
  }
  return foldIndexed(mutableListOf<MutableList<Char>>()) { index, acc, s ->

    if (index % width == 0) {
      acc.add(mutableListOf())
      acc.add(mutableListOf())
      acc.add(mutableListOf())
      if (factor == 4) acc.add(mutableListOf())
    }
    s.split("/").forEachIndexed { i, it -> it.toCollection(acc[index / width * factor + i]) }
    acc
  }
}


fun String.rotateRule() = when (length) {
  5 -> "${get(3)}${get(0)}/${get(4)}${get(1)}"
  11 -> "${get(8)}${get(4)}${get(0)}/${get(9)}${get(5)}${get(1)}/${get(10)}${get(6)}${get(2)}"
  else -> error("No rotation possible")
}

fun String.flipRule() = when (length) {
  5 -> "${get(1)}${get(0)}/${get(4)}${get(3)}"
  11 -> "${get(2)}${get(1)}${get(0)}/${get(6)}${get(5)}${get(4)}/${get(10)}${get(9)}${get(8)}"
  else -> error("No rotation possible")
}

fun List<List<Char>>.divide() =
  when {
    size % 2 == 0 -> squares(2)
    size % 3 == 0 -> squares(3)
    else -> error("made an error with $size")
  }


fun List<List<Char>>.squares(size: Int) =
  windowed(size = size, step = size)
    .flatMap { list ->
      val list0 = list[0].windowed(size, size) { it.joinToString("") }
      val list1 = list0.zip(list[1].windowed(size, size) { it.joinToString("") }).map { (a, b) -> "$a/$b" }
      when (size) {
        2 -> list1
        3 -> list1.zip(list[2].windowed(size, size) { it.joinToString("") }).map { (a, b) -> "$a/$b" }
        else -> error("Invalid size")
      }
    }

