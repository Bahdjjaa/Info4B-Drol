package modesjeu;

import static utils.Constantes.Environment.BIG_CLOUD_HEIGHT;
import static utils.Constantes.Environment.BIG_CLOUD_WIDTH;
import static utils.Constantes.Environment.SMALL_CLOUD_HEIGHT;
import static utils.Constantes.Environment.SMALL_CLOUD_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Network.ClientJoueur;
import Network.ServeurCentral;
import entities.Joueur;
import gamestates.Playing;
import main.Game;

public class Cooperatif extends Mode implements Modemethods{
	
	private Joueur joueur;

    
    public Cooperatif(Game game, Playing playing) {
		super(game, playing);
		initClasses();
	}



	private void initClasses() {
		this.joueur = new Joueur(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), playing);
		this.joueur.loadLvlData(this.levelManager.getCurrentLevel().getLevelData());
	}
	
	
	
	public Joueur getJoueur() {
		return this.joueur;
	}

	
	public void loadNextLevel() {
		resetAll();
    	levelManager.loadNextLevel();
	}

	@Override
	public void update() {
		if(paused) {
			pauseOverlay.update();
		}else if(levelCompleted) {
			levelCompletedOverlay.update();
		}else if(gameOver){
			gameOverOverlay.update();
		}else if (joueur.estMort()) {
			joueur.update();
		}else{
			this.levelManager.update();
			this.joueur.update();
			this.enemyManager.update(this.levelManager.getCurrentLevel().getLevelData(), joueur);
			this.equipageManager.update(this.levelManager.getCurrentLevel().getLevelData());
			this.objetsManager.update();
			checkCloseToBorder();			
		}
	}
	
	private void checkCloseToBorder() {
			int playerX = (int)joueur.getHitbox().x;
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
	
	public void windowFocusLost() {
		joueur.resetDirBooleans();
		
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		levelCompleted = false;
		joueur.resetAll();
		enemyManager.resetAllEnemies();
		equipageManager.resetAllEquipage();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		drawClouds(g);
		this.levelManager.draw(g, xLvlOffset);
		joueur.render(g, xLvlOffset);
		this.objetsManager.draw(g, xLvlOffset);
		this.enemyManager.draw(g, xLvlOffset);
		this.equipageManager.draw(g, xLvlOffset);
		
		
		if(paused) {
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
			this.pauseOverlay.draw(g);	
		}else if(gameOver) {
			gameOverOverlay.draw(g);
		}else if(levelCompleted) {
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
			levelCompletedOverlay.draw(g);
		}
		
	}
	
	private void drawClouds(Graphics g) {
		for(int i = 0 ; i < 3; i++) {
			g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int)(xLvlOffset * 0.3), (int)(204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		}
		for(int i = 0 ; i < smallCloudsPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
		
	}
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if(paused)
		    	pauseOverlay.mouseDragged(e);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(!gameOver) {
			if(e.getButton() == MouseEvent.BUTTON1)
				joueur.setAttack(true);
		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mousePressed(e);
			else if(levelCompleted)
				levelCompletedOverlay.mousePressed(e);
		}else {
			gameOverOverlay.mousePressed(e);
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseReleased(e);
			else if(levelCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}else {
			gameOverOverlay.mouseReleased(e);
		}
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseMoved(e);
			else if(levelCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}else {
			gameOverOverlay.mouseMoved(e);
		}
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverlay.keyPressed(e);
		else {
			switch(e.getKeyCode()){
			
            case KeyEvent.VK_LEFT:
                joueur.setLeft(true);
                break;
                
            case KeyEvent.VK_RIGHT:
            	joueur.setRight(true);
                break;
                
            case KeyEvent.VK_SPACE:
            	joueur.setJump(true);
            	break;
            case KeyEvent.VK_ESCAPE:
            	paused = !paused;
            	break;

			}
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver) {
			switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT: 
            	joueur.setLeft(false);
                break;
                
            case KeyEvent.VK_RIGHT:  
            	joueur.setRight(false);
                break;
                
            case KeyEvent.VK_SPACE:
            	joueur.setJump(false);
                break;
			}
		}
		
	}
	
	public void setJoueurMort(boolean mort) {
		this.joueur.setMort(mort);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
