package com.example.pepper_entertainment_app_old_android_studio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.ListenResult
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.*
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(), RobotLifecycleCallbacks {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_main)

        QiSDK.register(this, this);
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        ctx = qiContext
    }

    override fun onRobotFocusLost() {
        ctx = null
    }

    override fun onRobotFocusRefused(reason: String?) {
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    companion object{
        var ctx: QiContext? = null
        var sayFuture: Future<*> ?= null
        var animateFuture: Future<*> ?= null
        var listenFuture: Future<ListenResult> ?= null
    }
}
