package com.example.pepper_entertainment_app_old_android_studio

import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.example.pepper_entertainment_app_old_android_studio.databinding.FragmentDanceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FragmentDance : Fragment() {

    private lateinit var binding : FragmentDanceBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dance, container, false)

        //Back button handler
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navBack()
            }
        })

        //Button handler for image button
        binding.btDance.setOnClickListener{
            navBack()
        }

        //Animation
        CoroutineScope(IO).launch {
            RobotUtil.prepareAnimate()
            val animation: Animation = AnimationBuilder.with(MainActivity.ctx)
                .withResources(R.raw.dance)
                .build()
            val animate: Animate = AnimateBuilder.with(MainActivity.ctx)
                .withAnimation(animation)
                .build()

            mediaPlayer = MediaPlayer.create(context, R.raw.maiahi)
            animate.addOnStartedListener {
                mediaPlayer.start()
            }
            MainActivity.animateFuture = animate.async().run()
            MainActivity.animateFuture?.andThenConsume {
                navBack()
            }
        }
        return binding.root
    }

    private fun navBack(){
        mediaPlayer.stop()
        mediaPlayer.release()
        RobotUtil.cancelAllFutures()
        val action = FragmentDanceDirections.actionFragmentDanceToFragmentMode(false)
        Navigation.findNavController(binding.root).navigate(action)
    }
}