package net.blakelee.reactiveworkflows.test.second

import android.view.View
import com.squareup.coordinators.Coordinator
import kotlinx.android.synthetic.main.test_layout_two.view.*
import net.blakelee.reactiveworkflows.MainActivity

class SecondCoordinator(private val screen: SecondScreen) : Coordinator() {

    private lateinit var activity: MainActivity

    override fun attach(view: View) {
        activity = view.context as MainActivity

        view.button.setOnClickListener { screen.eventHandler.secondTest() }
    }
}