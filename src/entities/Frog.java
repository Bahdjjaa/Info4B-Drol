package entities;

import static utils.Constantes.Directions.RIGHT;
import static utils.Constantes.EquipageConstantes.*;

import main.Game;

public class Frog extends Equipage{

	public Frog(float x, float y) {
		super(x, y, FROG_WIDTH, FROG_HEIGHT, FROG);
		initHitbox(23, 25);
		xDrawOffset = (int)(4 * Game.SCALE);
		yDrawOffset = (int)(6 * Game.SCALE);
	}
	
	public void update(int[][] lvlData) {
		updateAnimationTick();
		updateMouvement(lvlData);
		
	}
	
	public void updateMouvement(int[][] lvlData) {
		if(firstUpdate)
			firstUpdateCheck(lvlData);
		 if(inAir)
			 updateInAir(lvlData);
		 else {
			 mouvement(lvlData);
		 }
	}
	
	public int flipX() {
		 if(dirMarche == RIGHT)
			 return 0;
		 else
			 return width;
		 
	 }
	 
	 public int flipW() {
		if(dirMarche == RIGHT)
			return 1;
		else
			return -1;
	 }

}
