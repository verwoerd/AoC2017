package day18.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/11/2021
 */
fun part1(input: BufferedReader): Any {
  val program = input.parseInput()
  val computer = Computer()
  computer.execute(program)
  return computer.recover
}

fun BufferedReader.parseInput() = lineSequence().map { Instruction.parseFromString(it) }.toList()

enum class Operation {
  SND, SET, ADD, MUL, MOD, RCV, JGZ;
}

data class Instruction(
  val operation: Operation,
  val x: String,
  val y: String
) {
  companion object {
    val SINGLE_REGEX = Regex("(snd|rcv) (-?\\d+|\\w)")
    val DOUBLE_REGEX = Regex("(set|add|mul|mod|jgz) (\\w) (-?\\d+|\\w)")

    fun parseFromString(line: String): Instruction {
      return when {
        SINGLE_REGEX.matches(line) ->
          SINGLE_REGEX.matchEntire(line)!!.destructured
            .let { (opp, x) -> Instruction(Operation.valueOf(opp.uppercase()), x, "") }
        DOUBLE_REGEX.matches(line) ->
          DOUBLE_REGEX.matchEntire(line)!!.destructured
            .let { (opp, x, y) -> Instruction(Operation.valueOf(opp.uppercase()), x, y) }
        else -> error("Invalid line $line")
      }
    }
  }
}

data class Computer(
  var sound: Long = 0,
  var recover: Long = 0,
  var registers: MutableMap<Char, Long> = mutableMapOf<Char, Long>().withDefault { 0L }
) {
  fun execute(program: List<Instruction>) {
    var pc = 0
    while (pc < program.size) {
      val instruction = program[pc]
//      println("[$pc] $instruction $this")
      when (instruction.operation) {
        Operation.SND -> sound = getValue(instruction.x)
        Operation.SET -> registers[instruction.x.first()] = getValue(instruction.y)
        Operation.ADD -> registers[instruction.x.first()] = getValue(instruction.x) + getValue(instruction.y)
        Operation.MUL -> registers[instruction.x.first()] = getValue(instruction.x) * getValue(instruction.y)
        Operation.MOD -> registers[instruction.x.first()] = getValue(instruction.x) % getValue(instruction.y)
        Operation.RCV -> if (getValue(instruction.x) != 0L) {
          recover = sound
          return
        }
        Operation.JGZ -> if (getValue(instruction.x) > 0) {
          pc += getValue(instruction.y).toInt()
          continue
        }
      }
      pc++
    }
  }

  fun getValue(value: String) =
    when (val digit = value.toLongOrNull()) {
      null -> registers.getValue(value.first())
      else -> digit
    }

}
