package Project2;

import java.io.*;
import java.net.*;

/*
 * Antti Herala
 */

/*
 * Thread that listens the communication coming to the port.
 * Prints out any message other user sends
 */

public class Listener extends Thread {
	
	//ServerSocket from the client
	private ServerSocket ssocket = null;
	// Boolean, that is used to terminate the thread
	private volatile boolean running = true;
	
	// Constructor, that takes in the ServerSocket 
	//and saves it into the private variable.
	public Listener(ServerSocket socket) {
		ssocket = socket;
	}
	
	// Termination function, not much to say
	public void terminate() {
		running = false;
	}
	
	/*
	 * Threads run()-function that starts when .start() is called.
	 */
	public void run() {
		// Loop with the termination boolean
		while(running) {
			try {
				// Accept the message to the port that is being listened
				Socket socket = ssocket.accept();
				// Create new InputStream to catch the message
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				// Print out the message into the console
				System.out.println(in.readLine());
				// Close the Stream, so nothing more comes.
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
