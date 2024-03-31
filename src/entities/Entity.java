/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author bahdjjaa
 */
public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

	protected void initHitbox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x,y,width, height);
	}
	
//	protected void updateHitbox() {
//		hitbox.x = (int)x;
//		hitbox.y = (int)y;
//	}
	
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		//For debugging the hitbox
		g.setColor(Color.pink);
		g.drawRect((int)this.hitbox.x - xLvlOffset,(int)this.hitbox.y,(int)this.hitbox.width, (int)this.hitbox.height);
	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	
}
