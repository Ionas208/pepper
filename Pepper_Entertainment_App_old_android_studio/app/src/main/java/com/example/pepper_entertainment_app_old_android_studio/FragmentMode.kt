package com.example.pepper_entertainment_app_old_android_studio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.`object`.conversation.ListenResult
import com.aldebaran.qi.sdk.`object`.conversation.Phrase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMode.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMode : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var nav: NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_mode, container, false)
        val btDance: Button = view.findViewById(R.id.btDance)
        val btJoke: Button = view.findViewById(R.id.btJoke)
        val btQuiz: Button = view.findViewById(R.id.btQuiz)

        //nav = Navigation.findNavController(view)

        btDance.setOnClickListener {
            //navigateToDance()
            Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentDance)
        }
        btJoke.setOnClickListener {
            //navigateToJoke()
            Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentJoke)
        }
        btQuiz.setOnClickListener {
            //navigateToQuiz()
            Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentQuiz)
        }

        /*
            Phrases
         */
        val dancePhrases: Future<PhraseSet> = PhraseSetBuilder.with(MainActivity.ctx)
            .withTexts("Tanz", "Dance")
            .buildAsync()
        val jokePhrases: Future<PhraseSet>  = PhraseSetBuilder.with(MainActivity.ctx)
            .withTexts("Witz", "Joke")
            .buildAsync()
        val quizPhrases: Future<PhraseSet>  = PhraseSetBuilder.with(MainActivity.ctx)
            .withTexts("Quiz")
            .buildAsync()

        /*
            Listens
         */
        /*val danceListen: Listen = ListenBuilder.with(MainActivity.ctx)
            .withPhraseSet(dancePhrases.andThenApply{ph -> ph.phrases})
            .build()
        dancePhrases.andThenApply{ph -> ph}
        val jokeListen: Listen = ListenBuilder.with(MainActivity.ctx)
            .withPhraseSet(jokePhrases)
            .build()
        val quizListen: Listen = ListenBuilder.with(MainActivity.ctx)
            .withPhraseSet(quizPhrases)
            .build()*/

        /*
            Futures
         */
        /*val danceFuture: Future<ListenResult> = danceListen.async().run()
        val jokeFuture: Future<ListenResult> = jokeListen.async().run()
        val quizFuture: Future<ListenResult> = quizListen.async().run()

        danceFuture.andThenConsume {future ->
            val match: PhraseSet = future.matchedPhraseSet
            if(match.phrases.size > 0){
                //navigateToDance()
                Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentDance)
            }
        }

        jokeFuture.andThenConsume {future ->
            val match: PhraseSet = future.matchedPhraseSet
            if(match.phrases.size > 0){
                //navigateToJoke()
                Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentJoke)
            }
        }

        quizFuture.andThenConsume {future ->
            val match: PhraseSet = future.matchedPhraseSet
            if(match.phrases.size > 0){
                //navigateToQuiz()
                Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentQuiz)
            }
        }
*/
        return view
    }

    private fun navigateToDance(){
        nav?.navigate(R.id.action_fragmentMode_to_fragmentDance)
    }

    private fun navigateToJoke(){
        nav?.navigate(R.id.action_fragmentMode_to_fragmentJoke)
    }

    private fun navigateToQuiz(){
        nav?.navigate(R.id.action_fragmentMode_to_fragmentQuiz)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMode().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}