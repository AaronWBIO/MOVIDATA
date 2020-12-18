package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.util.Date;




public class Map3 extends AppCompatActivity
{


    ImageView btn_subida, btn_bajada,btn_parada_ns,btn_parada_s;
    TextView tv_pasajeros,tv_distancia, tv_nombre_ruta;

    public static final int DEFAULT_UPDATE_INTERVAL = 8;
    public static final int FAST_UPDATE_INTERVAL = 8;
    public static final int PERMISSION_FINE_LOCATION = 99;

    boolean gpsTraking = false;
    int nPasajeros;
    int nsubidas;
    int nbajadas;
    int nparadas;
    int cont;
    float distancia;
    int d;
    String Paradero, incidentes, distancia_total;

    Archivo myFile;
    String uploadFilePath;


    Location currentLocation;
    //Location request is a config file for all settings related to FusedLocation ProviderCLient
    LocationRequest locationRequest;
    //Google location API services
    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;


    String ciudad, unidad, ruta, IMEI, tarifa, descripcion, imageUrl;
    ImageView map_logo, map_home, finalizar_ruta,btn_incidente;
    TextView string_paradas;
    Chronometer simpleChronometer;

    double startLat, startLon, endLat, endLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map3);

        unidad = "";
        ruta = "";
        IMEI = "";
        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");
        ruta = getIntent().getExtras().getString("ruta");
        IMEI = getIntent().getExtras().getString("IMEI");

        unidad = getIntent().getExtras().getString("unidad");
        tarifa = getIntent().getExtras().getString("tarifa");
        descripcion = getIntent().getExtras().getString("descripcion");
        imageUrl = getIntent().getExtras().getString("imageUrl");

        Paradero = "0";
        nPasajeros = 0;
        nsubidas = 0;
        nbajadas = 0;
        nparadas = 0;
        incidentes = "";
        distancia = 0;
        d = 0;
        cont = 0;
        startLon = 0;

        tv_nombre_ruta = this.findViewById(R.id.tv_nombre_ruta);
        tv_nombre_ruta.setText(ruta);

        tv_distancia = this.findViewById(R.id.tv_distancia);
        tv_distancia.setText("0 m");

        tv_pasajeros = this.findViewById(R.id.tv_pasajeros);
        btn_subida = this.findViewById(R.id.btn_subida);
        btn_bajada = this.findViewById(R.id.btn_bajada);

        btn_parada_ns = this.findViewById(R.id.btn_parada_ns);
        btn_parada_s = this.findViewById(R.id.btn_parada_s);

        string_paradas = this.findViewById(R.id.string_paradas);
        string_paradas.setText("0");

        btn_incidente = this.findViewById(R.id.btn_incidente);
        btn_incidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incidentes = "Punto de Conflicto";
                Toast.makeText(Map3.this, "incidente registrado", Toast.LENGTH_SHORT).show();

            }
        });

        btn_parada_ns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paradero = "Parada no señalizada";
                nparadas = nparadas + 1;
                string_paradas.setText(Integer.toString(nparadas));
                Toast.makeText(Map3.this, "Paradero marcado", Toast.LENGTH_SHORT).show();
            }
        });
        btn_parada_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paradero = "Parada señalizada";
                nparadas = nparadas + 1;
                string_paradas.setText(Integer.toString(nparadas));
                Toast.makeText(Map3.this, "Paradero marcado", Toast.LENGTH_SHORT).show();
            }
        });

        btn_subida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gpsTraking)
                {
                    nsubidas++;
                    nPasajeros++;
                    tv_pasajeros.setText(Integer.toString(nPasajeros));
                }
                else {
                    Toast.makeText(Map3.this, "Debes iniciar la ruta", Toast.LENGTH_SHORT).show();
                }

            }

        });

        btn_bajada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gpsTraking){
                    if(nPasajeros != 0){
                        nPasajeros--;
                        nbajadas++;
                        tv_pasajeros.setText(Integer.toString(nPasajeros));
                    }
                    else{
                        Toast.makeText(Map3.this, "El número de pasajeros no puede descender más", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Map3.this, "Debes iniciar la ruta", Toast.LENGTH_SHORT).show();
                }
            }
        });


        map_logo = this.findViewById(R.id.map3_logo);
        map_home = this.findViewById(R.id.map3_home);

        finalizar_ruta = this.findViewById(R.id.finalizar_ruta);
        finalizar_ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarRuta();
            }
        });

        if (ciudad.equals("merida")) {
            map_logo.setImageResource(R.drawable.movi_merida);

        } else {
            map_logo.setImageResource(R.drawable.movi_morelia);

        }

        map_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //iniciar la captura de datos gps


        //set all properties of Location
        locationRequest = new LocationRequest();
        //how often does the default location check out ocurr?
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        //how often does the location check ocurr when set to the most frequent update?
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        //locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //Activar localizacion por GPS
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        myFile = new Archivo();
        uploadFilePath = Map3.this.getFilesDir().toString() + "/rutas/";

        //event that is triggered whenever the update interval is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                //save the location
                //updateUIValues(locationResult.getLastLocation());
                //guardar automaticamente las ubicaciones
                //MyApplication myApplication = (MyApplication)getApplicationContext();
                //savedLocations = myApplication.getMyLocations();
                //savedLocations.add(currentLocation);

                //guardar las locations en un txt
                //Cabecera del archivo de texto
                //lat, lon, Personas, Parada, Fecha
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                double lat = locationResult.getLastLocation().getLatitude();
                double lon = locationResult.getLastLocation().getLongitude();
                String coordenada = lat + " ," + lon;
                String contenido = coordenada + ", pasajeros:" + Integer.toString(nPasajeros) + "," + "paradero:" + Paradero + ","+incidentes+"," + currentDateTimeString + "\n";

                //myFile.writeToFile(coordenada);
                Toast.makeText(Map3.this, coordenada, Toast.LENGTH_LONG).show();
                //Toast.makeText(Map3.this, ruta, Toast.LENGTH_LONG).show();
                myFile.writeToFile(uploadFilePath, ruta + ".txt", contenido);
                Paradero = "0";
                incidentes = "";


                if(cont == 0){
                    startLat = lat;
                    startLon = lon;

                }
                if(cont == 10){
                    endLat = lat;
                    endLon = lon;
                    cont = 0;
                    float results[] = new float [10];

                    Location.distanceBetween(startLat, startLon, endLat,endLon,results);
                    if(results[0] > 100){//si la distancia es mayor a 100 metros, significa que se ha habido desplazamiento
                        distancia = distancia + results[0];
                        d = (int) distancia;

                        tv_distancia.setText(Integer.toString(d));
                    }
                }
                cont++;



                //

            }
        };//end of location callback

        //turn on location tracking
        gpsTraking = true;
        startLocationUpdates();
        //Cabecera del archivo de texto
        Toast.makeText(Map3.this, "Ruta iniciada", Toast.LENGTH_SHORT).show();


        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.start(); // start a chronometer


    }






    private void startLocationUpdates() {

        updateGPS();
        //tv_updates.setText("Location is being tracked");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        //fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

    }

    private void stopLocationUpdates() {

        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void updateGPS()
    {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(Map3.this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //user provided the permission
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //we got permission. put teh values of location into the UI components
                    //updateUIValues(location);
                    currentLocation = location;
                }
            });
        }
        else
        {
            //Permission not granted yet
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }

        }
    }


    public void terminarRuta()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Map3.this);
        builder.setMessage("¿Está seguro que desea terminar la Ruta?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things
                //simpleChronometer.get
                simpleChronometer.stop(); // stop a chronometer

                gpsTraking = false;
                stopLocationUpdates();

                //ir a calificar viaje

                distancia_total = Integer.toString(d);
                long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();

                //Toast.makeText(Map3.this, "minutos: "+Long.toString(elapsedMillis/60000), Toast.LENGTH_SHORT).show();


                Intent i = new Intent(Map3.this, Map4.class);
                i.putExtra("ciudad", ciudad);
                i.putExtra("ruta", ruta);
                i.putExtra("IMEI", IMEI);
                i.putExtra("distancia", distancia_total);
                i.putExtra("tiempo", Long.toString(elapsedMillis/60000));

                i.putExtra("tarifa", tarifa);
                i.putExtra("unidad", unidad);
                i.putExtra("descripcion", descripcion);
                i.putExtra("imageUrl", imageUrl);

                startActivity(i);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }// en of terminarRuta

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}