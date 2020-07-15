import java.util.Scanner
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val posn1 = readLine()!!.split(" ")
    val vector1 = sqrt(posn1[0].toDouble().pow(2) + posn1[1].toDouble().pow(2))

    val posn2 = readLine()!!.split(" ")
    val vector2 = sqrt(posn2[0].toDouble().pow(2) + posn2[1].toDouble().pow(2))

    val dotProduct = posn1[0].toDouble() * posn2[0].toDouble() + posn1[1].toDouble() * posn2[1].toDouble()

    val angleBNVectors = acos(dotProduct / (vector1 * vector2)) * (180/3.14)

    println(Math.floor(angleBNVectors))
}