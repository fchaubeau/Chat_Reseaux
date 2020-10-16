package Chat_Reseaux.stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientOut extends Thread {

    private Socket clientSocket;
    private List<Socket> liste;
    private List<Message> message;

    ClientOut(Socket s, List<Socket> listeclient, List<Message> listeMessage) {
        this.clientSocket = s;
        this.liste = listeclient;
        this.message = listeMessage;
    }

    private void broadcast(Message m) throws IOException {
        broadcast(m, false);
    }

    private void broadcast(Message m, boolean sendToSender) throws IOException {
        synchronized (liste) {
            for (Socket so : liste) {
                if (sendToSender || so != clientSocket)
                    new PrintStream(so.getOutputStream()).println(m);
            }
        }
        synchronized (message) {
            message.add(m);
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("logs.txt"));) {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            } catch (Exception e) {
                System.err.println("Erreur lors de la sérialisation des messages : " + e);
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
            while (!clientSocket.isClosed()) {
                broadcast(new Message(name, socIn.readLine()));
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
            
        }
        synchronized (liste) {
        liste.remove(clientSocket);
        }
        try {
            broadcast(new Message(name, false));
            System.out.println("fin du thread de " + name);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}