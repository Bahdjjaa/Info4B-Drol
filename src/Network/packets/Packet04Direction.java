package Network.packets;

import Network.ClientJoueur;
import Network.ServeurCentral;

public class Packet04Direction extends Packet{
	
	private String username;
	private boolean left, right, jump;

	public Packet04Direction(byte[] data) {
		super(04);
		String[] dataArr = lireData(data).split(",");
		this.username = dataArr[0];
		this.left = Boolean.parseBoolean(dataArr[1]);
		this.right = Boolean.parseBoolean(dataArr[2]);
		this.jump = Boolean.parseBoolean(dataArr[3]);
	}
	public Packet04Direction(String username,  boolean left, boolean right, boolean jump) {
		super(04);
		this.username = username;
		this.left = left;
		this.right = right;
		this.jump = jump;
	}
	@Override
	public byte[] getData() {
		return ("04"+ this.username+","+this.left+","+this.right+","+this.jump).getBytes();
	}

	@Override
	public void writeData(ClientJoueur joueur) {
		joueur.envoieData(getData());
		
	}

	@Override
	public void writeData(ServeurCentral serveur) {
		serveur.envoieDataToJoueurs(getData());
		
	}
	
	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	
	public boolean isJump() {
		return jump;
	}
	public String getUserName() {
		return username;
	}
	
}

