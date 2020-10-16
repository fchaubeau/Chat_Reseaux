/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */
package Chat_Reseaux.stream;

import java.net.*;
import java.util.*;
import java.io.*;

public class EchoServerMultiThreaded {

	private static List<Message> historique() {
		List<Message> listeMessage = Collections.synchronizedList(new ArrayList<Message>());
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("logs.txt"));) {
			listeMessage = (List<Message>) objectInputStream.readObject();
		} catch (Exception e) {
			System.err.println("Erreur lors du chargement de l'historique des messages : " + e);
		}
		return listeMessage;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		try (ServerSocket listenSocket = new ServerSocket(Integer.parseInt(args[0]));) {
			List<Socket> listeclient = Collections.synchronizedList(new ArrayList<Socket>());
			List<Message> listeMessage = historique();
			System.out.println("Server ready...");
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				ClientOut co = new ClientOut(clientSocket, listeclient, listeMessage);
				co.start();
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
<<<<<<< HEAD:stream/EchoServerMultiThreaded.java
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
		}
		
      }
  }
=======
	}
}
>>>>>>> 59d058a26e8ae30f7c90228fae2bc04ac889c966:Chat_Reseaux/stream/EchoServerMultiThreaded.java
