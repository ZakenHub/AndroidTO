package com.seas.usuario.grouponandroidstudiov2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seas.usuario.grouponandroidstudiov2.datos.GrouponData;
import com.seas.usuario.grouponandroidstudiov2.threads.ServiciosTuristicLocation;

public class MapaActivity extends FragmentActivity {
//public class MapaActivity extends Activity implements OnMapReadyCallback {
    private static final String TAG = "com.seas.TuristicLocation.activityprincipal";
    private GoogleMap mMap;

    private static ServiciosTuristicLocation serviciosTuristicLocation;
    private static MapaActivity mapaActivity;

    public static MapaActivity getInstance() {
        return mapaActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        mapaActivity = this;
        serviciosTuristicLocation = new ServiciosTuristicLocation();
        cargaGoogleMap();
        // Recuperamos los lugares depende de la que se haya pulsado
        Bundle extras = getIntent().getExtras();
        //if (extras != null) {
        //String parametro = extras.getString(VistaListaIconos.MyKey);

        // utilizamos el parametro
        serviciosTuristicLocation.miThread();
        //}
    }


    private void cargaGoogleMap() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setMyLocationEnabled(true);

                String localLong = GrouponData.getLocalSeleccionado().getLocalLong();
                String localLat = GrouponData.getLocalSeleccionado().getLocalLat();

                LatLng localLatLong = new LatLng(Double.parseDouble(localLong), Double.parseDouble(localLat));
                CameraPosition camPos = new CameraPosition.Builder()
                        .target(localLatLong)   //Centramos el mapa en el local seleccionado
                        .zoom(18)               //Establecemos el zoom en 18
                        .build();

                CameraUpdate camUpd3 =
                        CameraUpdateFactory.newCameraPosition(camPos);

                mMap.animateCamera(camUpd3);
            }
        }
    }

    public void anadirMarca(String nombre,String descripcion, String lat, String lng, Bitmap img){

        BitmapDescriptor imagen = BitmapDescriptorFactory.fromBitmap(img);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                .title(nombre)
                .snippet(descripcion)
                .icon(imagen));
    }

//    @Override
//    public void onMapReady(GoogleMap mMap) {
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setMyLocationEnabled(true);
//
//        String localLong = GrouponData.getLocalSeleccionado().getLocalLong();
//        String localLat = GrouponData.getLocalSeleccionado().getLocalLat();
//
//        LatLng localLatLong = new LatLng(Double.parseDouble(localLong), Double.parseDouble(localLat));
//        CameraPosition camPos = new CameraPosition.Builder()
//                .target(localLatLong)   //Centramos el mapa en el local seleccionado
//                .zoom(18)               //Establecemos el zoom en 18
//                .build();
//
//        CameraUpdate camUpd3 =
//                CameraUpdateFactory.newCameraPosition(camPos);
//
//        mMap.animateCamera(camUpd3);
//    }
}
