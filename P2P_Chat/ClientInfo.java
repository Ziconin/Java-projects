package Project2;

import java.io.Serializable;

/*
 * Antti Herala
 */

/*
 * Container-class that holds the clients data.
 * Includes the username, host, port and online-flag.
 * Has to be serializable because Java.
 */
// The serializable class ClientInfo does not declare 
// a static final serialVersionUID field of type long, so suppress the warning
@SuppressWarnings("serial")
public class ClientInfo implements Serializable {
	
	// Holds the data about the client
	private String username;
	private String host;
	private int port;
	private boolean online;
	
	//Constructor, where the data is set
	public ClientInfo(String user, String host, int port) {
		username = user;
		this.host = host;
		this.port = port;
		online = true;
	}
	// Bunch of getters and setters for future use.
	public String getUser() {
		return username;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public int setPort(int newPort) {
		return port = newPort;
	}
	
	public boolean getOnline() {
		return online;
	}
	
	public void setOnline(boolean set) {
		online = set;
	}

}
