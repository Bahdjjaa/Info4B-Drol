/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/**
 *
 * @author bahdjjaa
 */
public class Window{
    private JFrame jframe;
    
    public Window(GamePanel gp){
        this.jframe = new JFrame();    
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.add(gp);  
        this.jframe.setResizable(false);
        this.jframe.pack();
        this.jframe.setLocationRelativeTo(null);
        this.jframe.setVisible(true);
        this.jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
					
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				gp.getGame().getPlaying().windowFocusLost();
				
			}
        	
        });
        
        
    }
}
