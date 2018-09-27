package Project2;

/*
 * Antti Herala
 */

/*
 * Mockup class for demonstrations
 * 
 * Run this, is you want to test multiple connections and sending messages
 * */

public class MockClient {

	public static void main(String[] args) {
		// Opens a new client on startup, where username is "Vertti",
		//host is localhost and port 30000
		new Client("Vertti", "localhost", 30000);
	}

}
