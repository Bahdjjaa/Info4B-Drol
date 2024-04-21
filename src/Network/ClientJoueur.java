package Network;
import java.io.*;
import java.net.*;

import Network.packets.Packet;
import Network.packets.Packet00Login;
import Network.packets.Packet02Move;
import Network.packets.Packet03Attack;
import Network.packets.Packet.PacketTypes;
import entities.JoueurCooperatif;
import main.Game;
import modesjeu.Cooperatif;
import modesjeu.Mode;
import modesjeu.Modejeu;

public class ClientJoueur extends Thread{

	//Variables client
	private InetAddress ip;
	private DatagramSocket socket;
	private Game game;
	
	
	
	//Constructeur
	public ClientJoueur(Game game, String ip) {
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName(ip);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	public Game getGame() {
		return game;
	}
	
	public void envoieData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, 1331);
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
                this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
                //System.out.println("SERVER > "+ new String(packet.getData()));
        } 
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
			if(message.equalsIgnoreCase("Jeu complet \nJouer en mode solo")) {
				System.out.println("Vous ne pouvez rejoindre: " + message);
				return;
			}	
			packet = new Packet00Login(data);
			System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet00Login)packet).getUserName()+ " a rejoint le jeu...");
			JoueurCooperatif joueur = new JoueurCooperatif(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), ((Packet00Login)packet).getUserName(), game.getPlaying(), address, port);
			joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
			//Ajouter le joueur au jeu
			//joueur.setClient(this);
			game.getPlaying().ajoutEntity(joueur);
			break;
		case DISCONNECT:
			break;
		case MOVE:
			packet = new Packet02Move(data);
			this.GererLesMouvements((Packet02Move)packet);
			break;
		case ATTACK:
			packet = new Packet03Attack(data);
			this.GererLesAttaques((Packet03Attack)packet);
		}
		
	}

	private void GererLesAttaques(Packet03Attack packet) {
		Mode mode = this.game.getPlaying().getModejeu();
		if(mode instanceof Cooperatif) {
			((Cooperatif)mode).setJoueurEnAttaque(packet.getUserName(), packet.getAttack());
		}
			
		
	}

	private void GererLesMouvements(Packet02Move packet) {
		Mode mode = this.game.getPlaying().getModejeu();
		if(mode instanceof Cooperatif) {
			((Cooperatif)mode).moveJoueur(packet.getUserName(), packet.getX(), packet.getY());
		}
		
	}
	

}
