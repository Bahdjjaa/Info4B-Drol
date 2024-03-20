/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

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
        this.jframe.setLocationRelativeTo(null);
        this.jframe.setResizable(false);
        this.jframe.pack();
        this.jframe.setVisible(true);
    }
}
