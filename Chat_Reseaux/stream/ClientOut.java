package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientOut extends Thread {

    private DatagramSocket clientSocket;
    private List<DatagramSocket> liste;
    private List<Message> message;

    ClientOut(DatagramSocket s, List<DatagramSocket> listeclient, List<Message> listeMessage) {
        this.clientSocket = s;
        this.liste = listeclient;
        this.message = listeMessage;
    }
    
    private void broadcast(Message m) throws IOException {
        broadcast(m, false);
    }

    private void broadcast(Message m, boolean sendToSender) throws IOException {
        synchronized (liste) {
            for (DatagramSocket so : liste) {
                if (sendToSender || so != clientSocket)
                    new PrintStream(so.getOutputStream()).println(m);
            }
        }
        synchronized (message) {
            message.add(m);
        }
    }

    @Override
    public void run() {
        String name = "";
        try (BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintStream socOut = new PrintStream(clientSocket.getOutputStream());) {
            name = socIn.readLine();
            for (Message m : message) {
                socOut.println(m);
            }
            broadcast(new Message(name, true), true);
            while (true) {
                broadcast(new Message(name, socIn.readLine()));
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        } finally {
            liste.remove(clientSocket);
            try {
            broadcast(new Message(name, false));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}