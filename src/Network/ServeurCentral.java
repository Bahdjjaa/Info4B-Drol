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
		private List<JoueurCooperatif> joueursCooperatifs = new ArrayList<JoueurCooperatif>();
		
		
		
		//Constructeur
		public ServeurCentral(Game game) {
			this.game = game;
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
	        
	            while (true) {
	                byte[] data = new byte[1024];
	                DatagramPacket packet = new DatagramPacket(data, data.length);
	                try {
	                	socket.receive(packet);
	                }catch (IOException e) {
	                    e.printStackTrace();
	                }
	                parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
	                /*
	                String message  = new String(packet.getData());
	                if(message.trim().equalsIgnoreCase("ping"))
	                	System.out.println("CLIENT > "+message);
	                	envoieData("pong".getBytes(), packet.getAddress(),packet.getPort());
	                	*/
	        } 
	    }

		private void parsePacket(byte[] data, InetAddress address, int port) {
			String message =  new String(data).trim();
			PacketTypes type = Packet.chercherPacket(message.substring(0, 2));
			
			switch(type) {
			default:
			case INVLIDE:
				break;
			case LOGIN:
				Packet00Login packet = new Packet00Login(data);
				System.out.println("["+address.getHostAddress()+":"+port+"] "+packet.getUserName()+ " est connecté...");
				JoueurCooperatif joueur = new JoueurCooperatif(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), packet.getUserName(), game.getPlaying(), address, port);
				joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
				joueursCooperatifs.add(joueur);
				//Ajouter le joueur au jeu
				game.getPlaying().ajoutEntity(joueur);
				break;
			case DISCONNECT:
				break;
			}
			
		}

		public void envoieDataToJoueurs(byte[] data) {
			for(JoueurCooperatif jc: joueursCooperatifs) {
				envoieData(data, jc.ip, jc.port);
			}
			
		}
		

}


