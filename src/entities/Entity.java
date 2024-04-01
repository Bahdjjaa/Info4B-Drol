/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

/**
 *
 * @author bahdjjaa
 */
public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int tick, index;
    protected int etat;
    protected float vitesseAir;
    protected boolean inAir = false;
    protected int maxVie;
	protected int vie;
	protected Rectangle2D.Float attackBox;
	protected float vitesseMarche;
    
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x,y,(int)(width * Game.SCALE),(int)( height * Game.SCALE));
	}
	
	
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		//For debugging the hitbox
		g.setColor(Color.pink);
		g.drawRect((int)this.hitbox.x - xLvlOffset,(int)this.hitbox.y,(int)this.hitbox.width, (int)this.hitbox.height);
	}
	
	protected void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(Color.pink);
		g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public int getEtat() {
		 return this.etat;
	 }
	
	 
	public int getAniIndex() {
		 return this.index;
	 }
	 
	 
	 
	
	
}
