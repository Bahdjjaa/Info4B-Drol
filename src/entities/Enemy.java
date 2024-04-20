package entities;

import static utils.Constantes.EnemyConstantes.*;
import static utils.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import gamestates.Playing;
import main.Game;
import modesjeu.Modejeu;
import objets.Porte;

import static utils.Constantes.Directions.*;
import static utils.Constantes.*;

public abstract class Enemy extends Entity {
	
	protected int typeEnemy;
	protected boolean firstUpdate = true;
	
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDist = Game.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	protected int nbInter;
	
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.typeEnemy = enemyType;
		maxVie = GetMaxHealth(enemyType);
		vie = maxVie;
		vitesseMarche = 0.35f * Game.SCALE;
		nbInter = 0;
	}
	
	
	
	protected void updateAnimationTick() {
	        this.tick++;
	        if(this.tick >=  VITESSE_ANIMATION){
	            this.tick = 0; 
	            this.index++;
	            if(this.index >= GetSpriteAmount(typeEnemy, this.etat)){
	                this.index = 0;
	                
	                switch(this.etat) {
	                case ATTACK, HIT -> this.etat = IDLE;
	                case DEAD -> active = false;
	                }
	                
	            }
	        }

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
	 
	protected void move(int[][] lvlData) {
		 float xSpeed = 0;
		 /*if(Modejeu.mode == Modejeu.COOPERATIF || Modejeu.mode == Modejeu.COMBAT) {
			 vitesseMarche = 0.175f * Game.SCALE;
		 }*/
	 		
	 		if(walkDir == LEFT) {
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



	protected boolean canSeePlayer(int[][] lvlData, Joueur joueur) {
		int joueurTileY =(int)(joueur.getHitbox().y / Game.TILES_SIZE);
		if(joueurTileY == tileY)
			if(isJoueurInRange(joueur))
				if(IsSightClear(lvlData, hitbox, joueur.hitbox, tileY))
					return true;
		
		return false;
	}
	 
	 protected void turnTowardsPlayer(Joueur joueur) {
		 if(joueur.hitbox.x  > hitbox.x)
			 walkDir = RIGHT;
		 else
			 walkDir = LEFT;
	 }
	 
	 
	protected boolean isJoueurInRange(Joueur joueur) {
		int absValue =(int) Math.abs(joueur.hitbox.x - hitbox.x);
		return absValue <= attackDist * 5;
	}
	
	protected boolean isPlayercloseForAttack(Joueur joueur) {
		int absValue =(int) Math.abs(joueur.hitbox.x - hitbox.x);
		return absValue <= attackDist;
	}
	
	
	protected void newState(int enemyState) {
		this.etat = enemyState;
		tick = 0 ;
		index = 0;
	}
	
	public void hurt(int amount) {
		vie -= amount; //Modified this too
		if(vie <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}
	
	public void out() {
		nbInter++;
		if(nbInter >= 200)
			active = false;
	}
	
	protected void checkEnemyHit(Rectangle2D.Float attackBox, Joueur j) {
		if(attackBox.intersects(j.hitbox))
			j.changeHealth(-GetEnemyDamage(typeEnemy));
		attackChecked = true;
	}
	
	 
	 private void chanegWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	 public boolean isActive() {
		 return active;
	 }
	 
}
