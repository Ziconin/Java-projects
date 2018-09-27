package Project2;

import java.io.*;
import java.net.*;
import java.util.Vector;

/*
 * Antti Herala
 */

/*
 * The base for Client-class. Holds the basic functionality:
 * ServerSocket to enable listening
 * Listener-object for incoming connections
 * Socket to send out data
 * ClientInfo-object to hold the users info
 * client_info-Vector to hold information about all the users
 */

public class Client {
	
	private static ServerSocket server = null;
	private static Socket socket = null;
	
	private static ClientInfo info = null;
	private static Vector<ClientInfo> client_info = null;
	
	// Constructing the class with username, host and port to be listened
	public Client(String name, String host, int port) {
		
		// New ClientInfo from the parameters
		info = new ClientInfo(name, host, port);
		
		try {
			// Start a new ServerSocket that listens to the port
			server = new ServerSocket(port);
			
			// Start up the Listener-thread to listen the connections in the background
			Listener listen = new Listener(server);
			listen.start();
			
			// Enable user input, i.e. main loop
			userInput();
			
			// Kill the thread when user quits
			listen.terminate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void userInput() {
		try {
			// New user input reader plus some helper Strings
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromUser;
			String msg;
			
			// Main loop, that listens user to the point when stdIn fails
			while((fromUser = stdIn.readLine()) != null) {
				
				if(fromUser.equals(".quit")) {
					info.setOnline(false); // Set user offline
					updateList(false); // Update the server that the user is offline
					socket = new Socket(info.getHost(), info.getPort());
					// Open new socket, that sends empty message to the 
					// Listener-thread to kill it
					new ClientConn(socket, " "); // Show ending message and break loop
					System.out.println("Bye!");
					break;
				}
				// Log the user in:
				else if(fromUser.equals(".login")) {
					info.setOnline(true); // Set user online
					updateList(true); // Update users copy of user list
					System.out.println("Logged in."); // Notify user
				}
				
				else if(fromUser.equals(".update")) {
					updateList(true); // Update users copy of user list
				}
				
				else if(fromUser.equals(".names")) {
					printUsers(); // Print the user list
				}

				// Connect user to another and send a message:
				else if(fromUser.equals(".connect")) {
					printUsers(); // Print the user list
					System.out.print("Select user: "); // Ask username
					ClientInfo user = searchUser(stdIn.readLine());
					// Search the user from the list
					if(user.equals(info)) { // If there's no such nick
						System.out.println("No such user!");
					}
					else {
						System.out.print("Message: "); // What the message contains
						// Parse the message to look like a real message
						msg = "Message from " + info.getUser() + ": " + stdIn.readLine();
						// Open socket to that user and send message
						socket = new Socket(user.getHost(), user.getPort());
						new ClientConn(socket, msg);
						// Turn socket back to null to close the connection
						socket = null;
						// Info to user, that the message went away
						System.out.println("Message sent!");
					}					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Helper-funtions for updating and printing the list
	public static void updateList(boolean flag) {
		try {
			socket = new Socket(info.getHost(), 50000); // Open socket to server
			ServerConn.getInstance(info, socket, flag); // Send clients info, socket and flag
			// Flags point is to indicate the server to either add user to the list
			// or just update the existing field
			client_info = ServerConn.closeInstance(); // Get Vector from server
			socket = null; // Nullify socket
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Print users
	public static void printUsers() {
		// Basic for-loop, where the Vector is printed out
		for(int i=0;i<client_info.size();i++) {
			if(client_info.get(i).getOnline()) { 
			// If user's online-field is true, the online-bracket is printed
				System.out.print("Online: ");
			}
			else {
			// Else it's offline-bracket for them
				System.out.print("Offline: ");
			}
			// Print the user and online-status
			System.out.println(client_info.get(i).getUser());
		}
	}
	
	// Search user from the Vector
	public static ClientInfo searchUser(String name) {
		int i;
		// Standard for-loop
		for(i=0;i<client_info.size();i++) {
			// Find equal name from the Vector
			if(name.equals(client_info.get(i).getUser())) {
				break;
			}
		}
		// If there isn't such a nick, the users own info is returned
		if(i == client_info.size()) {
			return info;
		}
		// Or the searched info is returned
		return client_info.get(i);
	}
	
	/*
	 * main-method, where a new Client is created. When necessary, 
	 * run MockClient-class to test out the messaging and other uses
	 */
	public static void main(String[] args) {
		new Client("Leevi", "localhost", 20000);
	}
}