/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

/**
 *
 * @author bahdjjaa
 */
public class GamePanel extends JPanel{
    
    private MouseInputs mouseInputs;
    private Game game;
   
  
   
        
    public GamePanel(Game game){    
       mouseInputs = new MouseInputs(this);
       this.game = game;
       
       setPanelSize();
       addKeyListener(new KeyboardInputs(this));
       addMouseListener(mouseInputs);
       addMouseMotionListener(mouseInputs);  

    }
    
     public Game getGame(){
        return this.game;
    }

  
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.game.render(g);
        
      
    }


    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : "+ GAME_WIDTH + " : "+ GAME_HEIGHT);
       
    }
    
}
