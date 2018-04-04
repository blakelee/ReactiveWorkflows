package net.blakelee.archtest.test.first

import android.view.View
import com.squareup.coordinators.Coordinator
import net.blakelee.archtest.MainActivity

class FirstCoordinator(private val screen: FirstScreen) : Coordinator() {

    private lateinit var activity: MainActivity

    override fun attach(view: View) {
        activity = view.context as MainActivity
    }

    override fun detach(view: View) {

    }
}