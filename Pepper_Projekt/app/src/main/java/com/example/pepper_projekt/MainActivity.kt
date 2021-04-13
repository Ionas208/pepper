package com.example.pepper_projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.ListenResult

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
        var sayFuture: Future<*>?= null
        var animateFuture: Future<*>?= null
        var listenFuture: Future<ListenResult>?= null
    }
}