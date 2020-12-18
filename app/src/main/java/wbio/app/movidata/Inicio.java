package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    ImageView img_merida, img_morelia,img_btn_sig;
    String ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ciudad = "";

        img_merida = this.findViewById(R.id.img_merida);
        img_merida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_merida.setImageResource(R.drawable.merida);
                img_morelia.setImageResource(R.drawable.morelia_);
                ciudad = "merida";
            }
        });

        img_morelia = this.findViewById(R.id.img_morelia);
        img_morelia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_merida.setImageResource(R.drawable.merida_);
                img_morelia.setImageResource(R.drawable.morelia);
                ciudad = "morelia";
            }
        });

        img_btn_sig = this.findViewById(R.id.img_btn_sig);
        img_btn_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ciudad.isEmpty()){
                    Toast.makeText(Inicio.this, "Debe seleccionar una ciudad", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ciudad.equals("morelia"))
                    {//enviar a morelia
                        Intent i = new Intent(Inicio.this, Home.class);
                        i.putExtra("ciudad", ciudad);
                        startActivity(i);

                    }
                    if(ciudad.equals("merida"))
                    {//enviar a merida
                        Intent i = new Intent(Inicio.this, Home.class);
                        i.putExtra("ciudad", ciudad);
                        startActivity(i);
                    }
                }
            }
        });


    }
}