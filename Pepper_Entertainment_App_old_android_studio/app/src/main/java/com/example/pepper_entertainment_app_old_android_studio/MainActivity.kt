package com.example.pepper_entertainment_app_old_android_studio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        QiSDK.register(this, this);
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        ctx = qiContext
        val say: Say = SayBuilder.with(ctx) // Create the builder with the context.
            .withText("Hello human!") // Set the text to say.
            .build() // Build the say action.
        say.run()
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
    }
}
