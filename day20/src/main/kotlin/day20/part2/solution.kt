package day20.part2

import day20.part1.Particle
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/11/2021
 */
fun part2(input: BufferedReader): Any {
  val particles = input.lineSequence().mapIndexed { index, line -> Particle.from(line, index) }.toList()
  var countOccurences = 0
  val threshold = 5000
  var lastSize = -1
  return generateSequence(particles) { list ->
    val result = list.map { it.doTick() }
    result.filter { particle -> result.all { it.id == particle.id || it.position != particle.position } }
  }.takeWhile {
    when (it.size) {
      lastSize -> countOccurences++
      else -> lastSize = it.size.also { countOccurences = 0 }
    }
    countOccurences != threshold
  }.last().size
}
