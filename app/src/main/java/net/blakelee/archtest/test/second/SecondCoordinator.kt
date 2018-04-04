package net.blakelee.archtest.test.second

import android.view.View
import com.squareup.coordinators.Coordinator
import kotlinx.android.synthetic.main.test_layout_four.view.*
import net.blakelee.archtest.MainActivity

class SecondCoordinator(private val screen: SecondScreen) : Coordinator() {

    private lateinit var activity: MainActivity

    override fun attach(view: View) {
        activity = view.context as MainActivity
    }
}