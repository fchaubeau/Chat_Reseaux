package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;

public class ClientOut
	extends Thread {
	
	private Socket clientSocket;
	
	ClientOut(Socket s) {
		this.clientSocket = s;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
    	  try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            String line;
            while (true) {
    		  	line = stdIn.readLine();
          		socOut.println(line);
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
       }
  
  }

  