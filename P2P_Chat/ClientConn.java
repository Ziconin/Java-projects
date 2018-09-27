package Project2;

import java.io.*;
import java.net.*;

/*
 * Antti Herala
 */

/*
 * Handles the connection between two clients.
 * No questions asked, could be larger is need be, but small is beautiful.
 */

public class ClientConn {
	
	//Just construct the hell out of it
	public ClientConn(Socket s, String msg) {
		try (
			// New printWriter, so we can send stuff.
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		) {
			// Send stuff.
			out.println(msg);
			// When stuff gone, close door.
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
