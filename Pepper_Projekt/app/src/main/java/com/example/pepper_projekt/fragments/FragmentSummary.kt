package com.example.pepper_projekt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.example.pepper_entertainment_app.FragmentQuizArgs
import com.example.pepper_projekt.MainActivity
import com.example.pepper_projekt.R
import com.example.pepper_projekt.bl.RobotUtil
import com.example.pepper_projekt.databinding.FragmentStartBinding
import com.example.pepper_projekt.databinding.FragmentSummaryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class FragmentSummary : Fragment() {

    private lateinit var binding : FragmentSummaryBinding
    val args: FragmentSummaryArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)

        binding.tvScore.text = args.score.toString()
        binding.tvMaxScore.text = args.maxScore.toString()

        listenForBack()

        binding.btBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentSummary_to_fragmentMode)
        }

        return binding.root
    }

    private fun listenForBack(){
        CoroutineScope(Dispatchers.IO).launch {
            val texts = arrayOf("back", "zurück")
            val phrases: PhraseSet = PhraseSetBuilder.with(MainActivity.ctx)
                    .withTexts(*texts)
                    .build()
            val listen: Listen = ListenBuilder.with(MainActivity.ctx)
                    .withPhraseSet(phrases)
                    .build()

            RobotUtil.prepareListen()
            MainActivity.listenFuture = listen.async().run()
            MainActivity.listenFuture?.andThenConsume { future ->
                val phrase: Phrase = future.heardPhrase
                if(phrase.text in texts){
                    CoroutineScope(Main).launch {navBack()}
                }
            }
        }
    }

    private fun navBack(){
        Navigation.findNavController(binding.root).navigate(R.id.action_fragmentSummary_to_fragmentMode)
    }
}