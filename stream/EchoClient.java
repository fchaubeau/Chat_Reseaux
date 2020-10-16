/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient {

  /**
   * main method accepts a connection, receives a message from client then sends
   * an echo to the client
   **/
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
      System.exit(1);
    }
    //args[0], Integer.parseInt(args[1])
    MulticastSocket groupSocket = new MulticastSocket(Integer.parseInt(args[1]));
    try (
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
      int groupPort = Integer.parseInt(args[1]);
      InetAddress groupAddress = InetAddress.getByName(args[0]);
      groupSocket.joinGroup(groupAddress);
      ClientThread ct = new ClientThread(groupSocket,groupAddress);
      String name = "";
      while (name.length() < 1) {
        System.out.println("Entrez un nom d'utilisateur");
        name = stdIn.readLine();
      }
      byte [] bufferName = new Message(name,true).toString().getBytes();
      DatagramPacket packetName = new DatagramPacket(bufferName, bufferName.length, groupAddress, groupPort);
      groupSocket.send(packetName);
      ct.start();
      String line = "";
      while (true) {
        line = stdIn.readLine();
        if (line.equals("."))
          break;
        byte [] bufferLine = new Message(name,line).toString().getBytes();
        DatagramPacket packetLine = new DatagramPacket(bufferLine, bufferLine.length, groupAddress, groupPort);
        groupSocket.send(packetLine);
      }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host:" + args[0]);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for " + "the connection to:" + args[0]);
      System.err.println(e);
      System.exit(1);
    }
    
  }
}
