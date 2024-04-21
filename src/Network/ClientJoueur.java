package Network;
import java.io.*;
import java.net.*;

import Network.packets.Packet;
import Network.packets.Packet00Login;
import Network.packets.Packet02Move;
import Network.packets.Packet03Attack;
import Network.packets.Packet04Direction;
import Network.packets.Packet05Score;
import Network.packets.Packet.PacketTypes;
import entities.JoueurCooperatif;
import main.Game;
import modesjeu.Cooperatif;
import modesjeu.Mode;


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
			GererLesConnexions((Packet00Login)packet, address, port);
			
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
		Mode mode = this.game.getPlaying().getModejeu();
		if(mode instanceof Cooperatif) {
			((Cooperatif)mode).setScore(packet.getUserName(), packet.getScore());
		}
	}

	private void GererLesConnexions(Packet00Login packet, InetAddress address, int port) {
		System.out.println("["+address.getHostAddress()+":"+port+"] "+packet.getUserName()+ " a rejoint le jeu...");
		JoueurCooperatif joueur = new JoueurCooperatif(packet.getX(), packet.getY(), (int)(64 * Game.SCALE), (int)(40* Game.SCALE),packet.getUserName(), game.getPlaying(), address, port);
		joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
		game.getPlaying().ajoutEntity(joueur);
	}


	private void GererLesDirections(Packet04Direction packet) {
		Mode mode = this.game.getPlaying().getModejeu();
		if(mode instanceof Cooperatif) {
			((Cooperatif)mode).setDirection(packet.getUserName(), packet.isLeft(), packet.isRight(), packet.isJump());
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
