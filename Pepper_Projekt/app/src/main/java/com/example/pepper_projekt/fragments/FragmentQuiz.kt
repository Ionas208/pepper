package com.example.pepper_entertainment_app

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.example.pepper_projekt.MainActivity
import com.example.pepper_projekt.R
import com.example.pepper_projekt.beans.Answer
import com.example.pepper_projekt.beans.Question
import com.example.pepper_projekt.beans.Topic
import com.example.pepper_projekt.bl.GameLogic
import com.example.pepper_projekt.bl.RobotUtil
import com.example.pepper_projekt.databinding.FragmentQuizBinding
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class FragmentQuiz : Fragment() {

    private lateinit var binding : FragmentQuizBinding

    val args: FragmentQuizArgs by navArgs()
    lateinit var gameLogic: GameLogic
    lateinit var buttons: Map<Answer, Button>
    lateinit var answerName: Map<String, Answer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CoroutineScope(IO).launch {
                    RobotUtil.waitForAllFutureCancellations()
                    CoroutineScope(Main).launch {
                        Navigation.findNavController(binding.root).navigate(R.id.action_fragmentQuiz_to_fragmentMode)
                    }
                }
            }
        })

        if(args.gameLogic == null){
            gameLogic = GameLogic(args.topic, getQuestionsForTopic(args.topic))
        }else{
            gameLogic = args.gameLogic!!
            gameLogic.currentQuestionIndex++
        }

        val q = gameLogic.questions.get(gameLogic.currentQuestionIndex)

        binding.tvQuestion.text = q.question
        buttons = mapOf(
            q.answers[0] to binding.btAnswerA,
            q.answers[1] to binding.btAnswerB,
            q.answers[2] to binding.btAnswerC,
            q.answers[3] to binding.btAnswerD
        )
        answerName = mapOf(
            "A" to q.answers[0],
            "B" to q.answers[1],
            "C" to q.answers[2],
            "D" to q.answers[3]
        )
        for((answer, button) in buttons){
            button.text = answer.answerText
            button.setOnClickListener {handleAnswer(answer)}
        }

        RobotUtil.say(q.question)
        listenForAnswer()
        return binding.root
    }

    private fun getQuestionsForTopic(topic: Topic): List<Question>{
        val mapper = jacksonObjectMapper()
        var questions: List<Question> = mapper.readValue(
            context?.getResources()?.openRawResource(R.raw.questions)!!
        )
        questions = questions.filter { q -> q.topic == topic}.shuffled()
        return questions
    }

    private fun handleAnswer(answer: Answer){
        val q = gameLogic.questions.get(gameLogic.currentQuestionIndex)

        if(answer.correct){
            RobotUtil.say("Good Job!")
            buttons[answer]?.setBackgroundColor(Color.GREEN)
            gameLogic.score++
        }else{
            RobotUtil.say("Better Luck Next Time!")
            buttons[answer]?.setBackgroundColor(Color.RED)
            val correctAnswer = q.answers.filter{ ans -> ans.correct}[0]
            buttons[correctAnswer]?.setBackgroundColor(Color.GREEN)
        }
        binding.btNext.visibility = View.VISIBLE

        binding.btNext.setOnClickListener {
            nav2Next()
        }
        listenForNext()
    }

    private fun listenForAnswer(){
        CoroutineScope(IO).launch {
            val phrases: PhraseSet = PhraseSetBuilder.with(MainActivity.ctx)
                .withTexts("A", "B", "C", "D")
                .build()
            val listen: Listen = ListenBuilder.with(MainActivity.ctx)
                .withPhraseSet(phrases)
                .build()

            RobotUtil.prepareListen()
            MainActivity.listenFuture = listen.async().run()
            MainActivity.listenFuture?.andThenConsume {future ->
                val phrase: Phrase = future.heardPhrase
                answerName[phrase.text]?.let {
                    CoroutineScope(Main).launch {handleAnswer(it)}
                }
            }
        }
    }

    private fun listenForNext(){
        CoroutineScope(IO).launch {
            val phraseTexts = arrayOf("next", "weiter")
            val phrases: PhraseSet = PhraseSetBuilder.with(MainActivity.ctx)
                .withTexts(*phraseTexts)
                .build()
            val listen: Listen = ListenBuilder.with(MainActivity.ctx)
                .withPhraseSet(phrases)
                .build()

            RobotUtil.prepareListen()
            MainActivity.listenFuture = listen.async().run()
            MainActivity.listenFuture?.andThenConsume {future ->
                val phrase: Phrase = future.heardPhrase
                if(phrase.text in phraseTexts){
                    CoroutineScope(Main).launch {nav2Next()}
                }
            }
        }
    }

    private fun nav2Next(){
        CoroutineScope(IO).launch {
            RobotUtil.waitForAllFutureCancellations()
            CoroutineScope(Main).launch {
                if(gameLogic.currentQuestionIndex < gameLogic.questions.size-1){
                    val action = FragmentQuizDirections.actionFragmentQuizSelf(args.topic, gameLogic)
                    Navigation.findNavController(binding.root).navigate(action)
                }else{
                    val action = FragmentQuizDirections.actionFragmentQuizToFragmentSummary(gameLogic.score, gameLogic.questions.size)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }

    }

}