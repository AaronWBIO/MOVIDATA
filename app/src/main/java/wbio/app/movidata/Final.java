package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Final extends AppCompatActivity {

    ImageView final_logo, final_footer,btn_final;
    String ciudad, IMEI,unidad,ruta,calificacion,comentario,retroalimentacion, tiempo, distancia,tarifa,descripcion,imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        final_logo = this.findViewById(R.id.final_logo);
        final_footer = this.findViewById(R.id.final_footer);

        unidad = "";
        ruta = "";
        IMEI = "";
        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");
        ruta = getIntent().getExtras().getString("ruta");
        IMEI = getIntent().getExtras().getString("IMEI");
        calificacion = getIntent().getExtras().getString("calificacion");
        retroalimentacion = getIntent().getExtras().getString("retroalimentacion");
        comentario = getIntent().getExtras().getString("comentario");
        distancia = getIntent().getExtras().getString("distancia");
        tiempo = getIntent().getExtras().getString("tiempo");

        unidad = getIntent().getExtras().getString("unidad");
        tarifa = getIntent().getExtras().getString("tarifa");
        descripcion = getIntent().getExtras().getString("descripcion");
        imageUrl = getIntent().getExtras().getString("imageUrl");


        //Toast.makeText(Final.this, "retro: "+retroalimentacion, Toast.LENGTH_SHORT).show();


        if(ciudad.equals("merida")){
            final_logo.setImageResource(R.drawable.movi_merida);
            final_footer.setImageResource(R.drawable.footer_merida);
        }else{
            final_logo.setImageResource(R.drawable.movi_morelia);
            final_footer.setImageResource(R.drawable.footer_morelia);
        }

        btn_final = this.findViewById(R.id.btn_final);
        btn_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Final.this, Home.class);
                i.putExtra("ciudad", ciudad);
                startActivity(i);
            }
        });

        String contenido = "Unidad: " +unidad+"\n"+
                           "Tarifa: "+tarifa + "\n"+
                           "Imagen: "+imageUrl+"\n"+
                           "Calificación: "+calificacion+"\n" +
                           "Retroalimentación: "+retroalimentacion+"\n"+
                           "Comentarios: "+comentario+"\n"+
                           "Tiempo de recorrido (min): "+tiempo+"\n"+
                           "Distancia de recorrido (m): "+distancia+"\n";

        String uploadFilePath = Final.this.getFilesDir().toString() + "/retroalimentacion/";
        Archivo myFile = new Archivo();
        myFile.writeToFile(uploadFilePath, ruta + ".txt", contenido);


    }
}