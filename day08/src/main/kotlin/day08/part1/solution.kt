package day08.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/11/2021
 */
fun part1(input: BufferedReader): Any {
  val registers = mutableMapOf<String, Long>().withDefault { 0L }
  input.lineSequence().map { Command.from(it) }
    .forEach { it.execute(registers) }
  return registers.values.maxOrNull() ?: error("this should not happen")
}


data class Command(
  val register: String,
  val operator: Operator,
  val diff: Long,
  val compareRegister: String,
  val compareOperator: CompareOperator,
  val compareDiff: Long
) {
  companion object {
    private val OPERATION_REGEX = Regex("(\\w+) (inc|dec) (-?\\d+) if (\\w+) ([=<>!]+) (-?\\d+)")
    fun from(line: String): Command {
      val (register, operator, diff, compareRegister, compareOperator, compareDiff) = OPERATION_REGEX.matchEntire(line)?.destructured
        ?: error("Can't parse line $line")
      return Command(
        register,
        Operator.from(operator),
        diff.toLong(),
        compareRegister,
        CompareOperator.from(compareOperator),
        compareDiff.toLong()
      )
    }
  }

  fun execute(registers: MutableMap<String, Long>) {
    if (compareOperator.execute(registers.getValue(compareRegister), compareDiff)) {
      registers[register] = operator.execute(registers.getValue(register), diff)
    }
  }
}

enum class Operator(
  val execute: (left: Long, right: Long) -> Long
) {
  INC({ l, r -> l + r }), DEC({ l, r -> l - r });

  companion object {
    fun from(operation: String): Operator = when (operation) {
      "inc" -> INC
      "dec" -> DEC
      else -> error("Unknown operation: Operation")
    }
  }
}

enum class CompareOperator(
  val execute: (left: Long, right: Long) -> Boolean
) {
  SMALLER({ l, r -> l < r }),
  SMALLER_EQUAL({ l, r -> l <= r }),
  EQUAL({ l, r -> l == r }),
  NOT_EQUAL({ l, r -> l != r }),
  GREATER_EQUAL({ l, r -> l >= r }),
  GREATER({ l, r -> l > r });

  companion object {
    fun from(operation: String): CompareOperator = when (operation) {
      "<" -> SMALLER
      "<=" -> SMALLER_EQUAL
      "==" -> EQUAL
      ">=" -> GREATER_EQUAL
      ">" -> GREATER
      "!=" -> NOT_EQUAL
      else -> error("invalid operation: Operation")
    }

  }
}
