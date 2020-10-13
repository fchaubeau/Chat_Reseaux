package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientOut
	extends Thread {
	
    private Socket clientSocket;
    private List<Socket> liste;
	
	ClientOut(Socket s, List<Socket> listeclient) {
        this.clientSocket = s;
        this.liste = listeclient;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
      public void run() {
        try {
          BufferedReader socIn = null;
          socIn = new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream()));    
          PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
          while (true) {
                String line = socIn.readLine();
                synchronized (liste) {
                    for (Socket so : liste) {
                        if (so != clientSocket)
                            new PrintStream(so.getOutputStream()).println(line);
                    }
                }
                //System.out.println(line);
          }
      } catch (Exception e) {
          System.err.println("Error in EchoServer:" + e); 
      }
     }
  
  }

  