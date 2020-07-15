import java.util.*
import kotlin.math.cos
import kotlin.math.sin

fun main(args: Array<String>) {
    val angle = readLine()!!.toDouble()
    println(sin(angle) - cos(angle))
}