package com.example.exameniib_monicalucio

import com.example.exameniib_monicalucio.Models.Estudiante
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar

class GoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    private lateinit var estudiante: Estudiante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        // Recibe el objeto Parcelable
        estudiante = intent.getParcelableExtra<Estudiante>("estudiante") ?: Estudiante(0,"","","",0.0,0,0.0,0.0)

        solicitarPermisos()
        iniciarLogicaMapa()
        val estudianteName:TextView = findViewById(R.id.tv_google_maps_name)
        estudianteName.text = estudiante.nombre
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap) {
                mapa = googleMap
                establecerConfiguracionMapa()
                moverCamaraConZoom(LatLng(estudiante.latitud, estudiante.longitud), 17f)
                anadirMarcador(LatLng(estudiante.latitud, estudiante.longitud), "Ubicación de ${estudiante.nombre}")
                anadirPolilinea()
                anadirPoligono()
            }
        }
    }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_google_maps),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    } // cl_google_maps
    fun anadirPolilinea(){
        with(mapa){
            val polilineaUno = mapa.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.1758614401269219, -78.48571121689449),
                        LatLng(-0.17843634842358283, -78.48244965071446),
                        LatLng(-0.17843634842358283, -78.47927391522337)
                    )
            )
            polilineaUno.tag = "Polilinea-uno"
        }
    }
    fun anadirPoligono(){
        with(mapa){
            val poligonoUno = mapa.addPolygon(
                PolygonOptions().clickable(true)
                    .add(
                        LatLng(-0.172342398151301, -78.48596870896792),
                        LatLng(-0.17508896750495734, -78.48124802107579),
                        LatLng(-0.17345819199934728, -78.47584068767206)
                    )
            )
        }
    }
    fun anadirMarcador(latLang:LatLng, title:String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(latLang)
                .title(title)
        )!!
    }


    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }
    fun moverCamaraConZoom(latLang: LatLng, zoom: Float = 10f){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
            latLang, zoom
        ))
    }





















}