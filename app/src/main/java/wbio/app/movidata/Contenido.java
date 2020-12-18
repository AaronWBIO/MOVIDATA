package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Contenido extends AppCompatActivity {

    String ciudad;
    ImageView contenido_logo,contenido_footer,contenido_home,contenido_title, menu1, menu2,menu3,menu4,menu5,menu6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);

        ciudad = "";
        ciudad = getIntent().getExtras().getString("ciudad");
        contenido_logo = this.findViewById(R.id.contenido_logo);
        contenido_footer = this.findViewById(R.id.contenido_footer);
        contenido_home = this.findViewById(R.id.contenido_home);
        contenido_title = this.findViewById(R.id.contenido_title);

        menu1 = this.findViewById(R.id.menu1);
        menu2 = this.findViewById(R.id.menu2);
        menu3 = this.findViewById(R.id.menu3);
        menu4 = this.findViewById(R.id.menu4);
        menu5 = this.findViewById(R.id.menu5);
        menu6 = this.findViewById(R.id.menu6);

        if(ciudad.equals("merida")){
            contenido_logo.setImageResource(R.drawable.movi_merida);
            contenido_footer.setImageResource(R.drawable.footer_merida);
            contenido_title.setImageResource(R.drawable.contenido_title_merida);

            menu1.setImageResource(R.drawable.cont_me1);
            menu2.setImageResource(R.drawable.cont_me2);
            menu3.setImageResource(R.drawable.cont_me3);
            menu4.setImageResource(R.drawable.cont_me4);
            menu5.setImageResource(R.drawable.rectangle_blue);
            menu6.setImageResource(R.drawable.rectangle_blue);


        }else{
            contenido_logo.setImageResource(R.drawable.movi_morelia);
            contenido_footer.setImageResource(R.drawable.footer_morelia);
            contenido_title.setImageResource(R.drawable.contenido_title_morelia);

            menu1.setImageResource(R.drawable.cont_mo1);
            menu2.setImageResource(R.drawable.cont_mo2);
            menu2.setImageResource(R.drawable.cont_mo3);
            menu4.setImageResource(R.drawable.cont_mo4);
            menu5.setImageResource(R.drawable.cont_mo5);
            menu6.setImageResource(R.drawable.cont_mo6);

        }

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciudad.equals("merida")){
                    String url = "http://isla.merida.gob.mx/serviciosinternet/ordenamientoterritorial/docs/PIMUS_2040.pdf";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
                else{
                    String url = "https://semovep.morelia.gob.mx/projects/san_jose/index.php";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciudad.equals("merida")){
                    String url = "https://drive.google.com/file/d/1F3tAZfUQRDhCX2yUymGmuPKmChlQrGc3/view";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
                else{

                    String url = "https://semovep.morelia.gob.mx/projects/vasco_quiroga/index.php";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }

            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciudad.equals("merida")){
                    String url = "https://drive.google.com/file/d/109sb_ggYDj_DqA3F7_czyMJTdGuSZ83K/view";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
                else{
                    String url = "https://semovep.morelia.gob.mx/projects/vicente_santamaria/index.php";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciudad.equals("merida")){
                    String url = "https://indd.adobe.com/view/72b011e0-26fe-4014-ba73-f74000f32f27";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
                else{
                    String url = "https://semovep.morelia.gob.mx/projects/guillermo_prieto/index.php";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);

                }
            }
        });

        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciudad.equals("merida")){

                }
                else{
                    String url = "https://semovep.morelia.gob.mx/projects/madero_poniente/index.php";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            }
        });
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciudad.equals("merida")){

                }
                else{
                    String url = "https://semovep.morelia.gob.mx/projects/anastacio_najera/index.php";
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);

                }
            }
        });



        contenido_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Contenido.this, Inicio.class);
                startActivity(i);
            }
        });

    }
}