package day16.part1

import rotateBack
import swap
import swapValues
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 24/11/2021
 */
fun part1(input: BufferedReader, dancers: Int = 16): Any {
  val dance = parseDance(input)
  val position = (0 until dancers).map { ('a'.code + it).toChar() }.toCollection(LinkedList())
  return dance.executeDance(position).joinToString("")
}

fun List<DanceMove>.executeDance(position: LinkedList<Char>) = fold(position) { acc, danceMove ->
  when (danceMove.step) {
    DanceStep.SPIN -> acc.rotateBack(danceMove.a)
    DanceStep.EXCHANGE -> acc.swap(danceMove.a, danceMove.b)
    DanceStep.PARTNER -> acc.swapValues(danceMove.c, danceMove.d)
  }
}

enum class DanceStep {
  SPIN, EXCHANGE, PARTNER
}

data class DanceMove(
  val step: DanceStep,
  val a: Int = 0,
  val b: Int = 0,
  val c: Char = '0',
  val d: Char = '0'
)


val SPIN_REGEX = Regex("s(\\d+)")
val EXCHANGE_REGEX = Regex("x(\\d+)/(\\d+)")
val PARTNER_REGEX = Regex("p(\\w)/(\\w)")

fun parseDance(input: BufferedReader) =
  input.readLine()
    .split(",")
    .map {
      when {
        SPIN_REGEX.matches(it) -> SPIN_REGEX.matchEntire(it)!!.destructured.let { (x) ->
          DanceMove(
            DanceStep.SPIN,
            x.toInt()
          )
        }
        EXCHANGE_REGEX.matches(it) -> EXCHANGE_REGEX.matchEntire(it)!!.destructured.let { (a, b) ->
          DanceMove(
            DanceStep.EXCHANGE,
            a.toInt(),
            b.toInt()
          )
        }
        PARTNER_REGEX.matches(it) -> PARTNER_REGEX.matchEntire(it)!!.destructured.let { (a, b) ->
          DanceMove(
            DanceStep.PARTNER,
            c = a.first(),
            d = b.first()
          )
        }
        else -> error("No such move $it")
      }
    }
