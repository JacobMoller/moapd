package com.bignerdranch.android.geoquiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var indexOfAnsweredQuestions = ArrayList<Int>()
    var answeredCorrect = ArrayList<Boolean>()

    val currentQuestionAlreadyAnswered: Boolean
        get() = indexOfAnsweredQuestions.contains(currentIndex)
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext() {
        //Check if done
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev() {
        //Check if done
        currentIndex = if(currentIndex != 0) {
            (currentIndex - 1) % questionBank.size
        }else{
            (questionBank.size - 1)
        }
    }


    val gameOver: Boolean
        get() = indexOfAnsweredQuestions.size == questionBank.size

    //Chapter 3 Challenge 1
    fun addAnsweredQuestions(logic: Boolean){
        indexOfAnsweredQuestions.add(currentIndex)
        answeredCorrect.add(answerLogic(logic))
    }

    fun answerLogic(userAnswer: Boolean): Boolean {
        return userAnswer == currentQuestionAnswer
    }

    //Chapter 3 Challenge 2
    fun getGameResults() : Int{
        var numberOfRight = 0
        for (answer in answeredCorrect) {
            if(answer){
                numberOfRight++
            }
        }
        return numberOfRight*100/questionBank.size
    }
}