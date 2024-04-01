package entities;


import main.Game;

import static utils.Constantes.GRAVITE;
import static utils.Constantes.VITESSE_ANIMATION;
import static utils.Constantes.Directions.*;
import static utils.Constantes.EquipageConstantes.GetSpriteAmount;
import static utils.HelpMethods.CanMoveHere;
import static utils.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utils.HelpMethods.IsEntityOnFloor;
import static utils.HelpMethods.IsFloor;

import java.awt.geom.Rectangle2D;

public abstract class Equipage extends Entity{
	
	protected int type;
	protected boolean firstUpdate = true;
	protected int dirMarche = LEFT;
	protected boolean active = true;
	protected float xDrawOffset;
	protected float yDrawOffset;
	protected int tileY;
	
	

	public Equipage(float x, float y, int width, int height, int type) {
		super(x, y, width, height);
		this.type = type;
		vitesseMarche = 0.3f * Game.SCALE;
	}
	
	public void reset() {
		index = 0;
		tick = 0;
		active = true;
	}
	
	protected void updateAnimationTick() {
        this.tick++;
        if(this.tick >=  VITESSE_ANIMATION){
            this.tick = 0; 
            this.index++;
            if(this.index >= GetSpriteAmount(type)){
                this.index = 0;  
            }
        }
	}

	public int getType() {
		return type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getxDrawOffset() {
		return xDrawOffset;
	}

	
	public float getyDrawOffset() {
		return yDrawOffset;
	}
	
	
	protected void mouvement(int[][] lvlData) {
		float xSpeed = 0;
 		
 		if(dirMarche == LEFT) {
 			xSpeed = -vitesseMarche;
 		}else {
 			xSpeed = vitesseMarche;
 		}
 		
 		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
 			if(IsFloor(hitbox, xSpeed, lvlData)) {
 				hitbox.x += xSpeed;
 				return;
 			}
 		
 		chanegWalkDir();
	}
	
	private void chanegWalkDir() {
		if(dirMarche == LEFT)
			dirMarche = RIGHT;
		else
			dirMarche = LEFT;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		 if(!IsEntityOnFloor(hitbox, lvlData))
			 inAir = true;
		 firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		 if(CanMoveHere(hitbox.x, hitbox.y + vitesseAir, hitbox.width, hitbox.height, lvlData)) {
			 hitbox.y += vitesseAir;
			 vitesseAir += GRAVITE;
		 }else {
			 inAir = false;
			 hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, vitesseAir);
			 tileY = (int)(hitbox.y / Game.TILES_SIZE);
		 }
	}
	
	



	
	
}
