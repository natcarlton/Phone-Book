import java.util.Scanner
import kotlin.math.pow

fun main(args: Array<String>) {
    val x = readLine()!!.toDouble()
    val cubic = x.pow(3) + x.pow(2) + x + 1
    println(cubic)
}