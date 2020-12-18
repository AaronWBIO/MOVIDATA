package wbio.app.movidata;

import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.provider.Settings.Secure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Random;



public class Map2 extends AppCompatActivity {

    ProgressDialog mydialog;

    String ciudad, unidad, ruta, tarifa, descripcion,imageUrl;
    ImageView map_logo,map_footer,map_home, vagoneta, micro, bus, iniciar_captura,camara;
    EditText edit_ruta,edit_tarifa,edit_descripcion;

    String IMEINumber;

    int selectedImg;


    ServerUrl url;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA },
                    1000);
        }//*/
        imageUrl="www";
        tarifa = "";
        descripcion = "";
        unidad = "";
        ruta = "";
        ciudad = "merida";
        IMEINumber="";
        ciudad = getIntent().getExtras().getString("ciudad");

        url = new ServerUrl();

        map_logo = this.findViewById(R.id.map2_logo);
        map_footer = this.findViewById(R.id.map2_footer);
        map_home = this.findViewById(R.id.map2_home);

        vagoneta = this.findViewById(R.id.vagoneta);
        micro = this.findViewById(R.id.micro);
        bus = this.findViewById(R.id.bus);

        edit_ruta = this.findViewById(R.id.edit_ruta);
        edit_descripcion = this.findViewById(R.id.edit_descripcion);
        edit_tarifa = this.findViewById(R.id.edit_tarifa);

        camara = this.findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent2();

            }
        });

        iniciar_captura = this.findViewById(R.id.iniciar_captura2);
        iniciar_captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tarifa = "";
                String descripcion = "";

                tarifa = edit_tarifa.getText().toString();
                descripcion = edit_descripcion.getText().toString();
                ruta = edit_ruta.getText().toString();

                if(!unidad.isEmpty() && !tarifa.isEmpty() && !ruta.isEmpty()){



                    IMEINumber = Secure.getString(Map2.this.getContentResolver(),Secure.ANDROID_ID);;

                    //Toast.makeText(Map2.this, IMEINumber, Toast.LENGTH_SHORT).show();

                   if(!IMEINumber.isEmpty())
                    {


                        //makePost(url.getUrl());
                        //subirImagen();

                        tarifa = edit_tarifa.getText().toString();
                        descripcion = edit_descripcion.getText().toString();


                        Intent i = new Intent(Map2.this, Map3.class);
                        i.putExtra("ciudad", ciudad);
                        i.putExtra("ruta", ruta);
                        i.putExtra("IMEI", IMEINumber);
                        i.putExtra("tarifa", tarifa);
                        i.putExtra("unidad", unidad);
                        i.putExtra("descripcion", descripcion);
                        i.putExtra("imageUrl", imageUrl);

                        startActivity(i);
                    }


                }
                else{
                    Toast.makeText(Map2.this, "Debe llenar los campos obligatorios", Toast.LENGTH_SHORT).show();

                }
            }
        });

        vagoneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vagoneta.setImageResource(R.drawable.vagoneta);
                micro.setImageResource(R.drawable.micro_);
                bus.setImageResource(R.drawable.bus_);
                unidad = "Vagoneta";
            }
        });
        micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vagoneta.setImageResource(R.drawable.vagoneta_);
                micro.setImageResource(R.drawable.micro);
                bus.setImageResource(R.drawable.bus_);
                 unidad = "Microbus";
            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vagoneta.setImageResource(R.drawable.vagoneta_);
                micro.setImageResource(R.drawable.micro_);
                bus.setImageResource(R.drawable.bus);
                unidad = "Autobus";
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
                Intent i = new Intent(Map2.this, Inicio.class);
                i.putExtra("ciudad", ciudad);
                startActivity(i);
            }
        });
    }

