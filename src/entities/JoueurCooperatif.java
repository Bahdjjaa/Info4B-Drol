package entities;

import java.net.InetAddress;

import Network.ClientJoueur;
import gamestates.Playing;

public class JoueurCooperatif extends Joueur  {
	
	public InetAddress ip;
	public int port;
	//private ClientJoueur client;

	public JoueurCooperatif(float x, float y, int width, int height,String username,Playing playing, InetAddress ip, int port) {
		super(x, y, width, height,username, playing);
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void update() {
		super.update();
	}

}
