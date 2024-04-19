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

import main.Game;

public class ServeurCentral extends Thread{
	//Variables client
		private DatagramSocket socket;
		private Game game;
		
		
		
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
	                String message  = new String(packet.getData());
	                if(message.trim().equalsIgnoreCase("ping"))
	                	System.out.println("CLIENT > "+message);
	                	envoieData("pong".getBytes(), packet.getAddress(),packet.getPort());
	        } 
	    }
		

}


