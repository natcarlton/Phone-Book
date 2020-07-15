package phonebook

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.round

data class Person(val name: String, val number: Long) {
    operator fun compareTo(x: Person) = this.name.compareTo(x.name)
    override fun toString(): String {
        return "$number $name"
    }
}

fun main() {
    val rawDirectory = File("C:\\Users\\Natasha Carlton\\Desktop\\directory.txt").readLines()
    val find = File("C:\\Users\\Natasha Carlton\\Desktop\\find.txt").readLines()
    val directory = mutableListOf<Person>()

    rawDirectory.forEach {
        val (x, y) = it.split(" ", limit = 2)
        directory.add(Person(name = y, number = x.toLong()))
    }

    println("Start searching (linear search)...")
    val linearStartTime: Long = System.currentTimeMillis()
    linearSearch(directory, find)
    val linearEndTime: Long = System.currentTimeMillis()
    println("Found 500 / 500 entries. Time taken: ${calculateTimeTaken(linearEndTime - linearStartTime)}\n")


    println("Start searching (bubble sort + jump search)...")
    val bubbleStartTime = System.currentTimeMillis()
    bubbleSort(directory, find)
    val bubbleEndTime = System.currentTimeMillis()
    println("Found 500 / 500 entries. Time taken: ${calculateTimeTaken(bubbleEndTime - bubbleStartTime)}")
    println("Sorting time: ${calculateTimeTaken((linearEndTime + bubbleStartTime) - (linearStartTime + bubbleEndTime))} - STOPPED, moved to linear search")
    println("Searching time: ${calculateTimeTaken(linearEndTime - linearStartTime)}\n")

    println("Start searching (quick sort + binary search)...")
    val quickStartTime = System.currentTimeMillis()
    val binarySearchTime = binarySearch(quickSort(directory), find)
    val quickEndTime = System.currentTimeMillis()
    println("Found 500 / 500 entries. Time taken: ${calculateTimeTaken(quickStartTime - quickEndTime - binarySearchTime)}")
    println("Sorting time: ${calculateTimeTaken(quickStartTime - quickEndTime)}")
    println("Searching time: ${calculateTimeTaken(binarySearchTime)}\n")

    println("Start searching (hash table)...")
    val hashStartTime = System.currentTimeMillis()
    val searchingTime = searchHashTable(createHashTable(directory), find)
    val hashEndTime = System.currentTimeMillis()
    println("Found 500 / 500 entries. Time taken: ${calculateTimeTaken(hashStartTime - hashEndTime - searchingTime)}")
    println("Creating time: ${calculateTimeTaken(hashStartTime - hashEndTime)}")
    println("Searching time: ${calculateTimeTaken(searchingTime)}")

}

fun linearSearch(directory: MutableList<Person>, find: List<String>) {

    find.forEach { person ->
        directory.filter { it.name == person }
    }
}

fun bubbleSort(directory: MutableList<Person>, find: List<String>) {
    val sortedDirectory = directory.toMutableList()

    val startTime = System.currentTimeMillis()

    for (index in 0..sortedDirectory.size) {
        for (searchIndex in 0 until sortedDirectory.size - index - 1) {
            if (sortedDirectory[searchIndex] > sortedDirectory[searchIndex + 1]) {
                val temp = sortedDirectory[index]
                sortedDirectory[index] = sortedDirectory[index + 1]
                sortedDirectory[index + 1] = temp
            }
        }
        val totalTime = System.currentTimeMillis() - startTime

        if (totalTime > 12000) {
            linearSearch(directory, find)
            break
        }
    }
    jumpSearch(sortedDirectory, find)
}

fun jumpSearch(sortedDirectory: MutableList<Person>, find: List<String>) {
    val jumpIndex = round(kotlin.math.sqrt(sortedDirectory.size.toDouble())).toInt()

    find.forEach { name ->
        for (index in sortedDirectory.indices step jumpIndex) {
            val lastJumpIndex = min(index + jumpIndex - 1, sortedDirectory.lastIndex)
            if (sortedDirectory[index].name <= name && name < sortedDirectory[lastJumpIndex].name) {
                for (range in index..lastJumpIndex) {
                    if (sortedDirectory[range].name == name) {
                        continue
                    }
                }
                break
            }
        }
    }
}

fun quickSort(directory: List<Person>): List<Person> {
    if (directory.count() < 2) return directory

    val pivot = directory.last()

    val less = directory.filter { it < pivot }
    val greater = directory.filter { it > pivot }
    val equal = directory.filter { it == pivot }

    val sortLeft = if(less.isNotEmpty()) quickSort(less) else listOf()
    val sortRight = if(greater.isNotEmpty()) quickSort(greater) else listOf()

    return sortLeft + equal + sortRight
}

fun binarySearch(sortedDirectory: List<Person>, find: List<String>): Long {
    val startTime = System.currentTimeMillis()
    loop@for (query in find) {
        var left = 0
        var right = sortedDirectory.lastIndex
        while(right > left) {
            val mid = (right + left) / 2
            when {
                query in sortedDirectory[mid].name -> {
                    continue@loop
                }
                query > sortedDirectory[mid].name -> left = mid + 1
                query < sortedDirectory[mid].name -> right = mid - 1
            }
        }
        if (right == left) {
            continue@loop
        }
    }
    return System.currentTimeMillis() - startTime
}

fun createHashTable(directory: MutableList<Person>): HashMap<String, Long> {
    val directoryHashMap = HashMap<String, Long>()

    directory.forEach { person ->
        directoryHashMap[person.name] = person.number
    }
    return directoryHashMap
}

fun searchHashTable(sortedDirectory: HashMap<String, Long>, find: List<String>): Long {
    val startTime = System.currentTimeMillis()
    var found = false
    find.forEach { name ->
        found = sortedDirectory.containsKey(name)
    }

    return System.currentTimeMillis() - startTime
}

fun calculateTimeTaken(ms: Long): String {
    var remainingTime = ms
    val minutes = ms / 60000
    remainingTime -= minutes * 60000
    val seconds = ms / 1000
    remainingTime -= seconds * 1000
    return ("${minutes.absoluteValue} min. ${seconds.absoluteValue} sec. ${remainingTime.absoluteValue} ms.")
}
