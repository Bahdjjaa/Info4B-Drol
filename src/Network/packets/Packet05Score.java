package Network.packets;

import Network.ClientJoueur;
import Network.ServeurCentral;

public class Packet05Score extends Packet {
	
	private String username;
	private int score;
	
	public Packet05Score(byte[] data) {
		super(05);
		String[] dataArr = lireData(data).split(",");
		this.username = dataArr[0];
		this.score = Integer.parseInt(dataArr[1]);
	}
	
	public Packet05Score(String username, int score) {
		super(05);
		this.username = username;
		this.score = score;
	}
	@Override
	public byte[] getData() {
		return ("05"+ this.username+","+this.score).getBytes();

	}
	@Override
	public void writeData(ClientJoueur joueur) {
		joueur.envoieData(getData());
		
	}
	@Override
	public void writeData(ServeurCentral serveur) {
		serveur.envoieDataToJoueurs(getData());
		
	}
	public String getUserName() {
		return username;
	}
	public int getScore() {
		return score;
	}
	
}
