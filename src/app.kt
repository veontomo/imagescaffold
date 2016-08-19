import java.io.File
import java.util.*

/**
 * Created by Andrea on 19/08/2016.
 */
fun main(args: Array<String>) {
    val map = HashMap<String, MutableList<String>>()
    val folder = File(args[0])
    val files = folder.listFiles()
    println("total files: ${files.size}")
    files.map { it -> index(it, map) }
    println("map has ${map.size} elements")
    map.filter { it -> it.value.size == 1 }.map { it -> println("1: " + it.value[0]) }
    map.filter { it -> it.value.size > 1 }.map { it -> println(it.value.size.toString() + it.value.joinToString { it }) }
}

/**
 * Insert the file name into given map
 * @param file a file which name is to be grouped with other file names
 * @param map an index of already processed files
 */
fun index(file: File, map: HashMap<String, MutableList<String>>) {
    val name = baseName(file)
    if (name == null) {
        println("Warning: file ${file.name} has null base name.")
        return
    }
    if (!map.containsKey(name)) {
        map.put(name, mutableListOf(file.name))
    } else {
        map[name]!!.add(file.name)
    }
}

/**
 * Returns the first 5 digits in the name of given file
 * @param file
 * @return string consisting of 5 digits or null
 */
fun baseName(file: File): String? {
    val pattern = Regex("^\\d{5}")
    val fileName = file.nameWithoutExtension
    return pattern.find(fileName, 0)?.value
}

