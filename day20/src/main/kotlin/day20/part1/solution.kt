package day20.part1

import TripleCoordinate
import manhattanDistance
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/11/2021
 */
fun part1(input: BufferedReader): Any {
  val particles = input.lineSequence().mapIndexed { index, line -> Particle.from(line, index) }.toList()
  var countOccurences = 0
  val threshold = 5000
  var lastClosest = -1
  return generateSequence(particles) { list -> list.map { it.doTick() } }
    .map { list ->
      val min = list.minOf { manhattanDistance(it.position) }
      list.filter { manhattanDistance(it.position) == min }
    }.takeWhile {
      when (it.size) {
        1 -> when (val particle = it.first().id) {
          lastClosest -> countOccurences++
          else -> lastClosest = particle.also { countOccurences = 0 }
        }
        else -> {
          lastClosest = -1
          countOccurences = 0
        }
      }
      countOccurences != threshold
    }.last().first().id
}

data class Particle(
  val id: Int,
  val position: TripleCoordinate,
  val velocity: TripleCoordinate,
  val acceleration: TripleCoordinate
) {
  companion object {
    val REGEX = Regex("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>")
    fun from(line: String, id: Int) =
      REGEX.matchEntire(line)!!.destructured
        .let { (px, py, pz, vx, vy, vz, ax, ay, az) ->
          Particle(
            id,
            TripleCoordinate(px.toLong(), py.toLong(), pz.toLong()),
            TripleCoordinate(vx.toLong(), vy.toLong(), vz.toLong()),
            TripleCoordinate(ax.toLong(), ay.toLong(), az.toLong())
          )
        }
  }

  fun doTick() = copy(
    position = position + velocity + acceleration,
    velocity = velocity + acceleration
  )
}
