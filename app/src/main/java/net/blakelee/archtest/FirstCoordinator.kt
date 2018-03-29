package net.blakelee.archtest

import android.view.View
import com.squareup.coordinators.Coordinator
import kotlinx.android.synthetic.main.test_layout_one.view.*

class FirstCoordinator : Coordinator() {

    private lateinit var activity: MainActivity

    override fun attach(view: View) {
        activity = view.context as MainActivity

        view.createFour.setOnClickListener {
            activity.pushView(R.layout.test_layout_four)
        }
    }

    override fun detach(view: View) {
    }
}