package com.example.wordle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var wordToGuess = "abcd"
    var my_guess = ""

    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }

    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val main = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        val inputText = findViewById<EditText>(R.id.inputText)
        val button = findViewById<Button>(R.id.submit)

        val reset_button = findViewById<Button>(R.id.reset)

        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val input1 = findViewById<TextView>(R.id.input1)
        val input2 = findViewById<TextView>(R.id.input2)
        val input3 = findViewById<TextView>(R.id.input3)
        val check_guess1 = findViewById<TextView>(R.id.check_guess1)
        val check_input1 = findViewById<TextView>(R.id.check_input1)
        val check_guess2 = findViewById<TextView>(R.id.check_guess2)
        val check_input2 = findViewById<TextView>(R.id.check_input2)
        val check_guess3 = findViewById<TextView>(R.id.check_guess3)
        val check_input3 = findViewById<TextView>(R.id.check_input3)
        val display_word = findViewById<TextView>(R.id.display_word)

        var attempts = 0


        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        println(wordToGuess)
        button.setOnClickListener{ v->
            // Close keyboard
            inputText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            println("Guess button was actually clicked")
            my_guess = inputText.text.toString().uppercase()
            inputText.text.clear()
            if (my_guess.isNotEmpty() && my_guess.length == 4) {
                //println("My guess is not empty")
                attempts++
                if (attempts > 3) {
                    // lost the game: activate reset button,
                    button.visibility =View.INVISIBLE
                    reset_button.visibility = View.VISIBLE
                    my_guess = ""
                    main.setBackgroundColor(ContextCompat.getColor(this, R.color.loss_color))

                    inputText.text.clear()
                    // Display the word
                    display_word.text = wordToGuess
                    display_word.visibility = View.VISIBLE

                } else {
                    // Keep playing and checking whether or not you guessed it right
                    if (attempts == 1) {
                        input1.text = my_guess

                        guess1.visibility =View.VISIBLE
                        input1.visibility =View.VISIBLE
                        // Determine if we won
                        val match = checkGuess(my_guess)

                        check_input1.text = match
                        check_guess1.visibility =View.VISIBLE
                        check_input1.visibility =View.VISIBLE
                        if (match == "OOOO") {
                            // We won
                            button.visibility =View.INVISIBLE
                            reset_button.visibility = View.VISIBLE
                            my_guess = ""
                            main.setBackgroundColor(ContextCompat.getColor(this, R.color.win_color))
                            // Display WORD IN A BOX
                            display_word.text = wordToGuess
                            display_word.visibility = View.VISIBLE
                        }
                    } else if (attempts == 2) {
                        input2.text = my_guess

                        guess2.visibility =View.VISIBLE
                        input2.visibility =View.VISIBLE
                        // Determine if we won
                        val match = checkGuess(my_guess)
                        check_input2.text = match
                        check_guess2.visibility =View.VISIBLE
                        check_input2.visibility =View.VISIBLE
                        if (match == "OOOO") {
                            // We won
                            button.visibility =View.INVISIBLE
                            reset_button.visibility = View.VISIBLE
                            my_guess = ""
                            main.setBackgroundColor(ContextCompat.getColor(this, R.color.win_color))
                            // Display WORD IN A BOX
                            display_word.text = wordToGuess
                            display_word.visibility = View.VISIBLE
                        }
                    } else if (attempts == 3) {
                        input3.text = my_guess

                        guess3.visibility =View.VISIBLE
                        input3.visibility =View.VISIBLE
                        // Determine if we won
                        println("This is the result: " + checkGuess(my_guess.toString()))
                        val match = checkGuess(my_guess)
                        check_input3.text = match
                        check_guess3.visibility =View.VISIBLE
                        check_input3.visibility =View.VISIBLE

                        button.visibility =View.INVISIBLE
                        reset_button.visibility = View.VISIBLE
                        my_guess = ""
                        if (match == "OOOO") {
                            main.setBackgroundColor(ContextCompat.getColor(this, R.color.win_color))
                        } else {
                            main.setBackgroundColor(ContextCompat.getColor(this, R.color.loss_color))
                        }
                        // Display the word
                        display_word.text = wordToGuess
                        display_word.visibility = View.VISIBLE
                    }
                }
            } else {
                println("My guess is empty")
            }

        }

        // Resetting game
        reset_button.setOnClickListener{ v->
            // Close keyboard
            inputText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            main.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            button.visibility =View.VISIBLE
            reset_button.visibility = View.INVISIBLE
            guess1.visibility =View.INVISIBLE
            guess2.visibility =View.INVISIBLE
            guess3.visibility =View.INVISIBLE
            input1.visibility =View.INVISIBLE
            input2.visibility =View.INVISIBLE
            input3.visibility =View.INVISIBLE
            check_guess1.visibility = View.INVISIBLE
            check_guess2.visibility = View.INVISIBLE
            check_guess3.visibility = View.INVISIBLE
            check_input1.visibility =View.INVISIBLE
            check_input2.visibility =View.INVISIBLE
            check_input3.visibility =View.INVISIBLE

            input1.text = "sample_input_1"
            input2.text = "sample_input_2"
            input3.text = "sample_input_3"
            display_word.text = "WORD_DISPLAY"
            display_word.visibility = View.INVISIBLE
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            println("New word is: " +  wordToGuess)
            attempts = 0
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}