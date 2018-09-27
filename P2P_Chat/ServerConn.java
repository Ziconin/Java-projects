package Project2;

import java.io.*;
import java.net.*;
import java.util.Vector;

/*
 * Antti Herala
 */

/*
 * This class is a Singleton, so there can be only one connection
 * to server at a time. Connection takes only as much time as it 
 * takes to move data for security and performance. (== I wanted to do it that way)
 * */

public class ServerConn {
	//Only on instance is allowed for this class
	private static ServerConn instance = null;
	//Save the data vector, since it must be used in constructor and closing function.
	//Saves a lot of trouble to do it this way
	private static Vector<ClientInfo> client_info = new Vector<ClientInfo>();
	
	//Unchecked cast, not an issue here
	@SuppressWarnings("unchecked")
	
	/*Protected constructor, 'cause Singleton
	 * Take in user info, users socket and flag
	 * info = current users data
	 * s = current users socket
	 * flag = passed to server to indicate if the user 
	 * is logging in/updating user list or logging out
	 */
	protected ServerConn(ClientInfo info, Socket s, boolean flag) {
		try(
			/*
			 * Has to use ObjectStreams to be able to pass users info
			 * Without these it would end up like a fat guy into a small water slide
			 */
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		) {
			/*
			 * Send info to server, then send the flag. Server has to have 
			 * corresponding reader for these. As it has. 
			 */
			out.writeObject(info);
			out.writeObject(flag);
			//Server sends out the updated user list, pass it up to client
			client_info = (Vector<ClientInfo>) in.readObject();
			//Clean out the tubes
			out.close();
			in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Singletons creation function to check up
	 * existing instances
	 */
	public static ServerConn getInstance(ClientInfo info, Socket s, boolean flag) {
		if(instance == null) {
			instance = new ServerConn(info, s, flag);
		}
		return instance;
	}
	
	/*
	 * Reset the instance
	 * Return the user info list 
	 */
	public static Vector<ClientInfo> closeInstance() {
		instance = null;
		return client_info;
	}
}
