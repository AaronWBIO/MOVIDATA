package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Map4 extends AppCompatActivity {

    private String unidad, ruta, IMEI,ciudad, distancia, tiempo, tarifa,descripcion,imageUrl;
    ImageView pesimo,malo,normal,bueno,excelente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map4);

        unidad = "";
        ruta = "";
        IMEI = "";
        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");
        ruta = getIntent().getExtras().getString("ruta");
        IMEI = getIntent().getExtras().getString("IMEI");
        distancia = getIntent().getExtras().getString("distancia");
        tiempo = getIntent().getExtras().getString("tiempo");

        unidad = getIntent().getExtras().getString("unidad");
        tarifa = getIntent().getExtras().getString("tarifa");
        descripcion = getIntent().getExtras().getString("descripcion");
        imageUrl = getIntent().getExtras().getString("imageUrl");





        pesimo = this.findViewById(R.id.pesimo);
        malo = this.findViewById(R.id.malo);
        normal = this.findViewById(R.id.normal);
        bueno = this.findViewById(R.id.bueno);
        excelente = this.findViewById(R.id.excelente);

        pesimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarCalificacion("pesimo");
            }
        });
        malo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarCalificacion("Malo");
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarCalificacion("Normal");
            }
        });
        bueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarCalificacion("Bueno");
            }
        });
        excelente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminarCalificacion("Excelente");
            }
        });




    }//end onf create

    public void terminarCalificacion(final String calificacion)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Map4.this);
        builder.setMessage("Ha asignado una calificacion de "+calificacion);

        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things


                Intent i = new Intent(Map4.this, Retro.class);
                i.putExtra("ciudad", ciudad);
                i.putExtra("ruta", ruta);
                i.putExtra("IMEI", IMEI);
                i.putExtra("calificacion", calificacion);
                i.putExtra("distancia", distancia);
                i.putExtra("tiempo", tiempo);

                i.putExtra("tarifa", tarifa);
                i.putExtra("unidad", unidad);
                i.putExtra("descripcion", descripcion);
                i.putExtra("imageUrl", imageUrl);

                startActivity(i);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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