//-------------------------------
//-------------------------------
//-------------------------------
    String currentPhotoPath;
    private File createImageFile() throws IOException {
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //String con el formato de la fecha
        //String imageFileName = "Backup_" + timeStamp + "_"; //Nombre de la imagen
        String imageFileName = "MiImagenPrueba"; //Nombre de la imagen

        //getExternalFilesDir(); //crea una copia en el directorio de la aplicaion android/data/com.example.NAMEAPP/
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); //Direcctorio donde se almacenara la imagen
        File image = File.createTempFile( imageFileName,".jpg", storageDir );//Crea la imagen con nombre, extencion y ruta
        currentPhotoPath = image.getAbsolutePath();//Guardamos en una variable global la ubicacion del archivo (Save a file: path for use with ACTION_VIEW intents)
        return image;
    }
    /* Funcion que usa la camara, sin guardar en memoria la imagen
     * */

    /* Funcion que usa la camara y guarda la imagen en la memoria del telefono
     * Se necesita leer la documentacion para saber que hacen ciertas funciones*/
    private static int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent2() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Lanzamos la aplicacion de camara, existente es un objeto
        if ( takePictureIntent.resolveActivity(getPackageManager()) != null){ // Ensure that there's a camera activity to handle the intent
            File photoFile = null;  // Create the File where the photo should go
            try {
                photoFile = createImageFile(); //Se crea el archivo, se recupera el nombre y la ruta donde se guarda la imagen.
                //Toast.makeText(this, "Archivo Creado en " + photoFile.toString(), Toast.LENGTH_LONG ).show(); //Imprimomos un mensaje que indica donde se creo la imagen
            } catch (IOException ex) { // Error occurred while creating the File
                ex.printStackTrace(); Toast.makeText(this, "Error capturando foto", Toast.LENGTH_SHORT).show(); //Imprime que hubo error al crear el archivo para guardar la imagen
            }
            // Continue only if the File was successfully created
            if (photoFile != null) { //Verificacion de que el directorio y el archivo se crearon con exito
                Uri photoURI = FileProvider.getUriForFile(Map2.this, "wbio.app.movidata.provider", photoFile);  //com.example.testapi
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);//Donde almacenara la imagen recien tomada.
                //Toast.makeText(this, "Camara lanzada la URI es :"+photoURI.toString(), Toast.LENGTH_LONG).show(); //mensaje de
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);// Lanza la actividad de la camara y espera a que el resultado termine.
            }
        }
    }
//----------------------------------------------------------------
    Uri contentUri;
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
//----------------------------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            camara.setImageURI(Uri.parse(currentPhotoPath));
            Log.d("currentPhotoPath ",currentPhotoPath );

            BitmapDrawable drawable = (BitmapDrawable) camara.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            Uri _uri = getImageUri(this,bitmap );

            String sourceFileUri = _uri.toString();

            int version = getAndroidVersion();
            if(version <= 28) //telefonos con android 9
            {
                //Toast.makeText(this, "Version1: "+Integer.toString(version), Toast.LENGTH_LONG ).show();
                try {
                    sourceFileUri = PathUtil.getPath(Map2.this, _uri);
                }catch (Exception e){

                }


            }
            if(version>28 ){//android 10
                sourceFileUri = currentPhotoPath;
               // Toast.makeText(this, "Version2: "+Integer.toString(version), Toast.LENGTH_LONG ).show();

            }

            //Log.d("Listo para enviar ","hola" );
            final String imageFilePath = sourceFileUri;
            mydialog = ProgressDialog.show(Map2.this, "", "Enviando Imagen...", true);

            Log.d("imageFilePathr ",imageFilePath );
            Log.w("","image url : "+imageFilePath);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Looper.prepare();
                    UploadFile upload = new UploadFile("movidata"+getNumeroRandom(1, 10000), Map2.this);
                    upload.uploadFile(imageFilePath);

                    Looper.loop();

                }

            }).start();
        }
    }

    public int getAndroidVersion(){
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

            /*Log.e("MyActivity", "manufacturer " + manufacturer
                    + " \n model " + model
                    + " \n version " + version
                    + " \n versionRelease " + versionRelease
            );*/
        return version;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private int getNumeroRandom(int Low, int High) {
        Random r = new Random();
        int Result = r.nextInt(High-Low) + Low;
        return Result;
    }


    public class UploadFile {
        private String upLoadServerUri = null;
        String t_name;
        Context context;



        public UploadFile(String filepath, Context context) {
            t_name = filepath;
            this.context = context;

            Toast.makeText(context, "Enviando imagen..."+t_name, Toast.LENGTH_LONG).show();

        }

        public int uploadFile(String sourceFileUri) {

            //Log.d("File Uploaded For ",sourceFileUri );

            String fileName = sourceFileUri;
            int serverResponseCode = 200;
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (!sourceFile.isFile()) {
                Toast.makeText(context, "El archivo no existe", Toast.LENGTH_LONG).show();

                return 0;
            } else {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    upLoadServerUri = ServerUrl.getUploadImage();

                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename="+"'"
                            + t_name + ".jpg'" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
                    Toast.makeText(context, "La imagen se subió correctamente", Toast.LENGTH_LONG).show();
                    imageUrl = ServerUrl.getImage()+t_name+".jpg";
                    mydialog.dismiss();

                    // close the streams
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {

                    //Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                    Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_LONG).show();
                    mydialog.dismiss();
                } catch (Exception e) {
                    mydialog.dismiss();
                } // End else block
            }
            return serverResponseCode;
        }



    }
}