package entities;

import static utils.Constantes.Directions.*;
import static utils.Constantes.EnemyConstantes.*;
import static utils.HelpMethods.CanMoveHere;
import static utils.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utils.HelpMethods.IsEntityOnFloor;
import static utils.HelpMethods.IsFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Crabby extends Enemy{
	//Attackbox
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;
	
	
	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitbox(x,y, (int)(22* Game.SCALE), (int)(19*Game.SCALE));
		initAttackBox();
	}
	
	 public void update(int[][] lvlData, Joueur joueur) {
		 updateBehaviour(lvlData, joueur);
		 updateAnimationTick();
		 updateAttackBox();
		 
	 }
	
	 private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
		
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(82 * Game.SCALE), (int)(19 * Game.SCALE));
		attackBoxOffsetX = (int)(Game.SCALE * 30);
		
	}
	
	//Previously named updateMove
	private void updateBehaviour(int[][] lvlData, Joueur joueur) {
		 if(firstUpdate) 
			 firstUpdateCheck(lvlData);
			
		 if(inAir)
			 updateInAir(lvlData);
		 
		 else {
			 //Patrooling part
			 switch(enemyState) {
			 	case IDLE:
			 		newState(RUNNING);
			 		break;
			 	case RUNNING:
			 		if(canSeePlayer(lvlData, joueur)) {
			 			turnTowardsPlayer(joueur);

			 			if(isPlayercloseForAttack(joueur))
			 				newState(ATTACK);
			 		}
			 		move(lvlData);
			 		break;
			 	case ATTACK:
			 		if(index == 0)
			 			attackChecked = false;
			 		if(index == 3 && !attackChecked)
			 			checkEnemyHit(attackBox,joueur);
			 		break;
			 	case HIT:
			 		break;
				 
			 }
		 }
	 }
	
	
	public void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}
	
	
	 
	 public int flipX() {
		 if(walkDir == RIGHT)
			 return width;
		 else
			 return 0;
		 
	 }
	 
	 public int flipW() {
		if(walkDir == RIGHT)
			return -1;
		else
			return 1;
	 }

	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
	}

}
