package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Network.ServeurCentral;
import Niveaux.LevelManager;
import entities.Enemy;
import entities.EnemyManager;
import entities.EquipageManager;
import entities.Joueur;
import main.Game;
import modesjeu.Combat;
import modesjeu.Cooperatif;
import modesjeu.Mode;
import modesjeu.Modejeu;
import modesjeu.Solo;
import objets.ObjetsManager;
import objets.Porte;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;

import utils.LoadSave;
import static utils.Constantes.Environment.*;

public class Playing extends state implements Statemethods {
	 
	    //Modes jeu
	    private Solo solo;
	    private Cooperatif cooperatif;
	    private Combat combat;
	    private ServeurCentral server;
	   
	   
	    
	    public Playing(Game game) {
	 		super(game);
	 		initClasses();

	 	}
	 

		private void initClasses() {
      	    solo = new Solo(game, this);
      	    cooperatif = new Cooperatif(game, this);
      	    combat = new Combat(game, this);
      	    server = new ServeurCentral(1234, 2);
	       
	    }
		
		public void loadNextLevel() {
			switch(Modejeu.mode) {
			case SOLO:
				solo.loadNextLevel();
			default:
				break;
			}
		}
		
		public Mode getModejeu() {
			switch(Modejeu.mode) {
			case SOLO:
				return solo;
			default:
				return null;
			}
		}
		
		public Joueur getJoueur() {
			switch(Modejeu.mode) {
			case SOLO:
				return solo.getJoueur();
			default:
				break;
			}
			return null;
		}
		
		public EnemyManager getEnemyManager() {
			switch(Modejeu.mode) {
			case SOLO:
				return solo.getEnemyManager();
			default:
				break;
			}
			return null;
		}
		    
		public EquipageManager getEquipageManager() {
			switch(Modejeu.mode) {
			case SOLO:
				return solo.getEquipageManager();
			default:
				break;
			}
			return null;
		}
		    
		public LevelManager getLevelManager() {
			switch(Modejeu.mode) {
			case SOLO:
				return solo.getLevelManager();
			default:
				break;
			}
			return null;
		}
		public ObjetsManager getObjetsManager() {
			switch(Modejeu.mode) {
			case SOLO:
				return solo.getObjetsManager();
			default:
				break;
			}
			return null;
		 }
		
		public void windowFocusLost() {
			switch(Modejeu.mode) {
			case SOLO:
				solo.windowFocusLost();
				break;
			default:
				break;
			}
			
		}
		
		public void resetAll() {
			switch(Modejeu.mode) {
			case SOLO:
				solo.resetAll();
				break;
			default:
				break;
			}
		}
		
		public void setLevelCompleted(boolean b) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.setLevelCompleted(b);
				break;
			default:
				break;
			}
		}
		
		public void setGameOver(boolean gameOver) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.setGameOver(gameOver);
				break;
			default:
				break;
			}
		}
		
		public void setJoueurMort(boolean mort) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.setJoueurMort(mort);
				break;
			default:
				break;
			}
		}
		
		public void checkEnemyHit(Rectangle2D.Float attackBox) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.checkEnemyHit(attackBox);
				break;
			default:
				break;
			}
		}
		
		public void checkMemberRescue(Rectangle2D.Float hitbox) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.checkMemberRescue(hitbox);
				break;
			default:
				break;
			}
		}
		
		public void checkPorteTouche(Rectangle2D.Float hitBox) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.checkPorteTouche(hitBox);
				break;
			default:
				break;
			}
			
		}
		
		public void setMaxLvlOffset(int lvlOffset) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.setMaxLvlOffset(lvlOffset);
				break;
			default:
				break;
			}
		}
		
		public void unpauseGame() {
			switch(Modejeu.mode) {
			case SOLO:
				solo.unpauseGame();
				break;
			default:
				break;
			}
		}
		
	    

		@Override
		public void update() {
			switch(Modejeu.mode){
			case SOLO:
				solo.update();
				break;
			case COOPERATIF:
				if(!server.isGameStarted()) {
					server.acceptJoueurs();
				}
				cooperatif.update();
				break;
			default:
				break;
			}
			
		}


		@Override
		public void draw(Graphics g) {
			switch(Modejeu.mode) {
			case SOLO:
				solo.draw(g);
				break;
			case COOPERATIF:
				cooperatif.draw(g);
			default:
				break;
			}
			
		}
		
		public void mouseDragged(MouseEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.mouseDragged(e);
				break;
			default:
				break;
			}
		}
	
		
		@Override
		public void mouseClicked(MouseEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.mouseClicked(e);
				break;
			default:
				break;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.mousePressed(e);
				break;
			default:
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.mouseReleased(e);
			default:
				break;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.mouseMoved(e);
				break;
			default:
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.keyPressed(e);
				break;
			default:
				break;
			}
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (Modejeu.mode) {
			case SOLO:
				solo.keyReleased(e);
				break;
			default:
				break;
			}
			
		}
	
		
		

}
