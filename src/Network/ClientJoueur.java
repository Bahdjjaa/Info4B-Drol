package Network;
import java.io.*;
import java.net.*;

import main.Game;

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
                System.out.println("SERVER > "+ new String(packet.getData()));
        } 
    }
	

}
