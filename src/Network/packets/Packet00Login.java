package Network.packets;

import Network.ClientJoueur;
import Network.ServeurCentral;

public class Packet00Login extends Packet {

	private String username;
	
	public Packet00Login(byte[] data) {
		super(00);
		this.username = lireData(data);
	}
	
	public Packet00Login(String username) {
		super(00);
		this.username = username;
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
		return ("00"+ this.username).getBytes();
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return username;
	}
	
}
