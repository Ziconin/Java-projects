package Project3;

/*
Antti Herala
Hanna Salopaasi
*/

import java.io.*;

/*
 * getRfc-class is the main-class, that handles the execution.
 */

public class getRfc {
	
	// Private member for the UI-window for control purposes
	private static RfcPanel window = new RfcPanel();
	
	// Open up a new UI-window if one doesn't exist and give it new text
	private static void showRfc(String str) {
		// Checks if a window is already made
		if(window.getInstance()) {
			// Opens the window to the screen
			window.frame.setVisible(true);
		}
		window.setText(str);
		
	}
	
	// Parse the connection and get the .txt from ietf.org.
	// Give the UI-window knowledge about the current number of RFC
	private static void printRfc(String input) {
		String total;
		
		// Form a new connection and download the text
		Connection conn = new Connection(input);
		total = conn.getText();
		
		// If the connection gave some text out:
		if(total.length() > 0) {
			// Set the current RFC and send the file to the UI
			window.setCurrent(input);
			showRfc(total);
		}
	}

	// The main of the program
	public static void main(String[] args) {
		// Catch user input with BufferedReader
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		// "Title"
		System.out.println("RFC browser");
		
		// Ask the user to jump to a specific RFC and run until ".quit" is given
		try {
			while(true) {
				System.out.print("Enter index: ");
				userInput = stdIn.readLine();
				if(userInput.equals(".quit")) {
					System.exit(0);
					break;
				}
				// Send user input for download and parsing
				printRfc(userInput);
			}
		} catch (IOException e) {
			System.out.println("IO failed, try to restart.");
		}
	}

}
