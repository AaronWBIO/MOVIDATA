package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    String ciudad;
    ImageView movi_logo, footer_home,cambiar_ciudad, mapear_icon, recorridos_icon, contenido_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");

        movi_logo = this.findViewById(R.id.movi_logo);
        footer_home = this.findViewById(R.id.footer_home);
        cambiar_ciudad = this.findViewById(R.id.cambiar_ciudad);
        mapear_icon = this.findViewById(R.id.mapear_icon);
        recorridos_icon = this.findViewById(R.id.recorridos_icon);
        contenido_icon = this.findViewById(R.id.contenido_icon);

        if(ciudad.equals("merida")){
            movi_logo.setImageResource(R.drawable.movi_merida);
            footer_home.setImageResource(R.drawable.footer_merida);
        }else{
            movi_logo.setImageResource(R.drawable.movi_morelia);
            footer_home.setImageResource(R.drawable.footer_morelia);
        }

        cambiar_ciudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Inicio.class);
                startActivity(i);
            }
        });

        mapear_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Map.class);
                i.putExtra("ciudad", ciudad);
                startActivity(i);

            }
        });
        recorridos_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Recorridos.class);
                i.putExtra("ciudad", ciudad);
                startActivity(i);

            }
        });
        contenido_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Contenido.class);
                i.putExtra("ciudad", ciudad);
                startActivity(i);
            }
        });





    }
}