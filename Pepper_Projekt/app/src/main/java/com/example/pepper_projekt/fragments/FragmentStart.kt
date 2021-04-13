package com.example.pepper_entertainment_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.example.pepper_projekt.MainActivity
import com.example.pepper_projekt.R
import com.example.pepper_projekt.bl.RobotUtil
import com.example.pepper_projekt.databinding.FragmentStartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentStart : Fragment() {

    private lateinit var binding : FragmentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        nav2Mode()
        return binding.root
    }

    private fun nav2Mode() = CoroutineScope(Main).async {
        waitForCTX()
        CoroutineScope(IO).launch {
            RobotUtil.animate(R.raw.hello_01)
            RobotUtil.say("Hallo!")
            CoroutineScope(Main).launch {
                delay(2000)
                binding.tvHello.text = "Ich bin Pepper!"
                CoroutineScope(IO).launch {
                    RobotUtil.say("Ich bin Pepper")
                    CoroutineScope(Main).launch {
                        delay(2000)
                        Navigation.findNavController(binding.root).navigate(R.id.action_fragmentStart_to_fragmentMode)
                    }
                }
            }
        }
    }

    private suspend fun waitForCTX(){
        var counter = 0
        while(MainActivity.ctx == null && counter<=10){
            delay(200)
            counter++
        }
    }
}