package wbio.app.movidata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Recorridos extends AppCompatActivity {


    Archivo myFile;
    int serverResponseCode = 0;
    ProgressDialog mydialog;
    String upLoadServerUri = null;
    //ListView lv_mylist;
    /**********  File Path *************/
    String uploadFilePath;
    String uploadFileName;
    ArrayList<String> items;

    LinearLayout layoutList;


    String ciudad, ruta;
    ImageView map_logo,map_footer,map_home,btn_visualizar_ruta,btn_enviar;
    EditText input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos);

        ciudad = "";
        ciudad = getIntent().getExtras().getString("ciudad");
        map_logo = this.findViewById(R.id.recorridos_logo);
        map_footer = this.findViewById(R.id.recorridos_footer);
        map_home = this.findViewById(R.id.recorridos_home);


        ruta = "";

        input_email = this.findViewById(R.id.input_email);

        btn_enviar = this.findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validarEmail(input_email.getText().toString())){
                    input_email.setError("Email no válido");

                }else{
                    if(!ruta.isEmpty()){
                        input_email.setError(null);
                        sendEmail();
                    }else{
                        Toast.makeText(Recorridos.this, "Debe seleccionar una ruta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_visualizar_ruta = this.findViewById(R.id.btn_visualizar_ruta);
        btn_visualizar_ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ruta.isEmpty()){
                    Intent i = new Intent(Recorridos.this, MapsActivity.class);
                    i.putExtra("ciudad", ciudad);
                    i.putExtra("ruta", ruta);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Recorridos.this, "Debe seleccionar una ruta para visualizar", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                Intent i = new Intent(Recorridos.this, Inicio.class);
                startActivity(i);
            }
        });

        mydialog = null;
        myFile = new Archivo();
        uploadFilePath = Recorridos.this.getFilesDir().toString()+"/rutas/";
        items = new ArrayList<String>();


        /************* Php script path ****************/
        upLoadServerUri = " https://dd99c1fadb7f.ngrok.io/Mappaton/upload.php";
        uploadFileName = "";
        ListFiles(uploadFilePath);


        //---------------------------------------------------------------
        layoutList =  this.findViewById(R.id.layout_list);
        if(items.size()>0) {
            for (int i = 0; i < items.size(); i++) {

                final View nuevaView = getLayoutInflater().inflate(R.layout.row_add, null, false);
                final Button button = nuevaView.findViewById(R.id.btn_row_remove);

                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    limpiarBotones();

                    button.setBackgroundResource(R.drawable.rounded_button);
                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                    ruta = button.getText().toString(); //guarda el nombre de la ruta seleccionada
                    //button.setText(items.get(i));
                }
            });

                layoutList.addView(nuevaView);
            }
        }

        for(int i=0; i<layoutList.getChildCount();i++){
            View crickerView = layoutList.getChildAt(i);
            final Button button = crickerView.findViewById(R.id.btn_row_remove);

            button.setText(items.get(i));

        }

//---------------------------------------------------------------



    }//en od create

    /*private void sendEmail(ArrayList<String> datosIncentivos) {
        String message = "";
        String subject = "Datos del recorrido MOVIDATA";
        String email = datosIncentivos.get(3);

        for(int i=0;i<datosIncentivos.size();i++){
            message = message + datosIncentivos.get(i) +"\n";
        }

        JavaMailAPI javaMailAPI = new JavaMailAPI(Recorridos.this, email, subject, message);
        javaMailAPI.execute();
    }*/
    private void sendEmail() {

        String message = myFile.readFile(Recorridos.this.getFilesDir().toString()+ "/rutas/", ruta);
        String retroalimentacion = myFile.readFile(Recorridos.this.getFilesDir().toString()+ "/retroalimentacion/", ruta);
        message = message+"\n"+retroalimentacion;
        //Toast.makeText(Recorridos.this, message, Toast.LENGTH_SHORT).show();
        String subject = "Datos mapeados de la ruta "+ruta;
        String email = input_email.getText().toString();

        JavaMailAPI javaMailAPI = new JavaMailAPI(Recorridos.this, email, subject, message);

        //Bitmap bitmap = ((BitmapDrawable)imageView_arbol1.getDrawable()).getBitmap();
        //javaMailAPI.addImage(bitmap);
        javaMailAPI.execute();
    }




    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void limpiarBotones(){
        for(int i=0; i<layoutList.getChildCount();i++){
            View crickerView = layoutList.getChildAt(i);
            final Button button = crickerView.findViewById(R.id.btn_row_remove);

            button.setBackgroundResource(R.drawable.button_round_border);
            button.setTextColor(getResources().getColor(R.color.colorWhite));

        }
    }


    public void ConfirmarEnvio(String mensaje)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Recorridos.this);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things


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


    public void ListFiles(String path)
    {

        File directory = new File(path);
        if(directory.exists()){
            File[] fList = directory.listFiles();
            for (File file : fList) {
                items.add(file.getName());

            }

            //ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
            //lv_mylist.setAdapter(adaptador);

        }
        else {//directorio no existe
            Toast.makeText(Recorridos.this, "No hay rutas guardadas", Toast.LENGTH_SHORT).show();
        }


    }//End of ListFiles



}