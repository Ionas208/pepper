package com.example.pepper_entertainment_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    var ctx: QiContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}