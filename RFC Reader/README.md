Authors: Antti Herala & Hanna Salopaasi 

### RFC Reader with Swing ###

Asking user about the RFC number is made on console, where it loops until the kill command is issued.

The RFC is shown through a simple UI-window, that can be 
scrolled with keyboard keys. (Navigating through RFC using keyboard keys.)

The RFC is searched using only error check, 
that if the one user wants doesn't exist. (Search for a particular RFC, if it is available.)

The UI-window has buttons for previous and next RFC. We thought about implementing some sort of cache, 
but this seemed to be more logical and better (and perhaps even easier) choice.

The UI-window has a search bar, where a word can be input and searched with the button.

### How to use: ###

Use is quite simple, run the getRfc.java and enter index for the RFC you want. 

In the window that opens after the inquiry has simply named buttons for all necessary functions. 
