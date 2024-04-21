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
import Network.packets.Packet02Move;
import Network.packets.Packet03Attack;
import Network.packets.Packet04Direction;
import Network.packets.Packet05Score;
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
		
		public JoueurCooperatif getJoueurCooperatif(String username) {
			for(JoueurCooperatif jc : joueursConnectes) {
				if(jc.getUsername().equals(username))
					return jc;
			}
			return null;
		}
		
		public int getJoueurCooperatifIndex(String username) {
			int index = 0;
			for(JoueurCooperatif jc : joueursConnectes) {
				if(jc.getUsername().equals(username))
					break;
				index++;
			}
			return index;
			
		}
		
		@Override
	    public void run() {
			System.out.println("En attent de joueurs pour se connecter ...");
	        
	            while (!ready) {
	                byte[] data = new byte[1024];
	                DatagramPacket packet = new DatagramPacket(data, data.length);
	                try {
	                	socket.receive(packet);
	    	            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
	            
	                }catch (IOException e) {
	                    e.printStackTrace();
	                }
	                //this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
	                /*
	                String message  = new String(packet.getData());
	                if(message.trim().equalsIgnoreCase("ping"))
	                	System.out.println("CLIENT > "+message);
	                	envoieData("pong".getBytes(), packet.getAddress(),packet.getPort());
	                	*/
	            }
	            startGame();
	            
	    }

		private void startGame() {
			ready = true;  // Set ready when all players are connected
            game.setRunning(ready);
            System.out.println("All players connected. Starting game...");
	        envoieDataToJoueurs("Game Start".getBytes()); // Notify all players to start
           
			
		}


		private void sendGameFullMessage(InetAddress address, int port) {
			String msg = "Jeu complet \nJouer en mode solo";
			envoieData(msg.getBytes(), address, port);
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
				if(!ready) {
					if(joueursConnectes.size() < nbJoueurs) {
						packet = new Packet00Login(data);
						System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet00Login)packet).getUserName()+ " est connecté...");
						JoueurCooperatif joueur = new JoueurCooperatif(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), ((Packet00Login)packet).getUserName(), game.getPlaying(), address, port);
						joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
						joueur.setJoueurLocal(false);
						ajoutConnexion(joueur,(Packet00Login)packet);
					}else {
						sendGameFullMessage(address, port);
					}
				}
				
				break;
			case DISCONNECT:
				break;
				
			case MOVE:
				 packet = new Packet02Move(data);
				 System.out.println(((Packet02Move)packet).getUserName()+" a bougé vers"
						 			+((Packet02Move)packet).getX() +","+((Packet02Move)packet).getY());
				 this.GererLesMouvements((Packet02Move)packet);
				break;
				
			case ATTACK:
				packet  = new Packet03Attack(data);
				System.out.println(((Packet03Attack)packet).getUserName()+" attaque");
				this.GererLesAttaques((Packet03Attack)packet);
				break;
			case DIRECTION:
				packet = new Packet04Direction(data);
				System.out.println("Jumping or moving left or right");
				this.GererLesDirections((Packet04Direction)packet);
				break;
			case SCORE:
				packet = new Packet05Score(data);
				System.out.println(((Packet05Score)packet).getUserName() + " a incrimenté son score à "+((Packet05Score)packet).getScore());
				this.GererLesScores((Packet05Score)packet);
				break;
			}
			
		}

		private void GererLesScores(Packet05Score packet) {
			if(getJoueurCooperatif(packet.getUserName()) != null) {
				int index = getJoueurCooperatifIndex(packet.getUserName());
				this.joueursConnectes.get(index).setScore(packet.getScore());
				packet.writeData(this);
			}
			
		}


		private void GererLesDirections(Packet04Direction packet) {
			if(getJoueurCooperatif(packet.getUserName()) != null) {
				int index = getJoueurCooperatifIndex(packet.getUserName());
				this.joueursConnectes.get(index).setLeft(packet.isLeft());
				this.joueursConnectes.get(index).setRight(packet.isRight());
				this.joueursConnectes.get(index).setJump(packet.isJump());
				packet.writeData(this);
			}
			
		}


		private void GererLesAttaques(Packet03Attack packet) {
			if(getJoueurCooperatif(packet.getUserName()) != null) {
				int index = getJoueurCooperatifIndex(packet.getUserName());
				this.joueursConnectes.get(index).setAttack(packet.getAttack());
				packet.writeData(this);
			}
			
		}


		private void GererLesMouvements(Packet02Move packet) {
			if(getJoueurCooperatif(packet.getUserName()) != null) {
				int index = getJoueurCooperatifIndex(packet.getUserName());
				this.joueursConnectes.get(index).getHitbox().x = packet.getX();
				this.joueursConnectes.get(index).getHitbox().y = packet.getY();
				packet.writeData(this);
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
					packet = new Packet00Login(jc.getUsername(), jc.getHitbox().x, jc.getHitbox().y);
					envoieData(packet.getData(), joueur.ip, joueur.port);
				}	
			}
			if(!dejaConnecte) {
				this.joueursConnectes.add(joueur);
				//packet.writeData(this); YOU SON  OF B :(
			}
			/*if(joueursConnectes.size() == nbJoueurs) {
				startGameForAllPlayers();
			}*/
		}

		/*private void startGameForAllPlayers() {
			for(JoueurCooperatif jc : joueursConnectes) {
				if(!jc.estJoueurLocal())
					jc.getClient().getGame().setRunning(true);
			}
			 System.out.println("All players connected. Starting game...");
	         envoieDataToJoueurs("Game Start".getBytes()); // Notify all players to start
			
		}*/


		public void envoieDataToJoueurs(byte[] data) {
			for(JoueurCooperatif jc: joueursConnectes) {
				envoieData(data, jc.ip, jc.port);
			}
			
		}
		

}


