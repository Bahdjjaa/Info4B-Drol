/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
         //System.out.println("Mouse Clicked");
         //this.gamepanel.spawnrect(me.getX(), me.getY());
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }
    
}
