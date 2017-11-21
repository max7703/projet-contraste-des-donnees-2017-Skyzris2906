package informatique.cgmatane.qc.cq.databasestats.donnees;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import informatique.cgmatane.qc.cq.databasestats.modele.ModeleDate;
import informatique.cgmatane.qc.cq.databasestats.modele.Temperature;

/**
 * Created by 1743002 on 2017-11-16.
 */

public class TemperaturesDAO {

    private List<Temperature> listeTemperatures;
    private List<HashMap<String,String>> listeTemperaturesEnHashMap;
    private List<Temperature> listeTriee;
    private Context context;
    private static final String URL_CONNECTION = "http://192.168.1.146/service_temp/temperature/liste/index.php";

    public TemperaturesDAO(Context context){

        this.context = context;

        listeTemperatures = new ArrayList<>();
        listeTemperaturesEnHashMap = new ArrayList<>();
        listeTriee = new ArrayList<>();
    }

    public List<Temperature> listerLesTemperatures(){

        listeTemperatures.clear();

        try{

            URL url = new URL(URL_CONNECTION);
            HttpURLConnection service = (HttpURLConnection) url.openConnection();
            InputStream flux = service.getInputStream();

            Scanner lecteur = new Scanner(flux).useDelimiter("\\A");
            String xml = lecteur.hasNext() ? lecteur.next() : "";

            if (xml != null) {

                DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = parseur.parse(new StringBufferInputStream(xml));

                NodeList nodeListe = document.getElementsByTagName("Temperature");

                for (int i =0; i<nodeListe.getLength(); i++) {

                    Element elementTemperature = (Element) nodeListe.item(i);

                    int id = Integer.parseInt(elementTemperature.getElementsByTagName("id").item(0).getTextContent());
                    double degre = Double.parseDouble(elementTemperature.getElementsByTagName("temperature").item(0).getTextContent());
                    String date = elementTemperature.getElementsByTagName("date").item(0).getTextContent();
                    String heure = elementTemperature.getElementsByTagName("heure").item(0).getTextContent();

                    Calendar calendrier = ModeleDate.getDate(date,heure);

                    Temperature temperature = new Temperature(id,degre,calendrier);

                    listeTemperatures.add(temperature);
                }

            }
        }catch (Exception ex){
            Log.d("APPERROR", ex.getMessage());
        }

        return  listeTemperatures;
    }

    public List<HashMap<String,String>> listerLesTemperaturesEnHashMap(){

        listeTemperaturesEnHashMap.clear();

        for(Temperature temperature : this.listeTriee){
            listeTemperaturesEnHashMap.add(temperature.exporteEnHashMap());
        }

        return listeTemperaturesEnHashMap;
    }
}