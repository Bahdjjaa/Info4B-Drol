/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.OptionsJeu;
import gamestates.Playing;
import modesjeu.Cooperatif;
import modesjeu.Mode;
import modesjeu.Modejeu;
import ui.OptionsAudio;
import utils.LoadSave;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import Network.ClientJoueur;
import Network.ServeurCentral;
import audios.AudioManager;


/**
 *
 * @author bahdjjaa
 */
public class Game implements Runnable {
    
    private Window gwindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120; //Le nombre de frames dessinés par seconde
    private final int UPS_SET = 200;
    
    private Playing playing;
    private Menu menu;
    private OptionsJeu optsJeu;
    private OptionsAudio audioOptions;
    private AudioManager audioManager;
    
    private ClientJoueur joueurSocket;
    private ServeurCentral serveurSocket;
    
    private boolean running = false;
 
    
    public final static int TILES_DEFAULT_SIZE = 29;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 30; //Visible size but not the actual size of the level
    public final static int TILES_IN_HEIGHT = 17;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public static Game game;

    
    
    public Game(){
        initClasses();
        
        this.gamePanel = new GamePanel(this);
        this.gwindow = new Window(this.gamePanel);
        this.gamePanel.requestFocus();
        
        startGameLoop();
        
       
      
    }
    
    public synchronized void startGameLoop() {	
    	running = true;
    	this.gameThread = new Thread(this);
		this.gameThread.start();
    }
    

	public synchronized void stopGameLoop() {
		running = false;
    	try {
    		this.gameThread.join();
    	}catch(InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    private void update() {
        switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			if(running) {
				playing.update();
			}
			break;
		case OPTIONS:	
			optsJeu.update();
			break;
		case QUIT:		
		default:
			System.exit(0);
			break;
        	
        }
    }
    
    

	public void render(Graphics g){
    	switch(Gamestate.state) {
 		case MENU:
 			menu.draw(g);
 			break;
 		case PLAYING:
 			if(running)
				playing.draw(g);
 			break;
 		case OPTIONS:
 			optsJeu.draw(g);
 			break;
 		default:
 			break;	
         }
    }
    
    private void initClasses() {
    	this.game = this;
    	audioOptions = new OptionsAudio(this);
    	audioManager = new AudioManager();
    	menu = new Menu(this);
    	optsJeu = new OptionsJeu(this);
    	playing = new Playing(this);
    	
    }
   
    
    public Menu getMenu() {
    	return menu;
    }
    
    public Playing getPlaying() {
    	return playing;
    }
    
    public OptionsAudio getAudioOptions() {
    	return audioOptions;
    }
    public OptionsJeu getOptionsJeu() {
    	return optsJeu;
    }
    public AudioManager getAudioManager() {
    	return audioManager;
    }
    public GamePanel getGamePanel() {
    	return gamePanel;
    }
    
    public boolean getRunning() {
    	return running;
    }

    public ClientJoueur getJoueurSocket() {
		return joueurSocket;
	}
    public ServeurCentral getServeurSocket() {
		return serveurSocket;
	}

	public void setJoueurSocket(ClientJoueur joueurSocket) {
		this.joueurSocket = joueurSocket;
	}

	public void setServeurSocket(ServeurCentral serveurSocket) {
		this.serveurSocket = serveurSocket;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
    public void run() {
        
        double timePerFrame = 1000000000.0 / this.FPS_SET;
        double timePerUpdate = 1000000000.0 / this.UPS_SET;     
        
        long previousTime = System.nanoTime();
        
        int frames = 0;
        int updates = 0;
        
        long lastCheck = System.currentTimeMillis();
        
        double deltaU = 0;
        double deltaF = 0;
        
        //Game loop inside the thread
        while(true){
            //Get the current Time
            long currentTime = System.nanoTime();
            
            //To get the lost time (mostly in milliseconds) between each two updates
            deltaU += (currentTime - previousTime)/ timePerUpdate;
            
            deltaF += (currentTime - previousTime)/ timePerFrame;
            
            //update time
            previousTime = currentTime;
            
            //We check if the update take more time than it should take
            if(deltaU >= 1){
                //we make a new update
                update();
                updates++;
                deltaU--;
            }
            
            if(deltaF >= 1){
                this.gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                //System.out.println("FPS: "+ frames + " UPS: "+ updates);
                frames = 0;
                updates = 0;
            }     
        }
    }

	

    
}