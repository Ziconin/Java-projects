package Project3;

/*
Antti Herala
Hanna Salopaasi
*/

import java.awt.Color;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

/*
 * UI-window for reading the RFC. Very simple UI-window with a scrollable view,
 * few buttons and a search bar, made just to be convenient. The UI-window is a 
 * Singleton, so there won't be a million windows after the user is done.
 * 
 * We know it should be able to handle like 5 instances for convenience, but we went with this
 */

public class RfcPanel {

	// frame is necessary to run the whole thing
	public JFrame frame = null;
	// scrollPane is used in few functions, so it's easier to have it as a member
	private JScrollPane scrollPane = null;
	// Same thing as with scrollPane
	private JTextArea label = null;
	// The instance of the window, so only one can be created
	private boolean instance = false;
	// The current RFC
	private String current;
	// The search word
	private String word = "";
	// Text field for the search bar
	private JTextField textField;
	// Highlight painter, so there won't be multiple painters created when the button is pressed multiple times
	Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);

	
	// Constructor, where the instance-flag is turned
	public RfcPanel() {
		instance = true;
		initialize();
	}
	
	// Return the instance-flag for main-class
	public boolean getInstance() {
		return instance;
	}
	
	// Set the current RFC from the main-class
	public void setCurrent(String nmb) {
		current = nmb;
	}
	
	// Change the text in the UI-window. Seems ridiculous, but it's effective
	void setCurrText(boolean flag) {
		// Get int out of string
		int curr = Integer.parseInt(current);
		// Increment of decrement, depending which button you hit
		if(flag) {
			curr++;
		}
		else {
			curr--;
		}
		// Turn the int back to string and create new connection to update the text area
		current = Integer.toString(curr);
		Connection conn = new Connection(current);
		setText(conn.getText());
	}
	
	// Set new Text area and set is visible
	void setText(String text) {
		label = new JTextArea(text);
		scrollPane.setViewportView(label);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("RFC reader");
		frame.setBounds(100, 100, 750, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scrollPane = new JScrollPane();
		
		// Enable scrolling with arrow buttons
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		InputMap verticalMap = vertical.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW );
		verticalMap.put( KeyStroke.getKeyStroke( "DOWN" ), "positiveUnitIncrement" );
		verticalMap.put( KeyStroke.getKeyStroke( "UP" ), "negativeUnitIncrement" );
		
		// Go to previous RFC
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setCurrText(false);
			}
		});
		
		// Go to next RFC
		JButton btnNext = new JButton("Next");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setCurrText(true);
			}
		});
		
		// Implementing the search bar, first text field...
		textField = new JTextField();
		textField.setColumns(10);
		// ...and then the button! 
		JButton btnSearch = new JButton("Search!");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			// Adding a mouseClicked-event
			public void mouseClicked(MouseEvent arg0) {
				// Get user input from text field
				word = textField.getText();
				// If there's something:
				if(word.length() > 0) {
					// Delete old highlights
					label.getHighlighter().removeAllHighlights();
					// Get the index and length of the word
					int offset = label.getText().indexOf(word);
					int length = word.length();
					// While the index is still in the document, i.e. not EOF
					while(offset != -1) {
						try {
							label.getHighlighter().addHighlight(offset, offset+length, painter);
							offset = label.getText().indexOf(word, offset+1);
						} catch (BadLocationException e) {
							System.out.println("Highligher has malfunctioned. Please restart.");
						}
					}
				}
			}
		});
		// Setting up the layout and binding all elements to it, nothing fancy
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addComponent(btnPrevious)
					.addPreferredGap(ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSearch)
					.addGap(158)
					.addComponent(btnNext)
					.addGap(21))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPrevious)
						.addComponent(btnNext)
						.addComponent(btnSearch)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
					.addGap(8))
		);
		// Bind layout to frame
		frame.getContentPane().setLayout(groupLayout);
	}
}
