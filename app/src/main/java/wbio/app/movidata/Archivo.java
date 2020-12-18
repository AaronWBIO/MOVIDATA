package wbio.app.movidata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Archivo {

    public boolean writeToFile(String path, String nombre, String content) {
        //File path1 = new File(path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, nombre);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(content);
            writer.flush();
            writer.close();
            //output.setText(readFile());
            //Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();

            //Toast.makeText(MainActivity.this, readFile(), Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) { }

        return false;
    }

    public String readFile(String path, String name) {

        File fileEvents = new File(path+name);

        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        return result;
    }


    public ArrayList<String> GetLatLong(String path, String name) {

        ArrayList<String> latlong = new ArrayList<String>();

        File fileEvents = new File(path+name);

        //StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                //formato de la linea:
                //20.9328156 ,-89.5712661, pasajeros:0,paradero:1,13 ago. 2020 2:37:31 p. m.
                try {
                    String[] separado = line.split(",");
                    latlong.add(separado[0] + "," + separado[1]);
                }
                catch (Exception e){
                    latlong.add("0,0");
                    break;
                }
                //text.append(line);
                //text.append('\n');
            }
            br.close();
        } catch (IOException e) { }
        //String result = text.toString();
        return latlong;
    }


}