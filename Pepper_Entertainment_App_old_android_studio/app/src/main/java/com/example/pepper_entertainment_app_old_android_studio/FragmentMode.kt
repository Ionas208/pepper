package com.example.pepper_entertainment_app_old_android_studio

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.*
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.example.pepper_entertainment_app_old_android_studio.databinding.FragmentModeBinding
import com.example.pepper_entertainment_app_old_android_studio.databinding.FragmentModeNewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentMode : Fragment() {

    val dancePhrases = arrayOf<String>("Dance", "Tanz")
    val jokePhrases = arrayOf<String>("Witz", "Joke")
    val quizPhrases = arrayOf<String>("Quiz")

    var listenFuture: Future<ListenResult>? = null

    val args: FragmentModeArgs by navArgs()

    private lateinit var binding : FragmentModeNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mode_new, container, false);

        val firstCall = args.firstCall
        if(firstCall){
            explainModes()
        }

        binding.btDance.setOnClickListener {
            CoroutineScope(IO).launch {
                listenFuture?.requestCancellation()
                waitForFutureCancellation(listenFuture)
                CoroutineScope(Main).launch{
                    navigateToDance()
                }
            }
        }
        binding.btJoke.setOnClickListener {
            CoroutineScope(IO).launch {
                listenFuture?.requestCancellation()
                waitForFutureCancellation(listenFuture)
                CoroutineScope(Main).launch{
                    navigateToJoke()
                }
            }
        }
        binding.btQuiz.setOnClickListener {
            CoroutineScope(IO).launch {
                listenFuture?.requestCancellation()
                waitForFutureCancellation(listenFuture)
                CoroutineScope(Main).launch{
                    navigateToQuiz()
                }
            }
        }

        CoroutineScope(IO).launch {
            val phrases: PhraseSet = PhraseSetBuilder.with(MainActivity.ctx)
                .withTexts(*(dancePhrases+jokePhrases+quizPhrases))
                .build()

            val listen: Listen = ListenBuilder.with(MainActivity.ctx)
                .withPhraseSet(phrases)
                .build()

            listenFuture = listen.async().run()

            listenFuture?.andThenConsume {future ->
                val phrase:Phrase = future.heardPhrase
                if(dancePhrases.contains(phrase.text)){
                    navigateToDance()
                }
                else if(jokePhrases.contains(phrase.text)){
                    navigateToJoke()
                }
                else if(quizPhrases.contains(phrase.text)){
                    navigateToQuiz()
                }
            }

        }
        return binding.root
    }

    private fun navigateToDance(){
            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentMode_to_fragmentDance)
    }

    private fun navigateToJoke(){
        Navigation.findNavController(binding.root).navigate(R.id.action_fragmentMode_to_fragmentJoke)
    }

    private fun navigateToQuiz(){
        Navigation.findNavController(binding.root).navigate(R.id.action_fragmentMode_to_fragmentQuiz)
    }

    private suspend fun waitForFutureCancellation(future: Future<*>?){
        future?.let{
            while(!future.isDone && !future.isCancelled){
                delay(200)
            }
        }
    }

    private fun explainModes() = CoroutineScope(IO).async{
        val phrase = Phrase("Mode Explanation Test")
        val say: Say = SayBuilder.with(MainActivity.ctx)
            .withPhrase(phrase)
            .build()
        say.run()
    }
}