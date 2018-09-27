package Project3;

/*
Antti Herala
Hanna Salopaasi
*/

import java.io.*;
import java.net.*;

/*
 * Connection-class is responsible for forming the connection to
 * ietf.org and downloading the text. The availability is done by error checking, 
 * whether the file exists or not. The requirement "Search for a particular RFC, if it is available."
 * was so hazy, that this was the only logical solution we came up with.
 */
public class Connection {
	
	// Private member, where the .txt is stored
	private static String text = "";
	
	// Constructor with user input as parameter
	public Connection(String nmb) {
		try {
			// Standard URL-connection and read
			URL rfc = new URL(parseURL(nmb));
			URLConnection conn = rfc.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line;
			text = "";
			// Read the whole file and add to the variable
			while((line = reader.readLine()) != null) {
				text += line + "\n";
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// If the file doesn't exist
			System.out.println("404 File not found.");
		} catch (MalformedURLException e) {
			// If the URL fails, user doesn't have to know more
			System.out.println("404 File not found.");
		} catch (IOException e) {
			// IO failed
			System.out.println("Couldn't open file.");
		} 
	}
	
	// Parse the URL, so it can respond to users wishes
	private String parseURL(String nmb) {
		return "https://www.ietf.org/rfc/rfc" + nmb + ".txt";
	}
	
	// Getter for the text-variable
	public String getText() {
		return text;
	}

}
