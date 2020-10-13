/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */
package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServerMultiThreaded  {
  
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
       public static void main(String args[]){ 
        ServerSocket listenSocket;
        
  	if (args.length != 1) {
          System.out.println("Usage: java EchoServer <EchoServer port>");
          System.exit(1);
  	}
	try {
		listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
		List<Socket> listeclient = Collections.synchronizedList(new ArrayList<Socket>());
		System.out.println("Server ready..."); 
		while (true) {
			Socket clientSocket = listenSocket.accept();
			listeclient.add(clientSocket);
			System.out.println("Connexion from:" + clientSocket.getInetAddress());
			/*
			ClientThread ct = new ClientThread(clientSocket);
			ct.start();
			*/
			ClientOut co = new ClientOut(clientSocket, listeclient);
			co.start();
		}
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
      }
  }