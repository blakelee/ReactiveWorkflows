package net.blakelee.archtest.test.first

import android.graphics.Color
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.squareup.coordinators.Coordinator
import kotlinx.android.synthetic.main.test_layout_one.view.*
import net.blakelee.archtest.MainActivity

class FirstCoordinator(private val screen: FirstScreen) : Coordinator(),
    OnMapReadyCallback {

    private lateinit var activity: MainActivity

    private var map: GoogleMap? = null

    override fun attach(view: View) {
        activity = view.context as MainActivity


        view.createTwo.setOnClickListener {
            screen.eventHandler.firstEvents()
        }

        view.mapView.getMapAsync(this)
        view.mapView.onCreate(null)
        view.mapView.onResume()
    }

    override fun detach(view: View) {
        view.mapView.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        val polygon = PolygonOptions().add(
                LatLng(-89.999999999999, -180.0),
                LatLng(89.99999999999, -180.0),
                LatLng(89.99999999999, 179.99999999),
                LatLng(-89.99999999999, 179.99999999),
                LatLng(-89.99999999999, 0.0))

        polygon.addHole(listOf(
                LatLng(29.68224948021748, -23.676965750000022),
                LatLng(29.68224948021748, 44.87772174999998),
                LatLng(71.82725578445813, 44.87772174999998),
                LatLng(71.82725578445813, -23.676965750000022)))

        polygon.fillColor(Color.argb(75, 125, 125, 220))
        polygon.strokeColor(Color.argb(205, 125, 125, 220))
        polygon.strokeWidth(2.0f)

        map?.addPolygon(polygon)
    }
}