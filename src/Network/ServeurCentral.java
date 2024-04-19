package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ServeurCentral{
	
	private ServerSocket serverSocket;
	private List<ClientJoueur> joueurs = new ArrayList<ClientJoueur>();
	private int nbJoueurs;
	private boolean started = false;
	
	public ServeurCentral(int port, int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started on port " + port);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void acceptJoueurs() {
		while(joueurs.size() < nbJoueurs) {
			try {
				 Socket clientSocket = serverSocket.accept();
	             ClientJoueur clientHandler = new ClientJoueur(clientSocket, this);
	             joueurs.add(clientHandler);
	             clientHandler.start();
	             System.out.println("Player connected. Total players: " + joueurs.size());
	         } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
	 	System.out.println("All players connected. Starting game...");
	 	started = true;
	 	// Notify all clients that the game is starting
	 	broadcastMessage("START", null);

	}
	
	public void handlePlayerAction(String action, ClientJoueur sender) {
		for(ClientJoueur j : joueurs) {
			if(j == sender) {
				
			}
		}
	}
	
	public void broadcastMessage(String message, ClientJoueur sender) {
		for (ClientJoueur j : joueurs) {
			if (j != sender) {
				j.sendMessage(message);
        }
    }
	}
	public boolean isGameStarted() {
    	return started;
	}
}


