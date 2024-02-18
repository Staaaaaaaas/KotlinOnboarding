package jetbrains.kotlin.course.hangman

fun playGame(secret: String, maxAttemptsCount: Int) {
    var currentUserWord = getHiddenSecret(wordLength)
    var attempts = 0
    while(true){
        val guess = safeUserInput()
        currentUserWord = getRoundResults(secret, guess, currentUserWord)
        val complete = isComplete(secret, currentUserWord)
        attempts++
        if(isLost(complete, attempts, maxAttemptsCount)){
            println("Sorry, you lost! My word is $secret")
            return
        }
        if(isWon(complete, attempts, maxAttemptsCount)){
            println("Congratulations! You guessed it!")
            return
        }
    }

}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String) =
    if(guess !in secret) {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
        currentUserWord
    }
    else {
        val newUserWord = generateNewUserWord(secret, guess, currentUserWord)
        println("Great! This letter is in the word! The current word is ${newUserWord}")
        newUserWord
    }


fun safeUserInput(): Char {
    println("Please input your guess.")
    var guess: String
    do{
        guess = safeReadLine()
    }while(!isCorrectInput(guess))
    return guess[0].uppercaseChar()
}

fun isCorrectInput(userInput: String): Boolean{
    if(userInput.length != 1){
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if(!userInput[0].isLetter()){
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun getHiddenSecret(wordLength: Int) = List(wordLength) { underscore }.joinToString(separator)

fun generateSecret() = words.random()

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String{
    return secret.
    mapIndexed{ ind, it -> if(it == guess) it else currentUserWord[2*ind]}.joinToString(
        separator)
}

fun isComplete(secret: String, currentGuess: String) = secret == currentGuess.split(separator).joinToString("")


// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = attempts > maxAttemptsCount

fun main() {
    // Uncomment this code on the last step of the game
     println(getGameRules(wordLength, maxAttemptsCount))
     playGame(generateSecret(), maxAttemptsCount)
}
