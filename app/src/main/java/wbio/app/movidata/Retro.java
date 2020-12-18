package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Retro extends AppCompatActivity {

    ImageView calificacion_icon, servicio, conduccion, ambiente, seguridad, confort, accesibilidad,enviar_retro;
    TextView calificacion_text,dejar_comentario;
    int opciones, btn1, btn2, btn3, btn4, btn5, btn6;
    String unidad,IMEI,ciudad,ruta,calificacion, distancia, tiempo,tarifa,imageUrl,descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);

        unidad = "";
        ruta = "";
        IMEI = "";
        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");
        ruta = getIntent().getExtras().getString("ruta");
        IMEI = getIntent().getExtras().getString("IMEI");
        calificacion = getIntent().getExtras().getString("calificacion");
        distancia = getIntent().getExtras().getString("distancia");
        tiempo = getIntent().getExtras().getString("tiempo");

        unidad = getIntent().getExtras().getString("unidad");
        tarifa = getIntent().getExtras().getString("tarifa");
        descripcion = getIntent().getExtras().getString("descripcion");
        imageUrl = getIntent().getExtras().getString("imageUrl");

        opciones = 0;
        btn1 = 0;
        btn2 = 0;
        btn3 = 0;
        btn4 = 0;
        btn5 = 0;
        btn6 = 0;

        calificacion_text = (TextView) this.findViewById(R.id.calificacion_text);
        dejar_comentario = this.findViewById(R.id.dejar_comentario);
        dejar_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int suma = btn1 + btn2 + btn3 + btn4 + btn5 + btn6;
                if(suma > 3){
                    Toast.makeText(Retro.this, "Debe seleccionar hasta 3 opciones", Toast.LENGTH_LONG).show();
                }
                else{
                    String retroalimentacion = "";
                    if(btn1 == 1){
                        retroalimentacion =  retroalimentacion + "Servicio" + ",";
                    }
                    if(btn2 == 1){
                        retroalimentacion =  retroalimentacion + "Confort" + ",";
                    }
                    if(btn3 == 1){
                        retroalimentacion =  retroalimentacion + "Seguridad" + ",";
                    }
                    if(btn4 == 1){
                        retroalimentacion =  retroalimentacion + "Conducción" + ",";
                    }if(btn5 == 1){
                        retroalimentacion =  retroalimentacion + "Ambiente" + ",";
                    }if(btn6 == 1){
                        retroalimentacion =  retroalimentacion + "Accesibilidad" + ",";
                    }

                    Intent i = new Intent(Retro.this, Comentario.class);
                    i.putExtra("ciudad", ciudad);
                    i.putExtra("ruta", ruta);
                    i.putExtra("IMEI", IMEI);
                    i.putExtra("calificacion", calificacion);
                    i.putExtra("retroalimentacion", retroalimentacion);
                    i.putExtra("distancia", distancia);
                    i.putExtra("tiempo", tiempo);

                    i.putExtra("tarifa", tarifa);
                    i.putExtra("unidad", unidad);
                    i.putExtra("descripcion", descripcion);
                    i.putExtra("imageUrl", imageUrl);

                    startActivity(i);
                }
            }
        });

        calificacion_icon = this.findViewById(R.id.calificacion_icon);

        servicio = this.findViewById(R.id.servicio);
        conduccion = this.findViewById(R.id.conduccion);
        ambiente = this.findViewById(R.id.ambiente);
        seguridad = this.findViewById(R.id.seguridad);
        confort = this.findViewById(R.id.confort);
        accesibilidad = this.findViewById(R.id.accesibilidad);

        enviar_retro = this.findViewById(R.id.enviar_retro);
        enviar_retro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int suma = btn1 + btn2 + btn3 + btn4 + btn5 + btn6;
                if(suma > 3){
                    Toast.makeText(Retro.this, "Debe seleccionar hasta 3 opciones", Toast.LENGTH_LONG).show();
                }
                else{
                    String retroalimentacion = "";
                    if(btn1 == 1){
                        retroalimentacion =  retroalimentacion + "Servicio" + ",";
                    }
                    if(btn2 == 1){
                        retroalimentacion =  retroalimentacion + "Confort" + ",";
                    }
                    if(btn3 == 1){
                        retroalimentacion =  retroalimentacion + "Seguridad" + ",";
                    }
                    if(btn4 == 1){
                        retroalimentacion =  retroalimentacion + "Conducción" + ",";
                    }if(btn5 == 1){
                        retroalimentacion =  retroalimentacion + "Ambiente" + ",";
                    }if(btn6 == 1){
                        retroalimentacion =  retroalimentacion + "Accesibilidad" + ",";
                    }

                    Intent i = new Intent(Retro.this, Final.class);
                    i.putExtra("ciudad", ciudad);
                    i.putExtra("ruta", ruta);
                    i.putExtra("IMEI", IMEI);
                    i.putExtra("calificacion", calificacion);
                    i.putExtra("retroalimentacion", retroalimentacion);
                    i.putExtra("comentario", "");
                    i.putExtra("distancia", distancia);
                    i.putExtra("tiempo", tiempo);

                    i.putExtra("tarifa", tarifa);
                    i.putExtra("unidad", unidad);
                    i.putExtra("descripcion", descripcion);
                    i.putExtra("imageUrl", imageUrl);

                    startActivity(i);
                }
            }
        });


        if(calificacion.equals("Pesimo")){
            calificacion_text.setText("¿QUÉ SALIÓ MAL?");
            calificacion_icon.setImageResource(R.drawable.pesimo);
        }
        if(calificacion.equals("Malo")){
            calificacion_text.setText("¿QUÉ SALIÓ MAL?");
            calificacion_icon.setImageResource(R.drawable.malo);
        }
        if(calificacion.equals("Normal")){
            calificacion_text.setText("¿QUÉ PUEDE MEJORAR?");
            calificacion_icon.setImageResource(R.drawable.normal);
        }
        if(calificacion.equals("Bueno")){
            calificacion_text.setText("¿QUÉ FUE LO QUE MÁS TE GUSTÓ?");
            calificacion_icon.setImageResource(R.drawable.bueno);
        }
        if(calificacion.equals("Excelente")){
            calificacion_text.setText("¿QUÉ FUE LO QUE MÁS TE GUSTÓ?");
            calificacion_icon.setImageResource(R.drawable.excelente);
        }

        servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn1 == 0)
                {
                    servicio.setImageResource(R.drawable.servicio);
                    btn1 = 1;
                }else{
                    servicio.setImageResource(R.drawable.servicio_);
                    btn1 = 0;
                }

            }
        });
        confort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn2 == 0)
                {
                    confort.setImageResource(R.drawable.confort);
                    btn2 = 1;
                }else{
                    confort.setImageResource(R.drawable.confort_);
                    btn2 = 0;
                }

            }
        });
        seguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn3 == 0)
                {
                    seguridad.setImageResource(R.drawable.seguridad);
                    btn3 = 1;
                }else{
                    seguridad.setImageResource(R.drawable.seguridad_);
                    btn3 = 0;
                }

            }
        });
        conduccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn4 == 0)
                {
                    conduccion.setImageResource(R.drawable.conduccion);
                    btn4 = 1;
                }else{
                    conduccion.setImageResource(R.drawable.conduccion_);
                    btn4 = 0;
                }

            }
        });
        ambiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn5 == 0)
                {
                    ambiente.setImageResource(R.drawable.ambiente);
                    btn5 = 1;
                }else{
                    ambiente.setImageResource(R.drawable.ambiente_);
                    btn5 = 0;
                }

            }
        });
        accesibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn6 == 0)
                {
                    accesibilidad.setImageResource(R.drawable.accesibilidad);
                    btn6 = 1;
                }else{
                    accesibilidad.setImageResource(R.drawable.accesibilidad_);
                    btn6 = 0;
                }

            }
        });




    }
}