import java.util.Scanner
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val a = readLine()!!.toDouble()
    val b = readLine()!!.toDouble()
    val c = readLine()!!.toDouble()

    val p = (a + b + c) / 2

    val s = sqrt(p * (p-a) * (p-b) * (p-c))

    println(s)
}