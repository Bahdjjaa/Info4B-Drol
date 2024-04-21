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
import java.util.List;

import javax.swing.JOptionPane;

import Network.ClientJoueur;
import Network.ServeurCentral;
import Network.packets.Packet04Direction;
import entities.Joueur;
import entities.JoueurCooperatif;
import gamestates.Playing;
import main.Game;

public class Cooperatif extends Mode implements Modemethods{
	
	//private Joueur joueur;
	private ArrayList<JoueurCooperatif> joueurs = new ArrayList<JoueurCooperatif>();
    
    public Cooperatif(Game game, Playing playing) {
		super(game, playing);
	}

	
	public synchronized ArrayList<JoueurCooperatif> getJoueurs(){
		return joueurs;
	}

	public synchronized void ajoutJoueur(JoueurCooperatif joueur) {
		this.getJoueurs().add(joueur);
	}
	
	public synchronized int getJoueurIndex(String username) {
		int index = 0;
		for(JoueurCooperatif joueurs: joueurs) {
			if(joueurs.getUsername().equals(username)){
				break;
			}
			index++;
		}
		return index;
	}
	
	public synchronized void  moveJoueur(String username, float x, float y){
		int index = getJoueurIndex(username);
		JoueurCooperatif joueur = this.joueurs.get(index);
		joueur.getHitbox().x = x;
		joueur.getHitbox().y = y;
	   
	}
	
	public synchronized void setDirection(String username, boolean left, boolean right, boolean jump) {
			int index = getJoueurIndex(username);
			JoueurCooperatif joueur = this.joueurs.get(index);
			joueur.setLeft(left);
			joueur.setRight(right);
			joueur.setJump(jump);
		
	}
	
	public synchronized void setJoueurEnAttaque(String username, boolean attack) {
		int index = getJoueurIndex(username);
		this.joueurs.get(index).setAttack(attack);
	}
	
	
	/*public Joueur getJoueur() {
		return this.joueur;
	}*/
	
	public void loadNextLevel() {
		resetAll();
    	levelManager.loadNextLevel();
	}

	@Override
	public synchronized void update() {
		if(paused) {
			pauseOverlay.update();
		}else if(levelCompleted) {
			levelCompletedOverlay.update();
		}else if(gameOver){
			gameOverOverlay.update();
		}/*else if (joueur.estMort()) {
			joueur.update();
		}*/else{
			this.levelManager.update();
			//this.joueur.update();
			updateJoueurs();
			for(JoueurCooperatif jc : joueurs){
				if(jc.estJoueurLocal())
					this.enemyManager.update(this.levelManager.getCurrentLevel().getLevelData(), jc);
			}
			this.equipageManager.update(this.levelManager.getCurrentLevel().getLevelData());
			this.objetsManager.update();
			checkCloseToBorder();			
		}
	}
	
	private synchronized void updateJoueurs() {
		for(JoueurCooperatif jc : joueurs) {
			jc.update();
		}
	}
	
	private void checkCloseToBorder() {
		for(JoueurCooperatif jc : joueurs) {
			if(jc.estJoueurLocal()) {
				int playerX = (int)jc.getHitbox().x;
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
	}
	
	public void windowFocusLost() {
		//joueur.resetDirBooleans();
		for(JoueurCooperatif jc: joueurs)
				jc.resetDirBooleans();
		
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		levelCompleted = false;
		//joueur.resetAll();
		enemyManager.resetAllEnemies();
		equipageManager.resetAllEquipage();
		for(JoueurCooperatif jc : joueurs)
			jc.resetAll();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		drawClouds(g);
		this.levelManager.draw(g, xLvlOffset);
		renderJoueurs(g);
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
	
	private synchronized void renderJoueurs(Graphics g) {
		for(JoueurCooperatif jc : joueurs) {
				jc.render(g, xLvlOffset);
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
				for(JoueurCooperatif jc: joueurs)
					if(jc.estJoueurLocal())
						jc.setAttack(true);
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
			for(JoueurCooperatif jc: joueurs) {
				boolean envoieData = false;
				if(jc.estJoueurLocal()) {
					switch(e.getKeyCode()){
			
					case KeyEvent.VK_LEFT:
						jc.setLeft(true);
						envoieData = true;
						break;
                
					case KeyEvent.VK_RIGHT:
						jc.setRight(true);
						envoieData = true;
						break;
                
					case KeyEvent.VK_SPACE:
						jc.setJump(true);
						envoieData = true;
						break;
					case KeyEvent.VK_ESCAPE:
						paused = !paused;
						break;
					}
					if(envoieData) {
						Packet04Direction packet = new Packet04Direction(jc.getUsername(), jc.isLeft(), jc.isRight(), jc.isJump());
			        	packet.writeData(Game.game.getJoueurSocket());
			    		
					}
				}
			}
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver) {
			for(JoueurCooperatif jc: joueurs) {
				boolean envoieData = false;
					switch(e.getKeyCode()){
		            case KeyEvent.VK_LEFT: 
		            		jc.setLeft(false);
		            		envoieData = true;
		                break;
		                
		            case KeyEvent.VK_RIGHT:  
		            		jc.setRight(false);
		            		envoieData = true;
		                break;
		                
		            case KeyEvent.VK_SPACE:
		            		jc.setJump(false);
		            		envoieData = true;
		                break;
					}
					
					if(envoieData) {
						Packet04Direction packet = new Packet04Direction(jc.getUsername(), jc.isLeft(), jc.isRight(), jc.isJump());
			        	packet.writeData(Game.game.getJoueurSocket());
					}
			}
			
		}
		
	}
	
	public void setJoueurMort(boolean mort) {
		for(JoueurCooperatif jc :  joueurs)
			if(jc.estJoueurLocal())
				jc.setMort(mort);
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
