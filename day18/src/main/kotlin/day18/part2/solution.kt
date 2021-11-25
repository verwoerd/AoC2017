package day18.part2

import day18.part1.Instruction
import day18.part1.Operation
import day18.part1.parseInput
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/11/2021
 */
fun part2(input: BufferedReader): Any {
  val program = input.parseInput()
  val computer0 = FancyComputer(0)
  val computer1 = FancyComputer(1)
  val channel0 = Channel<Long>(Channel.UNLIMITED)
  val channel1 = Channel<Long>(Channel.UNLIMITED)
  runBlocking {
    val job1 = launch(Dispatchers.Unconfined) { computer0.execute(program, channel0, channel1, computer1) }
    val job2 = launch(Dispatchers.Unconfined) { computer1.execute(program, channel1, channel0, computer0) }
    job1.invokeOnCompletion { job2.cancel() }
    job2.invokeOnCompletion { job1.cancel() }
    joinAll(job1, job2)
  }
  return computer1.send
}

data class FancyComputer(
  val programId: Long,
  var registers: MutableMap<Char, Long> = mutableMapOf<Char, Long>().withDefault { 0L },
  var send: Int = 0,
  var waiting: Boolean = false,
) {
  @OptIn(ExperimentalCoroutinesApi::class)
  suspend fun execute(
    program: List<Instruction>,
    sendChannel: Channel<Long>,
    receiveChannel: Channel<Long>,
    computer: FancyComputer
  ) {
    var pc = 0
    registers['p'] = programId
    while (pc < program.size) {
      val instruction = program[pc]
//      println("[$programId][$pc] $instruction $this")
      when (instruction.operation) {
        Operation.SND -> sendChannel.send(getValue(instruction.x)).also { send++ }
        Operation.SET -> registers[instruction.x.first()] = getValue(instruction.y)
        Operation.ADD -> registers[instruction.x.first()] = getValue(instruction.x) + getValue(instruction.y)
        Operation.MUL -> registers[instruction.x.first()] = getValue(instruction.x) * getValue(instruction.y)
        Operation.MOD -> registers[instruction.x.first()] = getValue(instruction.x) % getValue(instruction.y)
        Operation.RCV -> {
          waiting = true
          if (computer.waiting && receiveChannel.isEmpty && sendChannel.isEmpty) {
            sendChannel.close()
            receiveChannel.close()
            return
          }
          try {
            registers[instruction.x.first()] = receiveChannel.receive()
          } catch (e: ClosedReceiveChannelException) {
            return
          }
          waiting = false
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
