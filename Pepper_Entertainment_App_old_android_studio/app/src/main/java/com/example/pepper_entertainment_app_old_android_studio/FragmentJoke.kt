package com.example.pepper_entertainment_app_old_android_studio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.example.pepper_entertainment_app_old_android_studio.databinding.ActivityMainBinding
import com.example.pepper_entertainment_app_old_android_studio.databinding.FragmentJokeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random


class FragmentJoke : Fragment() {

    private lateinit var binding : FragmentJokeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke, container, false);
        val jokes: ArrayList<Joke> = readJokes()
        sayJoke(jokes)
        return binding.root
    }

    private fun sayJoke(jokes: ArrayList<Joke>) = CoroutineScope(Main).launch {
        val randomJoke: Int = Random.nextInt(0, jokes.size)
        binding.tvJoke.text = jokes.get(randomJoke).joke

        CoroutineScope(IO).launch {
            var phrase = Phrase(jokes.get(randomJoke).joke)
            val sayJoke: Say = SayBuilder.with(MainActivity.ctx)
                .withPhrase(phrase)
                .build()
            sayJoke.run()
            CoroutineScope(Main).launch {
                delay(1000)
                binding.tvJoke.text = jokes.get(randomJoke).punchline
                CoroutineScope(IO).launch {
                    phrase = Phrase(jokes.get(randomJoke).punchline)
                    val sayPunchline: Say = SayBuilder.with(MainActivity.ctx)
                        .withPhrase(phrase)
                        .build()
                    sayPunchline.run()
                    CoroutineScope(Main).launch {
                        delay(1000)
                        val action = FragmentJokeDirections.actionFragmentJokeToFragmentMode(false)
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                }
            }
        }
    }

    private fun readJokes(): ArrayList<Joke>{
        val jokeStream : InputStream = resources.openRawResource(R.raw.jokes)
        val jokes : ArrayList<Joke> = ArrayList()
        val br = BufferedReader(InputStreamReader(jokeStream))
        //Skips first line
        br.readLine()
        var line: String ?= br.readLine()
        while(line != null){
            val joke: Joke = line2Joke(line)
            jokes.add(joke)
            line = br.readLine()
        }
        return jokes
    }

    private fun line2Joke(line: String): Joke{
        val joke: String = line.split(";")[0]
        val punchline: String = line.split(";")[1]
        return Joke(joke, punchline)
    }
}