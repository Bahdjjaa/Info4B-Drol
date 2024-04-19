package objets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

import static utils.Constantes.VITESSE_ANIMATION;
import static utils.Constantes.ObjetsConstantes.*;

public class Objets {
	
	protected int x, y, type;
	protected Rectangle2D.Float hitbox;
	protected boolean animate, active;
	protected int tick, index;
	protected int xDrawOffset, yDrawOffset;

	public Objets(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.type = objType;
	}
	
	public void updateAniTick() {
		tick++;
		if(tick >= VITESSE_ANIMATION) {
			tick = 0;
			index++;
			if(index >= GetSpriteAmount(type)) {
				index = 0;
				
			}
		}
			
	}
	
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x,y,(int)(width * Game.SCALE),(int)( height * Game.SCALE));
	}
	
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		//For debugging the hitbox
		g.setColor(Color.pink);
		g.drawRect((int)this.hitbox.x - xLvlOffset,(int)this.hitbox.y,(int)this.hitbox.width, (int)this.hitbox.height);
	}
	
	
	public int getTypeObj() {
		return type;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getIndex() {
		return index;
	}

	
	
}
