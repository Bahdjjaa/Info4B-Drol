/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.GamePanel;


/**
 *
 * @author bahdjjaa
 */
public class MouseInputs implements MouseListener, MouseMotionListener{

    private GamePanel gamepanel;
        
    public MouseInputs(GamePanel gp){
        this.gamepanel = gp;
    }   
    @Override
    public void mouseClicked(MouseEvent me) {
    	switch(Gamestate.state) {
    	case PLAYING:
    		gamepanel.getGame().getPlaying().mouseClicked(me);
    		break;
    	case OPTIONS:
    		gamepanel.getGame().getOptionsJeu().mouseClicked(me);
    	default:
    		break;
    		
    	}
    }

    @Override
    public void mousePressed(MouseEvent me) {
    	switch(Gamestate.state) {
    	case MENU:
    		gamepanel.getGame().getMenu().mousePressed(me);
    		break;
    	case PLAYING:
    		gamepanel.getGame().getPlaying().mousePressed(me);
    		break;
    	case OPTIONS:
    		gamepanel.getGame().getOptionsJeu().mousePressed(me);
    		break;
    	default:
    		break;
    		
    	}
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    	switch(Gamestate.state) {
    	case MENU:
    		gamepanel.getGame().getMenu().mouseReleased(me);
    		break;
    	case PLAYING:
    		gamepanel.getGame().getPlaying().mouseReleased(me);
    		break;
    	case OPTIONS:
    		gamepanel.getGame().getOptionsJeu().mouseReleased(me);
    		break;
    	default:
    		break;
    		
    	}
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    	switch(Gamestate.state) {
    	case PLAYING:
    		gamepanel.getGame().getPlaying().mouseDragged(me);
    		break;
    	case OPTIONS:
    		gamepanel.getGame().getOptionsJeu().mouseDragged(me);
    		break;
    	default:
    		break;
    		
    	}
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    	switch(Gamestate.state) {
    	case MENU:
    		gamepanel.getGame().getMenu().mouseMoved(me);
    		break;
    	case PLAYING:
    		gamepanel.getGame().getPlaying().mouseMoved(me);
    		break;
    	case OPTIONS:
    		gamepanel.getGame().getOptionsJeu().mouseMoved(me);
    		break;
    	default:
    		break;
    		
    	}
    }
    
}
