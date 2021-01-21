package com.example.pepper_entertainment_app_old_android_studio

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.ListenResult
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentMode : Fragment() {

    val dancePhrases = arrayOf<String>("Dance", "Tanz")
    val jokePhrases = arrayOf<String>("Witz", "Joke")
    val quizPhrases = arrayOf<String>("Quiz")

    var listenFuture: Future<ListenResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_mode_new, container, false)
        val btDance: ImageButton = view.findViewById(R.id.btDance)
        val btJoke: ImageButton = view.findViewById(R.id.btJoke)
        val btQuiz: ImageButton = view.findViewById(R.id.btQuiz)

        btDance.setOnClickListener {
            CoroutineScope(IO).launch {
                listenFuture?.requestCancellation()
                waitForFutureCancellation(listenFuture)
                CoroutineScope(Main).launch{
                    navigateToDance()
                }
            }
        }
        btJoke.setOnClickListener {
            CoroutineScope(IO).launch {
                listenFuture?.requestCancellation()
                waitForFutureCancellation(listenFuture)
                CoroutineScope(Main).launch{
                    navigateToJoke()
                }
            }
        }
        btQuiz.setOnClickListener {
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
        FragmentMode.view = view
        return view
    }

    private fun navigateToDance(){
        val v = FragmentMode.view
        if(v != null){
            Navigation.findNavController(v).navigate(R.id.action_fragmentMode_to_fragmentDance)
        }
    }

    private fun navigateToJoke(){
        val v = FragmentMode.view
        if(v != null){
            Navigation.findNavController(v).navigate(R.id.action_fragmentMode_to_fragmentJoke)
        }
    }

    private fun navigateToQuiz(){
        val v = FragmentMode.view
        if(v != null){
            Navigation.findNavController(v).navigate(R.id.action_fragmentMode_to_fragmentQuiz)
        }
    }

    private suspend fun waitForFutureCancellation(future: Future<*>?){
        future?.let{
            while(!future.isDone && !future.isCancelled){
                delay(200)
            }
        }
    }
    companion object{
        var view: View? = null
    }
}