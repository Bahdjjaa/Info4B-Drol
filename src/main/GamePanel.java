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
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    
}
