package com.seas.usuario.grouponandroidstudiov2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
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
import com.seas.usuario.grouponandroidstudiov2.tools.PermissionUtils;

//public class MapaActivity extends FragmentActivity {
public class MapaActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "com.seas.TuristicLocation.activityprincipal";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mapa;

    private static ServiciosTuristicLocation serviciosTuristicLocation;
    private static MapaActivity mapaActivity;


    public static MapaActivity getInstance() {
        return mapaActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapaActivity = this;
        serviciosTuristicLocation = new ServiciosTuristicLocation();
//        cargaGoogleMap();
        // Recuperamos los lugares depende de la que se haya pulsado
//        Bundle extras = getIntent().getExtras();
        //if (extras != null) {
        //String parametro = extras.getString(VistaListaIconos.MyKey);

        // utilizamos el parametro
        serviciosTuristicLocation.miThread();
        //}
    }


//    private void cargaGoogleMap() {
//        if (mMap == null) {
//            mMap = ((SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map)).getMap();
//            if (mMap != null) {
//                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                mMap.setMyLocationEnabled(true);
//
//                String localLong = GrouponData.getLocalSeleccionado().getLocalLong();
//                String localLat = GrouponData.getLocalSeleccionado().getLocalLat();
//
//                LatLng localLatLong = new LatLng(Double.parseDouble(localLong), Double.parseDouble(localLat));
//                CameraPosition camPos = new CameraPosition.Builder()
//                        .target(localLatLong)   //Centramos el mapa en el local seleccionado
//                        .zoom(18)               //Establecemos el zoom en 18
//                        .build();
//
//                CameraUpdate camUpd3 =
//                        CameraUpdateFactory.newCameraPosition(camPos);
//
//                mMap.animateCamera(camUpd3);
//            }
//        }
//    }

    public void anadirMarca(String nombre,String descripcion, String lat, String lng, Bitmap img){

        BitmapDescriptor imagen = BitmapDescriptorFactory.fromBitmap(img);

        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                .title(nombre)
                .snippet(descripcion)
                .icon(imagen));
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        mapa = mMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mapa.setMyLocationEnabled(true);

        mapa.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        String localLong = GrouponData.getLocalSeleccionado().getLocalLong();
        String localLat = GrouponData.getLocalSeleccionado().getLocalLat();

        LatLng localLatLong = new LatLng(Double.parseDouble(localLong), Double.parseDouble(localLat));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(localLatLong)   //Centramos el mapa en el local seleccionado
                .zoom(18)               //Establecemos el zoom en 18
                .build();

        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);

        mapa.animateCamera(camUpd3);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mapa != null) {
            // Access to the location has been granted to the app.
            mapa.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
