package Project2;

import java.io.*;
import java.net.*;
import java.util.Vector;

/*
 * Antti Herala
 */

/*
 * Basic server side functionality for client/server:
 * Has a ServerSocket to listen the connections
 * When a connection is created, it creates 
 * ObjectStreams to get and send back data
 * Catch users info and after that the flag concerning, what task the server has to do
 * Return data to user
 */

public class Server {
	
	// Private port that the server listens
	private static int port = 50000;
	// Vector for user data and reserved nicks
	private static Vector<ClientInfo> client_info = new Vector<ClientInfo>();
	private static Vector<String> names = new Vector<String>();

	// Just main, no fancy constructors here
	public static void main(String[] args) throws IOException {
		try (
			// Whip up a new ServerSocket
			ServerSocket serverSocket = new ServerSocket(port);
		) {
			while(true) {
				// Listen for connections and accept those
				Socket clientSocket = serverSocket.accept();
				// New ObjectStreams
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				// Get clients info and the flag
				ClientInfo info = (ClientInfo) in.readObject();
				boolean flag = (boolean) in.readObject();
				
				// If flag's true, add user to the list, unless it's already there
				if(flag) {
					if(!(names.contains(info.getUser()))) {
						client_info.add(info);
						names.add(info.getUser());
					}
					else{ // If the user name is already in the vector
						// Search the one and update the user online
						// If familiar user logs in
						for(int i=0;i<client_info.size();i++) {
							if(names.get(i).equals(info.getUser())) {
								client_info.get(i).setOnline(true);
							}
						}
					}
				}
				// Else just update the user to be offline
				else {
					for(int i=0;i<client_info.size();i++) {
						if(names.get(i).equals(info.getUser())) {
							client_info.get(i).setOnline(false);
						}
					}
				}
				// Send back the user list				
				out.writeObject(client_info);
				// Close all unnecessary streams and sockets
				clientSocket.close();
				in.close();
				out.close();
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
