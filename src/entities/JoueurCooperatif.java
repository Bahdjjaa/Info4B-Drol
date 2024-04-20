package entities;

import java.net.InetAddress;

import gamestates.Playing;

public class JoueurCooperatif extends Joueur  {
	
	public InetAddress ip;
	public int port;
	private boolean estJoueurLocal;

	public JoueurCooperatif(float x, float y, int width, int height,String username,Playing playing, InetAddress ip, int port) {
		super(x, y, width, height,username, playing);
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void update() {
		super.update();
	}

	public boolean estJoueurLocal() {
		return estJoueurLocal;
	}

	public void setJoueurLocal(boolean estJoueurLocal) {
		this.estJoueurLocal = estJoueurLocal;
	}

}
