package jetbrains.kotlin.course.last.push

fun dropTopFromLine(line: String, width: Int, patternHeight: Int, patternWidth: Int):String {
    if(patternHeight <= 1) return line
    val lines = line.lines()
    return lines.slice(1..lines.lastIndex).joinToString(newLineSymbol)
}

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int): String {
    val trimmedPattern = pattern.trimIndent()
    return when(generatorName){
        "canvas" -> canvasGenerator(trimmedPattern, width, height) + newLineSymbol
        "canvasGaps" -> canvasWithGapsGenerator(trimmedPattern, width, height) + newLineSymbol
        else -> error("Wrong generator name.")
    }
}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    val patternWidth = getPatternWidth(pattern)
    val oddRow = repeatHorizontallyWithGaps(pattern, width, patternWidth, 0)
    val evenRow = if(width != 1)repeatHorizontallyWithGaps(pattern, width, patternWidth, 1) else oddRow
    return List(height) {
        if(it%2 == 0) oddRow else evenRow
    }.joinToString(newLineSymbol)
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    val line = repeatHorizontally(pattern, width, getPatternWidth(pattern))
    val droppedLine = dropTopFromLine(line, 0, getPatternHeight(pattern), 0)
    return List(height) {
        if (it == 0) line else droppedLine
    }.joinToString(newLineSymbol)
}
fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int) =
    pattern.lines()
        .map { fillPatternRow(it, patternWidth) }.joinToString(
            newLineSymbol
        ){ elem -> List(n) {elem}.joinToString("") }

fun repeatHorizontallyWithGaps(pattern: String, n:Int, patternWidth: Int, parity: Int) =
    pattern.lines()
        .map { fillPatternRow(it, patternWidth) }.joinToString(
            newLineSymbol
        ){ elem -> List(n) {if(it%2 == parity) elem else "$separator".repeat(patternWidth)}
            .joinToString("") }


fun fillPatternRow(patternRow: String, patternWidth: Int) =
    if(patternWidth - patternRow.length >= 0) patternRow + "$separator".repeat(patternWidth - patternRow.length)
    else {
        throw IllegalStateException()
    }

fun getPatternHeight(pattern: String) = pattern.lines().size

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun main() {
    // Uncomment this code on the last step of the game
     val pattern = getPattern()
     val generatorName = chooseGenerator()
     println("Please input the width of the resulting picture:")
     val width = safeReadLine().toInt()
     println("Please input the height of the resulting picture:")
     val height = safeReadLine().toInt()

     println("The pattern:$newLineSymbol${pattern.trimIndent()}")

     println("The generated image:")
     println(applyGenerator(pattern, generatorName, width, height))
}
