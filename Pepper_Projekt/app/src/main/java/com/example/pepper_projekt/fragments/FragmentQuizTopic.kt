package com.example.pepper_projekt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.example.pepper_entertainment_app.FragmentJokeDirections
import com.example.pepper_projekt.MainActivity
import com.example.pepper_projekt.R
import com.example.pepper_projekt.beans.Topic
import com.example.pepper_projekt.bl.RobotUtil
import com.example.pepper_projekt.databinding.FragmentQuizTopicBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentQuizTopic : Fragment() {

    private lateinit var binding : FragmentQuizTopicBinding
    private lateinit var numberTopics: Map<Int, Topic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_topic, container, false)

        numberTopics = mapOf(
                1 to Topic.TOPIC1,
                2 to Topic.TOPIC1,
                3 to Topic.TOPIC3
        )

        binding.btTopic1.setOnClickListener{
            nav2Quiz(Topic.TOPIC1)
        }

        binding.btTopic2.setOnClickListener{
            nav2Quiz(Topic.TOPIC2)
        }

        binding.btTopic3.setOnClickListener{
            nav2Quiz(Topic.TOPIC3)
        }

        listenForTopic()

        return binding.root
    }

    private fun nav2Quiz(topic: Topic){
        val action = FragmentQuizTopicDirections.actionFragmentQuizTopicToFragmentQuiz(topic)
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun listenForTopic(){
        CoroutineScope(Dispatchers.IO).launch {
            val numbers: Array<String> = arrayOf("1", "2", "3")
            val topics = Topic.values().map {t -> t.toString()}

            val phrases: PhraseSet = PhraseSetBuilder.with(MainActivity.ctx)
                    .withTexts(*(numbers+topics))
                    .build()
            val listen: Listen = ListenBuilder.with(MainActivity.ctx)
                    .withPhraseSet(phrases)
                    .build()

            RobotUtil.prepareListen()
            MainActivity.listenFuture = listen.async().run()
            MainActivity.listenFuture?.andThenConsume { future ->
                val phrase: Phrase = future.heardPhrase
                if(phrase.text in numbers){
                    val number: Int = phrase.text.toInt()
                    CoroutineScope(Dispatchers.Main).launch { numberTopics[number]?.let { nav2Quiz(it) } }
                }else if(phrase.text in topics) {
                    CoroutineScope(Dispatchers.Main).launch { nav2Quiz(Topic.valueOf(phrase.text)) }
                }
            }
        }
    }
}