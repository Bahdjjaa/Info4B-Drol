package entities;

import static utils.Constantes.EnemyConstantes.*;
import static utils.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import main.Game;

import static utils.Constantes.Directions.*;

public abstract class Enemy extends Entity {
	
	protected int index, enemyState, enemyType;
	protected int tick, speed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed;
	protected float gravity = 0.04f * Game.SCALE;
	protected float walkSpeed = 0.35f * Game.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDist = Game.TILES_SIZE;
	protected int maxHealth;
	protected int currentHealth;
	protected boolean active = true;
	protected boolean attackChecked;
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x, y, width, height);
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}
	
	
	
	protected void updateAnimationTick() {
	        this.tick++;
	        if(this.tick >= this.speed){
	            this.tick = 0; 
	            this.index++;
	            if(this.index >= GetSpriteAmount(enemyType, enemyState)){
	                this.index = 0;
	                
	                switch(enemyState) {
	                case ATTACK, HIT -> enemyState = IDLE;
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
		 if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			 hitbox.y += fallSpeed;
			 fallSpeed += gravity;
		 }else {
			 inAir = false;
			 hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			 tileY = (int)(hitbox.y / Game.TILES_SIZE);
		 }
	}
	 
	protected void move(int[][] lvlData) {
		 float xSpeed = 0;
	 		
	 		if(walkDir == LEFT) {
	 			xSpeed = -walkSpeed;
	 		}else {
	 			xSpeed = walkSpeed;
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
		this.enemyState = enemyState;
		tick = 0 ;
		index = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount; //Modified this too
		if(currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}
	
	protected void checkEnemyHit(Rectangle2D.Float attackBox, Joueur j) {
		if(attackBox.intersects(j.hitbox))
			j.changeHealth(-GetEnemyDamage(enemyType));
		attackChecked = true;
	}
	
	
	 
	 void chanegWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	 
	public int getAniIndex() {
		 return this.index;
	 }
	 
	 public int getEnemyState() {
		 return this.enemyState;
	 }
	 
	 public boolean isActive() {
		 return active;
	 }
	 
	 
	 

}
