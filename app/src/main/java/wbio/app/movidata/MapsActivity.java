package wbio.app.movidata;

import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String ruta,ciudad;

    ArrayList<String> latlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //obtener datos recibidos de la Recorridos
        ruta = "";
        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");
        ruta = getIntent().getExtras().getString("ruta");
        //----------------------------------------

        //Leer archivo de ruta
        Archivo myFile = new Archivo();
        String directorio = MapsActivity.this.getFilesDir().toString()+"/rutas/";
        latlong = myFile.GetLatLong(directorio, ruta);






    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                //Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            // Log.e(TAG, "Can't find style. Error: ", e);
        }


        // dibujar ruta
        double lat0,lon0, la1,lon1;
        lat0 = 20.966940;
        lon0 = -89.624017;

        //LatLng puntos = new LatLng(la1, lon1);
        ArrayList<LatLng> puntos = new ArrayList<LatLng>();
        for(int i=0;i<latlong.size()-1;i++)
        {
            String [] separados = latlong.get(i).split(",");
            la1 = Double.parseDouble(separados[0]);
            lon1 = Double.parseDouble(separados[1]);
            LatLng temp = new LatLng(la1,lon1);
            puntos.add(temp);
            if(i==0){
                lat0=Double.parseDouble(separados[0]);
                lon0=Double.parseDouble(separados[1]);
            }

        }

        //Polyline polyline1 = googleMap.addPolyline(new PolylineOptions().clickable(true).add(  ));

        //List<LatLng> points = decodePoly(_path); // list of latlng
        for (int i = 0; i < puntos.size() - 1; i++) {
            LatLng src = puntos.get(i);
            LatLng dest = puntos.get(i + 1);

            // mMap is the Map Object
            Polyline line = mMap.addPolyline(
                    new PolylineOptions().add(
                            new LatLng(src.latitude, src.longitude),
                            new LatLng(dest.latitude,dest.longitude)
                    ).width(8).color(0xffaa0000).geodesic(true)
            );
        }



        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat0, lon0), 16));


    }
}