/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

	private Socket clientSocket;

	ClientThread(Socket s) {
		this.clientSocket = s;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket the client socket
	 **/
	@Override
	public void run() {
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while (true) {
				String line = socIn.readLine();
				System.out.println(line);
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
