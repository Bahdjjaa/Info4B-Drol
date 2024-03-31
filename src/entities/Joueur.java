/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.Constantes.Directions.*;
import static utils.HelpMethods.*;
import static utils.Constantes.PlayerConstants.GetSpriteAmount;
import static utils.Constantes.PlayerConstants.*;

/**
 *
 * @author bahdjjaa
 */
public class Joueur extends Entity {
    
    private BufferedImage [][] animations;
    private int tick, index, speed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float vitesseJoueur = 1.0f * Game.SCALE;
    private int [][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    
    //Jumping and Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    
    //Status bar UI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	
	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	//Attack box
	private Rectangle2D.Float attackBox;
	
	
	//Pour changer la direction du sprite
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked = false;
	private Playing playing;
	
    
    
    public Joueur(float x, float y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        this.loadAnimations();
        initHitbox(x, y, (int)(20*Game.SCALE),(int)( 27*Game.SCALE));
        initAttackBox();
          
    }
    
    public void setSpawn(Point spawn) {
    	x = spawn.x;
    	y = spawn.y;
    	
    	hitbox.x = x;
    	hitbox.y = y;
    }
     
    private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
		
	}

	public void update(){
		updateHealthBar();
		
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}

    	updateAttackBox();
    	updatePos();
    	if(attacking)
    		checkAttack();
    	
        updateAnimationTick();
        setAnimation();
        
    }
    
    private void checkAttack() {
		if(attackChecked || index != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
		}else if(left) {
			attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
		}
		attackBox.y = hitbox.y + (Game.SCALE * 10);
		
	}

	private void updateHealthBar() {
		healthWidth = (int)((currentHealth / (float)maxHealth) * healthBarWidth);
		
	}

	public void render(Graphics g, int lvlOffset){
        g.drawImage(this.animations[this.playerAction][this.index],
        		(int)this.hitbox.x - (int)xDrawOffset - lvlOffset + flipX,
        		(int)this.hitbox.y - (int)yDrawOffset,
        		width*flipW,
        		height,
        		null);
        //drawHitbox(g, lvlOffset);
        //drawAttackBox(g, lvlOffset);
        
        drawUI(g);
    }
    
    private void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(Color.pink);
		g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
		
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		
	}

	public void setMoving(boolean moving){
        this.moving = moving;
    }
    
    private void updateAnimationTick() {
        this.tick++;
        if(this.tick >= this.speed){
            this.tick = 0; 
            this.index++;
            if(this.index >= GetSpriteAmount(this.playerAction)){
                this.index = 0;
                this.attacking = false;
                this.attackChecked = false;
            }
        }
        
    }
    private void setAnimation() {
    	
    	int startAni = playerAction;
    	
        if(moving)
            this.playerAction = RUNNING;
        else
            this.playerAction = IDLE;
        
        if(inAir) {
        	if(airSpeed< 0)
        		playerAction = JUMP;
        	else
        		playerAction = FALLING;
        }
        
        if(attacking) {
        	this.playerAction = ATTACK;
        	if(startAni != ATTACK) {
        		index = 1;
        		tick = 0;
        		return;
        	}
        }
        
        if(startAni != playerAction)
        	resetAniTick();
    }

    private void resetAniTick() {
		this.tick = 0;
		this.index = 0;
		
	}

	private void updatePos() {
    	
    	this.moving = false;
    	if(this.jump) 
    		jump();
  
    	
    	if(!inAir)
    		if((!left && !right) || (left && right))
    			return;
    	
    	
    	float xSpeed = 0;
    	
    	if(left) {
    		xSpeed -= vitesseJoueur;
    		flipX = width;
    		flipW = -1;
    	}
    	if (right) {
    	  	xSpeed += vitesseJoueur;
    	  	flipX = 0;
    	  	flipW = 1;
    	}
    	if(!inAir)
    		if(!IsEntityOnFloor(hitbox, lvlData))
    			inAir = true;
 
    	if(inAir) {
    		if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
    			hitbox.y += airSpeed;
    			airSpeed += gravity;
    			updateXPos(xSpeed);
    		}
    		else {
    			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
    			if(airSpeed > 0) {
    				resetInAir();
    			}else {
    				airSpeed = this.fallSpeedAfterCollision;
    			}
    			updateXPos(xSpeed);
    		}
    	}else{
    		updateXPos(xSpeed);
    	}
    	moving = true;
    
    }
    
    private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = this.jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}

	private void updateXPos(float xSpeed) {
    	if(CanMoveHere(this.hitbox.x +xSpeed,this.hitbox.y,this.hitbox.width, this.hitbox.height, lvlData)) {
    		this.hitbox.x += xSpeed; 
    	}else {
    		this.hitbox.x = GetEntityPosNextToWall(hitbox,xSpeed);
    	}
		
	}
	
	public void changeHealth(int val) {
		 currentHealth += val;
		 if(currentHealth <= 0) {
			 currentHealth = 0;
			 // Game Oveerrr
			 
		 }else if( currentHealth >= maxHealth) {
			 currentHealth = maxHealth;
		 }
	}

	private void loadAnimations() {
    	BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);         
        this.animations = new BufferedImage[7][8];
        for(int i = 0 ; i < this.animations.length; i++){
        	for(int j = 0 ; j < this.animations[i].length; j++){
        		this.animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
            }
       }
         
       statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }
    
    public void loadLvlData(int[][] lvlData) {
    	this.lvlData = lvlData;
    	if(!IsEntityOnFloor(hitbox, lvlData))
    		inAir = true;
    		
    }
    
    public void resetDirBooleans() {
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
		
	}
    

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setAttack(boolean a) {
		this.attacking = a;
	}
	public void setJump(boolean j) {
		this.jump = j;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		
		hitbox.x = x;
		hitbox.y = y;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		
	}

}
