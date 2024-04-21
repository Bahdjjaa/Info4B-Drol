package Network.packets;

import Network.ClientJoueur;
import Network.ServeurCentral;

public class Packet02Move extends Packet {
	
	private String username;
	private float x, y;
	

	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArr = lireData(data).split(",");
		this.username = dataArr[0];
		this.x = Float.parseFloat(dataArr[1]);
		this.y = Float.parseFloat(dataArr[2]);
	
		
	}
	
	public Packet02Move(String username, float x, float y) {
		super(02);
		this.username = username;
		this.x = x;
		this.y = y;

	}

	@Override
	public void writeData(ClientJoueur joueur) {
		joueur.envoieData(getData());
		
	}

	@Override
	public void writeData(ServeurCentral serveur) {
		serveur.envoieDataToJoueurs(getData());
		
	}

	@Override
	public byte[] getData() {
		return ("02"+ this.username+","+this.x+","+this.y).getBytes();
	}

	public String getUserName() {
		return username;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	
}
