import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.io.StringBufferInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;

import accesseur.AccelerometreDAO;

public class AccelerometreService 
{
	public static void main (String[] args)
	{
		
		AccelerometreDAO accelerometreDAO = new AccelerometreDAO();
		accelerometreDAO.listerAccelerometres();
		
	}

}