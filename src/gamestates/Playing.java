package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Niveaux.LevelManager;
import entities.EnemyManager;
import entities.EquipageManager;
import entities.Joueur;
import main.Game;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;

import utils.LoadSave;
import static utils.Constantes.Environment.*;

public class Playing extends state implements Statemethods {
	 	
		private Joueur joueur;
	    private LevelManager levelManager;
	    private EnemyManager enemyManager;
	    private EquipageManager equipageManager;
	    private PauseOverlay pauseOverlay;
	    private GameOverOverlay gameOverOverlay;
	    private LevelCompletedOverlay levelCompletedOverlay;
	    private boolean paused = false;
	    
	    private int xLvlOffset;
	    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
	    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
	    private int maxLvlOffsetX;
	    
	    private BufferedImage backgroundImg, bigCloud, smallCloud;
	    private int[] smallCloudsPos;
	    private Random rnd = new Random();
	    
	    private boolean gameOver;
	    private boolean levelCompleted = false;
	    
	    public Playing(Game game) {
	 		super(game);
	 		initClasses();
	 		
	 		this.backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
	 		this.bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
	 		this.smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
	 		this.smallCloudsPos = new int[8];
	 		for(int i = 0 ; i < smallCloudsPos.length; i++)
	 			smallCloudsPos[i] = (int)(90 * Game.SCALE) + rnd.nextInt((int)(100 * Game.SCALE));
	 		
	 		calcLvlOffset();
	 		loadStartLevel();
	 		
	 	}
	    public void loadNextLevel() {
	    	resetAll();
	    	levelManager.loadNextLevel();
	    }
	    
	    private void loadStartLevel() {
			enemyManager.loadEnemies(levelManager.getCurrentLevel());
			equipageManager.loadEquipage(levelManager.getCurrentLevel());
			
		}

		private void calcLvlOffset() {
			maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
			
		}

		private void initClasses() {
      	    this.levelManager = new LevelManager(game);
      	    this.enemyManager = new EnemyManager(this);
      	    this.equipageManager = new EquipageManager(this);
	    	this.joueur = new Joueur(200, 200, (int)(64 * Game.SCALE), (int)(40* Game.SCALE), this);
	    	this.joueur.loadLvlData(this.levelManager.getCurrentLevel().getLevelData());
	    	this.pauseOverlay = new PauseOverlay(this);
	    	this.gameOverOverlay = new GameOverOverlay(this);
	    	this.levelCompletedOverlay = new LevelCompletedOverlay(this);
	       
	    }
	    
	    public Joueur getJoueur(){
	        return this.joueur;
	    }
	    
	    public EnemyManager getEnemyManager() {
	    	return enemyManager;
	    }
	    
	    public EquipageManager getEquipageManager() {
	    	return equipageManager;
	    }
	    
	    public void windowFocusLost() {
			joueur.resetDirBooleans();
			
		}
	    
	    public void setMaxLvlOffset(int lvlOffset) {
	    	this.maxLvlOffsetX = lvlOffset;
	    }
	    
	    public void setLevelCompleted(boolean b) {
			this.levelCompleted = b;
			
		}
	    public void unpauseGame() {
	    	this.paused = false;
	    }

		@Override
		public void update() {
			
			if(paused) {
				pauseOverlay.update();
			}else if(levelCompleted) {
				levelCompletedOverlay.update();
			}else if(!gameOver) {
				this.levelManager.update();
				this.joueur.update();
				this.enemyManager.update(this.levelManager.getCurrentLevel().getLevelData(), joueur);
				this.equipageManager.update(this.levelManager.getCurrentLevel().getLevelData());
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

		@Override
		public void draw(Graphics g) {
			g.drawImage(backgroundImg, 0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			drawClouds(g);
			this.levelManager.draw(g, xLvlOffset);
			this.joueur.render(g, xLvlOffset);
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
		

		public void resetAll() {
			gameOver = false;
			paused = false;
			levelCompleted = false;
			joueur.resetAll();
			enemyManager.resetAllEnemies();
			equipageManager.resetAllEquipage();
			
		}
		
		
		public void setGameOver(boolean gameOver) {
			this.gameOver= gameOver;
		}
		
		public void checkEnemyHit(Rectangle2D.Float attackBox) {
			enemyManager.enemyHit(attackBox);
		}
		
		public void checkMemberRescue(Rectangle2D.Float hitbox) {
			equipageManager.isRescued(hitbox);
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
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(!gameOver) {
				if(paused)
					pauseOverlay.mouseReleased(e);
				else if(levelCompleted)
					levelCompletedOverlay.mouseReleased(e);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(!gameOver) {
				if(paused)
					pauseOverlay.mouseMoved(e);
				else if(levelCompleted)
					levelCompletedOverlay.mouseMoved(e);
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
		

}
