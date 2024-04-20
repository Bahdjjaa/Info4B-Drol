package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import Network.packets.Packet;
import Network.packets.Packet.PacketTypes;
import Network.packets.Packet00Login;
import entities.JoueurCooperatif;
import main.Game;

public class ServeurCentral extends Thread{
	//Variables client
		private DatagramSocket socket;
		private Game game;
		private List<JoueurCooperatif> joueursConnectes = new ArrayList<JoueurCooperatif>();
		private int nbJoueurs;
		private boolean ready = false;
		
		
		
		public boolean isReady() {
			return ready;
		}


		//Constructeur
		public ServeurCentral(Game game) {
			this.game = game;
			nbJoueurs = game.getOptionsJeu().getNbPlayers();
			try {
				this.socket = new DatagramSocket(1331);
	        } catch (SocketException e) {
	            e.printStackTrace();
	        } 
			
		}
		
		public void envoieData(byte[] data, InetAddress ip, int port) {
			DatagramPacket packet = new DatagramPacket(data, data.length,ip,port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
	    public void run() {
			System.out.println("En attent des autres joueurs pour se connecter ...");
	        
	            while (joueursConnectes.size() < nbJoueurs) {
	                byte[] data = new byte[1024];
	                DatagramPacket packet = new DatagramPacket(data, data.length);
	                try {
	                	socket.receive(packet);
	                }catch (IOException e) {
	                    e.printStackTrace();
	                }
	                this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
	                /*
	                String message  = new String(packet.getData());
	                if(message.trim().equalsIgnoreCase("ping"))
	                	System.out.println("CLIENT > "+message);
	                	envoieData("pong".getBytes(), packet.getAddress(),packet.getPort());
	                	*/
	            }
	            ready = true;  // Set ready when all players are connected
	            game.setRunning(ready);
	            System.out.println("All players connected. Starting game...");
	            envoieDataToJoueurs("Game Start".getBytes()); // Notify all players to start
	    }

		private void parsePacket(byte[] data, InetAddress address, int port) {
			String message =  new String(data).trim();
			PacketTypes type = Packet.chercherPacket(message.substring(0, 2));
			Packet packet = null;
			switch(type) {
			default:
			case INVLIDE:
				break;
			case LOGIN:
				packet = new Packet00Login(data);
				System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet00Login)packet).getUserName()+ " est connecté...");
				JoueurCooperatif joueur = new JoueurCooperatif(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), ((Packet00Login)packet).getUserName(), game.getPlaying(), address, port);
				joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
				joueur.setJoueurLocal(false);
				ajoutConnexion(joueur,(Packet00Login)packet);
				break;
			case DISCONNECT:
				break;
			}
			
		}

		public void ajoutConnexion(JoueurCooperatif joueur, Packet00Login packet) {
			boolean dejaConnecte = false;
			for(JoueurCooperatif jc: this.joueursConnectes) {
				if(joueur.getUsername().equalsIgnoreCase(jc.getUsername())) {
					if(jc.ip == null)
						jc.ip = joueur.ip;
						
					if(jc.port == -1)
						jc.port = joueur.port;
					
					dejaConnecte = true;
				}else {
					envoieData(packet.getData(), jc.ip, jc.port);
					packet = new Packet00Login(jc.getUsername());
					envoieData(packet.getData(), joueur.ip, joueur.port);
				}	
			}
			if(!dejaConnecte) {
				this.joueursConnectes.add(joueur);
				//packet.writeData(this); YOU SON  OF B :(
			}
		}

		public void envoieDataToJoueurs(byte[] data) {
			for(JoueurCooperatif jc: joueursConnectes) {
				envoieData(data, jc.ip, jc.port);
			}
			
		}
		

}


