package entities;

import java.net.InetAddress;

import gamestates.Playing;

public class JoueurCooperatif extends Joueur  {
	
	public InetAddress ip;
	public int port;

	public JoueurCooperatif(float x, float y, int width, int height, Playing playing, InetAddress ip, int port) {
		super(x, y, width, height, playing);
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void update() {
		super.update();
	}

}
