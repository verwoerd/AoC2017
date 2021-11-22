package day10.part1

import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 22/11/2021
 */
fun part1(input: BufferedReader, size: Int = 256): Any {
  val skipList = input.readLine().split(",").map { it.toInt() }
  val list = (0 until size).toCollection(LinkedList())
  val result = knotHash(list, skipList)
  return result[0] * result[1]
}

fun knotHash(list: LinkedList<Int>, skipList:List<Int>, times: Int = 1): LinkedList<Int> {
  var skip = 0
  var rotations = 0
  var result : LinkedList<Int> = LinkedList()
  repeat(times) {
    result = skipList.fold(list) { acc, i ->
      val stack = Stack<Int>()
      repeat(i) { stack.add(acc.remove()) }
      while (stack.isNotEmpty()) {
        acc.add(stack.pop())
      }
      rotations += skip + i
      acc.rotate(skip++)
      acc
    }
  }
  result.rotateBack(rotations)
  return result
}

fun <T> LinkedList<T>.rotate(times: Int) {
  repeat(times % size) {
    addLast(removeFirst())
  }
}

fun <T> LinkedList<T>.rotateBack(times: Int) {
  repeat(times % size) {
    addFirst(removeLast())
  }
}
