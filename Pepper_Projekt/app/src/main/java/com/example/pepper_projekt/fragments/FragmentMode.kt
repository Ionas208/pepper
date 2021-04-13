package com.example.pepper_entertainment_app

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
import com.example.pepper_projekt.MainActivity
import com.example.pepper_projekt.R
import com.example.pepper_projekt.bl.RobotUtil
import com.example.pepper_projekt.databinding.FragmentModeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentMode : Fragment() {

    //Phrases for Listen
    val dancePhrases = arrayOf<String>("Dance", "Tanz")
    val jokePhrases = arrayOf<String>("Witz", "Joke")
    val quizPhrases = arrayOf<String>("Quiz")

    val args: FragmentModeArgs by navArgs()

    private lateinit var binding : FragmentModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mode, container, false)

        //Explaining modes
        val firstCall = args.firstCall
        if(firstCall){
            explainModes()
        }

        //=====================================
        // OnClickListener for Navigation
        //=====================================
        binding.btDance.setOnClickListener {
            CoroutineScope(IO).launch {
                MainActivity.listenFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.listenFuture)
                CoroutineScope(Main).launch{
                    navigateToDance()
                }
            }
        }
        binding.btJoke.setOnClickListener {
            CoroutineScope(IO).launch {
                MainActivity.listenFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.listenFuture)
                CoroutineScope(Main).launch{
                    navigateToJoke()
                }
            }
        }
        binding.btQuiz.setOnClickListener {
            CoroutineScope(IO).launch {
                MainActivity.listenFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.listenFuture)
                CoroutineScope(Main).launch{
                    navigateToQuiz()
                }
            }
        }

        //=====================================
        // Listening for Navigation
        //=====================================
        CoroutineScope(IO).launch {
            val phrases: PhraseSet = PhraseSetBuilder.with(MainActivity.ctx)
                .withTexts(*(dancePhrases+jokePhrases+quizPhrases))
                .build()

            val listen: Listen = ListenBuilder.with(MainActivity.ctx)
                .withPhraseSet(phrases)
                .build()

            //Cancel futures for listen
            RobotUtil.prepareListen()
            MainActivity.listenFuture = listen.async().run()

            //Evaluate Result
            MainActivity.listenFuture?.andThenConsume {future ->
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

    private fun explainModes() = CoroutineScope(IO).launch{
        RobotUtil.say("Hallo, mein Name ist Pepper, du kannst hier zwischen Tanz, Witz oder" +
                " Quiz aussuchen. Du kannst mit mir sprechen oder auf meinen Bildschirm klicken.")
    }

}