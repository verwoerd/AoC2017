package day17.part1

import linkedListOf
import rotate
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/11/2021
 */
fun part1(input: BufferedReader): Any {
  val steps = input.readLine().toInt()
  val spinLock = linkedListOf(0)
  var step = 1
  repeat(2017) {
    spinLock.rotate(steps + 1)
    spinLock.addFirst(step++)
  }
  return spinLock.rotate(1).first()
}
