/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import Network.packets.Packet02Move;
import Network.packets.Packet03Attack;
import Network.packets.Packet04Direction;
import Network.packets.Packet05Score;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.Constantes.Directions.*;
import static utils.HelpMethods.*;
import static utils.Constantes.PlayerConstants.GetSpriteAmount;
import static utils.Constantes.PlayerConstants.*;
import static utils.Constantes.*;

/**
 *
 * @author bahdjjaa
 */
public class Joueur extends Entity{
    
    private String username;
	private BufferedImage [][] animations;
    private boolean moving = false, attacking = false;
    private int score;
    private boolean estJoueurLocal;
    private boolean left, right, jump;
    private int [][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    
    //Jumping and Gravity
    private float jumpSpeed = -1.75f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    
    
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
	
	
	private int healthWidth = healthBarWidth;
	
	
	//Pour changer la direction du sprite
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked = false;
	private Playing playing;
	
	private boolean mort = false;
    
    public Joueur(float x, float y, int width, int height,String username, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        this.etat = IDLE;
        this.maxVie = 100;
        this.vie = maxVie;
        this.setScore(0);
        this.vitesseMarche = 1.0f * Game.SCALE;
        this.username = username;
        this.loadAnimations();
        initHitbox(20,27);
        initAttackBox();
          
    }
 
     
    private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
		
	}

	public void update(){
		updateHealthBar();
		
		if(vie <= 0) {
			if(etat != DEAD) {
				etat = DEAD;
				tick = 0; 
				index = 0;
				playing.setJoueurMort(true);
			}else if(index == GetSpriteAmount(DEAD) - 1 && tick >= VITESSE_ANIMATION - 1) {
				playing.setGameOver(true);
			}else {
				updateAnimationTick();
			}
			return;
		}

    	updateAttackBox();
    	updatePos();
    	
    	if(moving) {
    		checkRescue(hitbox);
    	}
    		
    	if(attacking) {
    		checkAttack();

    	}
    	
        updateAnimationTick();
        setAnimation();
        
    }
    
    private void checkAttack() {
		if(attackChecked || index != 1)
			return;
		attackChecked = true;
		boolean hit = playing.checkEnemyHit(attackBox);	
		if(hit) {
			score += 10;
			envoieScore();
		}
		
		
	}
    
    private void envoieScore() {
    	 if (this.estJoueurLocal) {  // Vérifiez si c'est le joueur local qui doit envoyer le score
    	        Packet05Score packet = new Packet05Score(this.getUsername(), this.score);
    	        packet.writeData(Game.game.getJoueurSocket());  // Envoie le paquet au serveur
    	    }
		
	}


	public void checkRescue(Rectangle2D.Float hitbox) {
		boolean rescued = playing.checkMemberRescue(hitbox);
		if(rescued) {
			score += 15;
			envoieScore();
		}
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
		healthWidth = (int)((vie / (float)maxVie) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset){
        g.drawImage(this.animations[this.etat][this.index],
        		(int)this.hitbox.x - (int)xDrawOffset - lvlOffset + flipX,
        		(int)this.hitbox.y - (int)yDrawOffset,
        		width*flipW,
        		height,
        		null);
        //Add username above the player.
        drawUI(g);
    }
    
	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		
		if(username != null) {
			int nameX = (int)(this.getHitbox().x - xDrawOffset - (username.length()*3.5) + width /2);
			int nameY = (int)(this.getHitbox().y - yDrawOffset - 20);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
			g.drawString(username, nameX, nameY);
		}
		
		int scoreX = statusBarY + statusBarWidth;
		int scoreY = statusBarY +  statusBarHeight/2;
		
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));
		g.drawString(""+score, scoreX, scoreY);
		
		
	}
	
	public boolean estJoueurLocal() {
		return estJoueurLocal;
	}

	public void setJoueurLocal(boolean estJoueurLocal) {
		this.estJoueurLocal = estJoueurLocal;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setMoving(boolean moving){
        this.moving = moving;
    }
    
    private void updateAnimationTick() {
        this.tick++;
        if(this.tick >= VITESSE_ANIMATION){
            this.tick = 0; 
            this.index++;
            if(this.index >= GetSpriteAmount(this.etat)){
                this.index = 0;
                this.attacking = false;
                this.attackChecked = false;
            }
        }
        
    }
    private void setAnimation() {
    	
    	int startAni = this.etat;
    	
        if(moving)
        	this.etat= RUNNING;
        else
        	this.etat = IDLE;
        
        if(inAir) {
        	if(vitesseAir < 0)
        		this.etat = JUMP;
        	else
        		this.etat = FALLING;
        }
        
        if(attacking) {
        	this.etat = ATTACK;
        	if(startAni != ATTACK) {
        		index = 1;
        		tick = 0;
        		return;
        	}
        }
        
        if(startAni != this.etat)
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
    		xSpeed -= vitesseMarche;
    		flipX = width;
    		flipW = -1;
    	}
    	if (right) {
    	  	xSpeed += vitesseMarche;
    	  	flipX = 0;
    	  	flipW = 1;
    	}
    	if(!inAir)
    		if(!IsEntityOnFloor(hitbox, lvlData))
    			inAir = true;
 
    	if(inAir) {
    		if(CanMoveHere(hitbox.x, hitbox.y + vitesseAir, hitbox.width, hitbox.height, lvlData)) {
    			hitbox.y += vitesseAir;
    			vitesseAir += GRAVITE;
    			updateXPos(xSpeed);
    		}
    		else {
    			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, vitesseAir);
    			if(vitesseAir > 0) {
    				resetInAir();
    			}else {
    				vitesseAir = this.fallSpeedAfterCollision;
    			}
    			updateXPos(xSpeed);
    		}
    	}else{
    		updateXPos(xSpeed);
    	}
    	moving = true;
    	if(this.estJoueurLocal) {
    		Packet02Move packet = new Packet02Move(this.getUsername(), this.hitbox.x, this.hitbox.y);
        	packet.writeData(Game.game.getJoueurSocket());
    	}
    }
    
    private void jump() {
		if(inAir)
			return;
		inAir = true;
		vitesseAir = this.jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		vitesseAir = 0;
		
	}

	private void updateXPos(float xSpeed) {
    	if(CanMoveHere(this.hitbox.x +xSpeed,this.hitbox.y,this.hitbox.width, this.hitbox.height, lvlData)) {
    		this.hitbox.x += xSpeed; 
    	}else {
    		this.hitbox.x = GetEntityPosNextToWall(hitbox,xSpeed);
    	}
		
	}
	
	public void changeHealth(int val) {
		 vie += val;
		 if(vie <= 0) {
			 vie = 0;

		 }else if( vie >= maxVie) {
			 vie = maxVie;
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
		
	}
    

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	
	public void setAttack(boolean a) {
		this.attacking = a;
	}
	public void setJump(boolean j) {
		this.jump = j;
	}
	
	public boolean isJump() {
		return jump;
	}
	
	public boolean isAttacking() {
		return attacking;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		etat = IDLE;
		vie = maxVie;
		mort = false;
		hitbox.x = x;
		hitbox.y = y;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		
	}


	public boolean estMort() {
		return mort;
	}


	public void setMort(boolean mort) {
		this.mort = mort;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}

}
