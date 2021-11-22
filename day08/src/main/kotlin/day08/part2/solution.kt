package day08.part2

import day08.part1.Command
import java.io.BufferedReader
import java.lang.Long.max

/**
 * @author verwoerd
 * @since 22/11/2021
 */
fun part2(input: BufferedReader): Any {
  val registers = mutableMapOf<String, Long>().withDefault { 0L }
  return input.lineSequence().map { Command.from(it) }
    .fold(0L) {acc, it ->
      it.execute(registers)
      max(acc, registers.values.maxOrNull() ?: 0)
    }
}
