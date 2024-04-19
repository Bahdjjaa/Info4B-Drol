package modesjeu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Network.ServeurCentral;
import entities.Joueur;
import gamestates.Playing;
import main.Game;

public class Cooperatif extends Mode implements Modemethods{
	
	private ArrayList<Joueur> joueurs;
	private ServeurCentral server;
	
	
	public Cooperatif(Game game, Playing playing) {
		super(game, playing);
		initJoueurs();
	}


	private void initJoueurs() {
		float x = 200;
		float y = 175;
		this.joueurs = new ArrayList<Joueur>();
		for(Joueur j : joueurs) {
			j = new Joueur(x, y, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), playing);
			x += 10;
		}
	}
	
	public Joueur getJoueur(int index) {
		return this.joueurs.get(index);
	}

	
	public ArrayList<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public void loadNextLevel() {
		
	}


	@Override
	public void update() {
		if(paused) {
			pauseOverlay.update();
		}else if(levelCompleted) {
			levelCompletedOverlay.update();
		}else if(gameOver){
			gameOverOverlay.update();
		}else{
			this.levelManager.update();
			updateJoueurs();
			this.enemyManager.update(this.levelManager.getCurrentLevel().getLevelData(), getJoueur(0));
			this.equipageManager.update(this.levelManager.getCurrentLevel().getLevelData());
			this.objetsManager.update();
			checkCloseToBorder();			
		}
	}
	
	private void checkCloseToBorder() {
		for(Joueur j : joueurs) {
			int playerX = (int)j.getHitbox().x;
			int diff = playerX - xLvlOffset;
			
			if(diff > rightBorder)
				xLvlOffset += diff - rightBorder;
			
			else if (diff < leftBorder)
				xLvlOffset += diff - leftBorder;
			
			if(xLvlOffset > maxLvlOffsetX)
				xLvlOffset = maxLvlOffsetX;
			
			else if(xLvlOffset < 0)
				xLvlOffset = 0;
		}
		
			
	}

	private void updateJoueurs() {
		for(Joueur j: joueurs) {
			if(j.estMort())
				j.update();
			else
				j.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
