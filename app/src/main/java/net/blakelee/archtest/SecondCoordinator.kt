package net.blakelee.archtest

import android.view.View
import com.squareup.coordinators.Coordinator
import kotlinx.android.synthetic.main.test_layout_four.view.*

class SecondCoordinator : Coordinator() {

    private lateinit var activity: MainActivity

    override fun attach(view: View) {
        activity = view.context as MainActivity

        view.close.setOnClickListener {
            activity.popCurrent()
        }
    }

    override fun detach(view: View) {
        super.detach(view)
    }
}