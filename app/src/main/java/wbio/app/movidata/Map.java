package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Map extends AppCompatActivity {

    String ciudad;
    ImageView map_logo,map_footer,map_home,iniciar_captura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ciudad = "merida";
        ciudad = getIntent().getExtras().getString("ciudad");

        map_logo = this.findViewById(R.id.map_logo);
        map_footer = this.findViewById(R.id.map_footer);
        map_home = this.findViewById(R.id.map_home);
        iniciar_captura = this.findViewById(R.id.iniciar_captura);

        if(ciudad.equals("merida")){
            map_logo.setImageResource(R.drawable.movi_merida);
            map_footer.setImageResource(R.drawable.footer_merida);
        }else{
            map_logo.setImageResource(R.drawable.movi_morelia);
            map_footer.setImageResource(R.drawable.footer_morelia);
        }

        map_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Map.this, Inicio.class);
                startActivity(i);
            }
        });
        iniciar_captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Map.this, Map2.class);
                i.putExtra("ciudad", ciudad);
                startActivity(i);
            }
        });

    }
}