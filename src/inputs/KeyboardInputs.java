/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;
import static utils.Constantes.Directions.*;

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
    	switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(ke);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(ke);
			break;
		case OPTIONS:
			gamePanel.getGame().getOptionsJeu().keyReleased(ke);
			break;
		default:
			break;
         
         }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(ke);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(ke);
			break;
		default:
			break;
         
         }
    }
    
    
}
