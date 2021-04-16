package com.example.pepper_projekt.bl

import androidx.annotation.RawRes
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.example.pepper_projekt.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RobotUtil {
    companion object{
        fun say(text: String){
            CoroutineScope(IO).launch {
                if(MainActivity.sayFuture != null){
                    MainActivity.sayFuture?.requestCancellation()
                    waitForFutureCancellation(MainActivity.sayFuture)
                }
                if(MainActivity.listenFuture != null){
                    MainActivity.listenFuture?.requestCancellation()
                    waitForFutureCancellation(MainActivity.listenFuture)
                }
                var phrase = Phrase(text)
                var say: Say = SayBuilder.with(MainActivity.ctx)
                    .withPhrase(phrase)
                    .build()
                MainActivity.sayFuture = say.async().run()
            }
        }

        suspend fun prepareListen(){
            if (MainActivity.sayFuture != null) {
                MainActivity.sayFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.sayFuture)
            }
            if (MainActivity.listenFuture != null) {
                MainActivity.listenFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.listenFuture)
            }
        }

        fun animate(resource: Int){
            CoroutineScope(IO).launch {
                if(MainActivity.animateFuture != null){
                    MainActivity.animateFuture?.requestCancellation()
                    waitForFutureCancellation(MainActivity.animateFuture)
                }
                val animation: Animation = AnimationBuilder.with(MainActivity.ctx)
                    .withResources(resource)
                    .build()
                val animate: Animate = AnimateBuilder.with(MainActivity.ctx)
                    .withAnimation(animation)
                    .build()

                MainActivity.animateFuture = animate.async().run()
            }
        }

        suspend fun prepareAnimate(){
            if(MainActivity.animateFuture != null){
                MainActivity.animateFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.animateFuture)
            }
        }

        fun cancelAllFutures(){
            CoroutineScope(IO).launch {
                if (MainActivity.sayFuture != null) {
                    MainActivity.sayFuture?.requestCancellation()
                    waitForFutureCancellation(MainActivity.sayFuture)
                }
                if(MainActivity.animateFuture != null){
                    MainActivity.animateFuture?.requestCancellation()
                    waitForFutureCancellation(MainActivity.animateFuture)
                }

                if(MainActivity.listenFuture != null){
                    MainActivity.listenFuture?.requestCancellation()
                    waitForFutureCancellation(MainActivity.listenFuture)
                }
            }
        }

        suspend fun waitForAllFutureCancellations(){
            if (MainActivity.sayFuture != null) {
                MainActivity.sayFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.sayFuture)
            }
            if(MainActivity.animateFuture != null){
                MainActivity.animateFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.animateFuture)
            }

            if(MainActivity.listenFuture != null){
                MainActivity.listenFuture?.requestCancellation()
                waitForFutureCancellation(MainActivity.listenFuture)
            }
        }

        private suspend fun waitForFutureCancellation(future: Future<*>?){
            future?.let{
                while(!future.isDone && !future.isCancelled){
                    delay(200)
                }
            }
        }
    }
}