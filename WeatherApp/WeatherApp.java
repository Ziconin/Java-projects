/*
 * Name: Antti Herala
 * 
 * Sources:
 * 	http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 	http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
 * 
 * Run the program using the location as the argument to get the information
*/

package Project1;

import java.net.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class WeatherApp {
	
		private static String parseURL(String URL1, String URL2, String location) {
			//Parse the URL with the desired location
			return URL1 + location + URL2;
		}
	
		private static double K2C(String kelvin) {
			// Convert Kelvin to Celcius
			return Double.parseDouble(kelvin) - 273.15;
		}
		
		// Read XML from the server
		private static String readXmlFromServer(String url) {
			// Read the XML into a String variable
			String total = "";
			try {
				// Define URL, connect to it and read everything
				URL xml = new URL(url);
				URLConnection uc = xml.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				// Read every line and increment that to the total string 
				String inputLine;
				while((inputLine = in.readLine()) != null) {
					total += inputLine+"\n";
				}
				// Close the connection
				in.close();
			
			}
			catch (Exception e) { // Only the location may vary (depending from the user)
				System.err.println("Invalid location!");
				System.exit(-1);
			}
			return total;
		}
		
		// Convert String to a Document for DOM, so the XML can be accessed
		private static Document parseDocument(String total) {
			Document doc = null;
			try {
				// Build Document and parse a string into it 
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(new InputSource(new StringReader(total)));
				// Normalize the Document, so the XML doesn't look weird
				doc.getDocumentElement().normalize();
			}
			catch (Exception e) {
				System.err.println("Invalid location!");
				System.exit(-1);
			}
			
			return doc;
		}
		
		// Print today's weather
		private static void currentDate(Document doc) {
			// Get the data between <current></current>
			NodeList list = doc.getElementsByTagName("current");
			// It is the only instance of <current>
			Node n = list.item(0);
			
			System.out.println("Today's weather:");
			
			//Cast Node to Element for easier access
			Element element = (Element) n;
			
			// Gather data from the attributes
			String weather = ((Element) (element.getElementsByTagName("weather").item(0))).getAttribute("value");
			String cityname = ((Element) (element.getElementsByTagName("city").item(0))).getAttribute("name");
			String temp = ((Element) (element.getElementsByTagName("temperature").item(0))).getAttribute("value");
			double newTemp = K2C(temp);
			String humidity = ((Element) (element.getElementsByTagName("humidity").item(0))).getAttribute("value") + "%";
			String wind_speed = ((Element) (element.getElementsByTagName("speed").item(0))).getAttribute("value") + " m/s";
			String wind_dir = ((Element) (element.getElementsByTagName("direction").item(0))).getAttribute("name");
			String last_update = ((Element) (element.getElementsByTagName("lastupdate").item(0))).getAttribute("value");
			
			System.out.println("\tCity: " + cityname);
			System.out.println("\tWeather: " + weather);
			System.out.printf("\tTemperature: %.2f °C\n", newTemp);
			System.out.println("\tHumidity: " + humidity);
			System.out.println("\tWind speed and direction: " + wind_speed + ", " + wind_dir);
			System.out.println("\tLast update: " + last_update);
		}
		
		// Print tomorrow's forecast
		private static void forecast(Document doc) {
			// Access location data
			NodeList list = doc.getElementsByTagName("location");
			// Get item
			Node n = list.item(0);
			
			Element element = (Element) n;
			
			//Grab the city's name and continue to the forecast
			String location = ((Element) (element.getElementsByTagName("name").item(0))).getTextContent();
			
			// Access forecast data
			list = doc.getElementsByTagName("forecast");
			// Get item
			n = list.item(0);
			
			element = (Element) n;
			
			System.out.println("Tomorrow's forecast:");
			
			String weather = ((Element) (element.getElementsByTagName("symbol").item(0))).getAttribute("name");
			String temp = ((Element) (element.getElementsByTagName("temperature").item(0))).getAttribute("day");
			String humidity = ((Element) (element.getElementsByTagName("humidity").item(0))).getAttribute("value") + "%";
			String wind_speed = ((Element) (element.getElementsByTagName("windSpeed").item(0))).getAttribute("mps") + " m/s";
			String wind_dir = ((Element) (element.getElementsByTagName("windDirection").item(0))).getAttribute("name");
			
			System.out.println("\tCity: " + location);
			System.out.println("\tWeather: " + weather);
			System.out.println("\tTemperature: " + temp + " °C");
			System.out.println("\tHumidity: " + humidity);
			System.out.println("\tWind speed and direction: " + wind_speed + ", " + wind_dir);
		}
	
		public static void main(String[] args) {
		try {
			if(args.length != 1) {
				System.err.println("Run the program with the location as the parameter, "
						+ "e.g. WeatherApp Lappeenranta returns the data of Lappeenranta.");
				System.exit(-1);
			}
			// Parse the URls for the application
			String todayURL = parseURL("http://api.openweathermap.org/data/2.5/weather?q=", "&mode=xml", args[0]);
			String tomorrowURL = parseURL("http://api.openweathermap.org/data/2.5/forecast/daily?q=", "&mode=xml&units=metric&cnt=1", args[0]);
			
			// Read XML respectively
			String today = readXmlFromServer(todayURL);
			String tomorrow = readXmlFromServer(tomorrowURL);
			
			// Parse the String to valid XML Document
			Document doc_today = parseDocument(today);
			Document doc_tomorrow = parseDocument(tomorrow);
			
			// Print out the XML in a satisfying format
			currentDate(doc_today);
			forecast(doc_tomorrow);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
