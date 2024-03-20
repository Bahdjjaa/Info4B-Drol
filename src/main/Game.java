/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entities.Joueur;
import java.awt.Graphics;

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
    
    private Joueur joueur;
            
            
    public Game(){
        initClasses();
        
        this.gamePanel = new GamePanel(this);
        this.gwindow = new Window(this.gamePanel);
        this.gamePanel.requestFocus();
        
        this.startGameLoop();
      
    }
    
    private void startGameLoop(){
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }
    
    private void update() {
        joueur.update();
    }
    
    public void render(Graphics g){
        joueur.render(g);
    }
    
    private void initClasses() {
       joueur = new Joueur(200, 200);
    }
    
    public Joueur getJoueur(){
        return this.joueur;
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
            
            //To get the lost time (mostly in miliseconds) between each two updates
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
                System.out.println("FPS: "+ frames + " UPS: "+ updates);
                frames = 0;
                updates = 0;
            }     
        }
    }

    
}