package Network.packets;

import Network.ClientJoueur;
import Network.ServeurCentral;

public class Packet03Attack extends Packet {
	
	private String username;
	private boolean attack;

	public Packet03Attack(byte[] data) {
		super(03);
		String[] dataArr = lireData(data).split(",");
		this.username = dataArr[0];
		this.attack = Boolean.parseBoolean(dataArr[1]);
	}
	
	public Packet03Attack(String username,boolean attack) {
		super(03);
		this.username = username;
		this.attack = attack;
	}

	@Override
	public byte[] getData() {
		return ("03" + this.username + ","+this.attack).getBytes();
	}

	@Override
	public void writeData(ClientJoueur joueur) {
		joueur.envoieData(getData());
		
	}

	@Override
	public void writeData(ServeurCentral serveur) {
		serveur.envoieDataToJoueurs(getData());
		
	}

	public boolean getAttack() {
		return attack;
	}

	public String getUserName() {
		return username;
	}

}
