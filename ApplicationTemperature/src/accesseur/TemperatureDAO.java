package accesseur;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modele.Accelerometre;
import modele.Temperature;

public class TemperatureDAO 
{	
	private Document parserXML(String xml)
	{
		Document doc = null;
		DocumentBuilder parseur;
		try {
			parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = parseur.parse(new StringBufferInputStream(xml));		
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
	
	private String consommerService(String url)
	{
		String xml = null;
			try
			{
				// récupere le XML
				URL urlServiceTemperature = new URL (url);
				URLConnection serviceTemperature = urlServiceTemperature.openConnection();
				InputStream fluxTemperature = serviceTemperature.getInputStream();
				
				Scanner lecteur = new Scanner(fluxTemperature).useDelimiter("\\A");
				xml = lecteur.hasNext() ? lecteur.next() : "";
				System.out.println(xml);
				return xml;
			}
			catch (MalformedURLException e){
				e.printStackTrace();
			}

			catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public List<Temperature> listerTemperature()
 	{
 		// Récupérer le xml
		String xml = consommerService("http://10101011100/service_temp/temperature/liste/index.php");		
		
 		// Interprétation du xml - construire les modeles
		if(xml != null)
 		{
 				Document document = parserXML(xml);
 				if (document == null) return null;
 				
 				ArrayList<Temperature> listeTemperatures = new ArrayList<Temperature>();
 				NodeList listeNoeudsTemperatures = document.getElementsByTagName("Temperature");
 				
 				for(int position = 0; position < listeNoeudsTemperatures.getLength(); position++)
 				{
 					Element elementTemperature = (Element)listeNoeudsTemperatures.item(position);
				
 					String numero = document.getElementsByTagName("id").item(0).getTextContent();
 					double degre = Double.parseDouble(getElementsByTagName("degre").item(0).getTextContent());
 					String date = document.getElementsByTagName("date").item(0).getTextContent();
 					String heure = document.getElementsByTagName("heure").item(0).getTextContent();
 					
 					Temperature temperature= new Temperature();
	 				//	temperature.setId(Integer.parseInt(id)); // TODO : robustesse 
					listeTemperatures.add(temperature);
 				}
	 				return listeTemperatures;
 			}		
		return null;
 	}

	private NodeList getElementsByTagName(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}

