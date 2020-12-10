package com.pepper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    private var qiContext: QiContext? = null

    fun getQiContext(): QiContext? {
        return qiContext
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*CoroutineScope(Main).launch {
            (activity as).getQiContext().let { cxt ->

                CoroutineScope(IO).launch {
                    Robot.say("Hay", cxt, true).AndThenConsume {

                        CoroutineScope(Main).launch {
                            

                        }
                    }
                }


            }

        }*/

        QiSDK.register(this, this)
    }


    override fun onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        // The robot focus is gained.
        this.qiContext = qiContext
    }

    override fun onRobotFocusLost() {
        // The robot focus is lost.
        this.qiContext = null
    }

    override fun onRobotFocusRefused(reason: String) {
        // The robot focus is refused.
    }
}