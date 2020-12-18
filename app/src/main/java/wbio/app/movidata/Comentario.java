package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Comentario extends AppCompatActivity {

    ImageView comentario_sig;
    EditText edit_comentario;

    String unidad, ruta, IMEI,ciudad,calificacion, retroalimentacion,comentario, distancia, tiempo,tarifa,descripcion,imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        unidad = "";
        ruta = "";
        IMEI = "";
        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");
        ruta = getIntent().getExtras().getString("ruta");
        IMEI = getIntent().getExtras().getString("IMEI");
        calificacion = getIntent().getExtras().getString("calificacion");
        retroalimentacion = getIntent().getExtras().getString("retroalimentacion");
        distancia = getIntent().getExtras().getString("distancia");
        tiempo = getIntent().getExtras().getString("tiempo");

        unidad = getIntent().getExtras().getString("unidad");
        tarifa = getIntent().getExtras().getString("tarifa");
        descripcion = getIntent().getExtras().getString("descripcion");
        imageUrl = getIntent().getExtras().getString("imageUrl");


        edit_comentario = this.findViewById(R.id.edit_comentario);

        comentario_sig = this.findViewById(R.id.comentario_sig);
        comentario_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comentario = edit_comentario.getText().toString();

                Intent i = new Intent(Comentario.this, Final.class);
                i.putExtra("ciudad", ciudad);
                i.putExtra("ruta", ruta);
                i.putExtra("IMEI", IMEI);
                i.putExtra("calificacion", calificacion);
                i.putExtra("retroalimentacion", retroalimentacion);
                i.putExtra("comentario", comentario);
                i.putExtra("distancia", distancia);
                i.putExtra("tiempo", tiempo);

                i.putExtra("tarifa", tarifa);
                i.putExtra("unidad", unidad);
                i.putExtra("descripcion", descripcion);
                i.putExtra("imageUrl", imageUrl);

                startActivity(i);
            }
        });

    }
}