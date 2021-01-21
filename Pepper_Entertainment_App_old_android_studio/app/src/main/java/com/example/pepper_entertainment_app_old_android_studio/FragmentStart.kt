package com.example.pepper_entertainment_app_old_android_studio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO


class FragmentStart : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        val btStart: Button = view.findViewById(R.id.btStart)

        val helloAnimation: Animation = AnimationBuilder.with(MainActivity.ctx)
            .withResources()
            .build()

        val hello: Animate = AnimateBuilder.with(MainActivity.ctx)
            .withAnimation(helloAnimation)
            .build()

        CoroutineScope(IO).run {
            hello.async().run()
        }

        btStart.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentStart2_to_fragmentMode)
        }
        return view
    }
}