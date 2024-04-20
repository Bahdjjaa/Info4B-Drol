package modesjeu;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import Niveaux.LevelManager;
import entities.EnemyManager;
import entities.Entity;
import entities.EquipageManager;
import entities.Joueur;
import gamestates.Playing;
import main.Game;
import objets.ObjetsManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.LoadSave;

public abstract class Mode{
	protected Game game;
	protected Playing playing;
	
	protected LevelManager levelManager;
	protected EnemyManager enemyManager;
	protected EquipageManager equipageManager;
	protected ObjetsManager objetsManager;
    
	protected PauseOverlay pauseOverlay;
	protected GameOverOverlay gameOverOverlay;
	protected LevelCompletedOverlay levelCompletedOverlay;

	      
	protected int xLvlOffset;
	protected int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
	protected int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
	protected int maxLvlOffsetX;
    
	protected BufferedImage backgroundImg, bigCloud, smallCloud;
	protected int[] smallCloudsPos;
	protected Random rnd = new Random();
    
	protected boolean gameOver;
	protected boolean levelCompleted = false;
	protected boolean paused = false;
	
	public Mode(Game game, Playing playing) {
		this.game = game;
		this.playing = playing;
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
	
	private void initClasses() {
		this.levelManager = new LevelManager(game);
		this.enemyManager = new EnemyManager(playing);
		this.equipageManager = new EquipageManager(playing);
		this.objetsManager = new ObjetsManager(playing);
    	
    	this.pauseOverlay = new PauseOverlay(playing);
    	this.gameOverOverlay = new GameOverOverlay(playing);
    	this.levelCompletedOverlay = new LevelCompletedOverlay(playing);
	}
	
	private void loadStartLevel() {
    	objetsManager.loadObjets(levelManager.getCurrentLevel());
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
		equipageManager.loadEquipage(levelManager.getCurrentLevel());
	}
	
	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
		
	}
	
	public Game getGame() {
		return game;
	}
	
    public EnemyManager getEnemyManager() {
    	return enemyManager;
    }
    
    public EquipageManager getEquipageManager() {
    	return equipageManager;
    }
    
    public LevelManager getLevelManager() {
    	return levelManager;
    }
    public ObjetsManager getObjetsManager() {
    	return objetsManager;
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
    
    public void setGameOver(boolean gameOver) {
		this.gameOver= gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.enemyHit(attackBox);
	}
	
	public void checkMemberRescue(Rectangle2D.Float hitbox) {
		equipageManager.isRescued(hitbox);
	}
	
	public void checkPorteTouche(Rectangle2D.Float hitBox) {
		enemyManager.enemyOut(hitBox);
		
	}
	
}
