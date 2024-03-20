/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import static utils.Constent.Directions.*;

/**
 *
 * @author bahdjjaa
 */
public class KeyboardInputs implements KeyListener{
    
    private GamePanel gamePanel;
    
    public KeyboardInputs(GamePanel gp){
        this.gamePanel = gp;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()){
            case KeyEvent.VK_A:
                this.gamePanel.getGame().getJoueur().setDirection(UP);
                break;
            case KeyEvent.VK_S:
                this.gamePanel.getGame().getJoueur().setDirection(LEFT);
                break;
            case KeyEvent.VK_D:
                this.gamePanel.getGame().getJoueur().setDirection(RIGHT);
                break;
            case KeyEvent.VK_W:
                this.gamePanel.getGame().getJoueur().setDirection(DOWN);
                break;
        
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
         switch(ke.getKeyCode()){
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:     
            case KeyEvent.VK_D:  
            case KeyEvent.VK_W:
                this.gamePanel.getGame().getJoueur().setMoving(false);
                break;
        
        }
    }
    
    
}
