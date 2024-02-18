package jetbrains.kotlin.course.almost.done

fun safeReadLine(): String {
    return readlnOrNull() ?: error("Null received")
}

fun photoshop() {
    val picture = getPicture()
    println("The old image:")
    println(picture)
    val filteredPicture = applyFilter(picture, chooseFilter())
    println("The transformed picture:")
    println(filteredPicture)
}

fun getPicture(): String {

    println("Do you want to use a predefined picture or a custom one? " +
            "Please input 'yes' for a predefined image or 'no' for a custom one")
    while(true){
        val response = safeReadLine()
        when(response){
            "yes" -> return choosePicture()
            "no" -> {
                println("Please input a custom picture")
                return safeReadLine()
            }
        }
    }
}

fun choosePicture():String {
    var picture: String?
    do{
        println("Please choose a picture. The possible options are: ${allPictures().joinToString(", ")}")
        val prompt = safeReadLine()
        picture = getPictureByName(prompt)
    }while (picture == null)
    return picture
}

fun chooseFilter(): String {
    val filters = listOf("borders", "squared")
    println("Please choose the filter: 'borders' or 'squared'")
    var result = safeReadLine()
    while(result !in filters) {
        println("Please input 'borders' or 'squared'")
        result = safeReadLine()
    }
    return result
}

fun trimPicture(picture: String) = picture.trimIndent()
fun applyFilter(picture: String, filter:String): String {
    return when(filter) {
        "borders" -> {
            applyBordersFilter(trimPicture(picture))
        }
        "squared" -> {
            applySquaredFilter(trimPicture(picture))
        }
        else -> error("Unrecognized filter name")
    }
}
fun applyBordersFilter(picture: String): String {
    val width = getPictureWidth(picture)
    val result = StringBuilder()
    result.append("$borderSymbol".repeat(width+4)).append(newLineSymbol)
    picture.lines().forEach {
        result.append(borderSymbol)
            .append(separator)
            .append(it)
            .append("$separator".repeat(width-it.length+1))
            .append(borderSymbol)
            .append(newLineSymbol)
    }
    result.append("$borderSymbol".repeat(width+4))
    return result.toString()
}
fun applySquaredFilter(picture: String): String {
    val borderPicture = applyBordersFilter(picture)
    val result = StringBuilder()
    val width = getPictureWidth(borderPicture)
    result.append("$borderSymbol".repeat(2*width)).append(newLineSymbol)
    val lines = borderPicture.lines()
    lines.slice(1..lines.size-2).forEach {
        result
            .append(it)
            .append(it)
            .append(newLineSymbol)
    }
    result.append("$borderSymbol".repeat(2*width)).append(newLineSymbol)
    lines.slice(1..lines.size-2).forEach {
        result
            .append(it)
            .append(it)
            .append(newLineSymbol)
    }
    result.append("$borderSymbol".repeat(2*width)).append(newLineSymbol)
    return result.toString()
}

fun main() {
    // Uncomment this code on the last step of the game
     photoshop()
}
