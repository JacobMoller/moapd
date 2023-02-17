package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateQuestion()

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            quizViewModel.addAnsweredQuestions(true)
            checkGameOver()
            checkButtons()
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            quizViewModel.addAnsweredQuestions(false)
            checkGameOver()
            checkButtons()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        // Challenge from Welcome document
        val version = Build.VERSION.SDK_INT
        binding.apiLevel.text = getString(R.string.api_level, version)

        //Chapter 2 Challenge 1
        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
    }

    //Chapter 3 Challenge 1
    private fun checkButtons(){
        if(quizViewModel.currentQuestionAlreadyAnswered){
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
        else{
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        checkButtons()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer: Boolean = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        // Chapter 1 Challenge 1
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun checkGameOver(){
        if(quizViewModel.gameOver){
            val percentageCalculation = quizViewModel.getGameResults()
            Toast.makeText(this, getString(R.string.answered_correct, percentageCalculation.toString()), Toast.LENGTH_SHORT).show()
        }
    }
}