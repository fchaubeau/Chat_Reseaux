/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread {

	private MulticastSocket groupSocket;
	private InetAddress groupAddress;

	ClientThread(MulticastSocket s, InetAddress groupAddress) {
		this.groupSocket = s;
		this.groupAddress = groupAddress;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket the client socket
	 **/
	@Override
	public void run() {
		try {
			//groupSocket.joinGroup(groupAddress);
			while (true) {
				byte [] buffer = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				groupSocket.receive(packet);
				System.out.println(new String(buffer));
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
