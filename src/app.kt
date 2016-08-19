import java.io.File
import java.util.*

/**
 * Created by Andrea on 19/08/2016.
 */
fun main(args: Array<String>) {
    val map = HashMap<String, MutableList<File>>()
    val folderInput = File(args[0])
    val folderNameOutput = args[1]
    val files = folderInput.listFiles()
    println("total files: ${files.size}")
    files.map { it -> index(it, map) }
    println("map has ${map.size} elements")
    // map.filter { it -> it.value.size == 1 }.map { it -> println("1: " + it.value[0].name) }
    // map.filter { it -> it.value.size > 1 }.map { it -> println(it.value.size.toString() + it.value.joinToString { it -> it.name }) }
    scaffold(folderNameOutput, map)
}

/**
 * Copy files from the map into given folder.
 *
 * If an index value contains a single file, then it will be copied to the output folder.
 * If an index value contains multiple files, then a subfolder with the index key will be created inside
 * which all files are to be copied.
 *
 * @param baseDir output folder
 * @param index
 */
fun scaffold(baseDir: String, index: HashMap<String, MutableList<File>>) {
    println("Saving in $baseDir folder, index has ${index.size} elements")
    for ((subfolder, files) in index){
        val size: Int = files.size
        println("$subfolder has $size entries")
        when (size) {
            0 -> println("Warning: key $subfolder contains no elements")
            1 -> files[0].copyTo(File(baseDir + files[0].name), false, DEFAULT_BUFFER_SIZE)
            else -> {
                println("saving multiple files into a subfolder")
                files.map { file -> file.copyTo(File(baseDir + subfolder + File.separator + file.name), false, DEFAULT_BUFFER_SIZE) }
            }
        }
    }
}

/**
 * Insert the file name into given map
 * @param file a file which name is to be grouped with other file names
 * @param map an index of already processed files
 */
fun index(file: File, map: HashMap<String, MutableList<File>>) {
    val name = baseName(file)
    if (name == null) {
        println("Warning: file ${file.name} has null base name.")
        return
    }
    if (!map.containsKey(name)) {
        map.put(name, mutableListOf(file))
    } else {
        map[name]!!.add(file)
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

