package entities;
import static utils.Constantes.Directions.*;
import static utils.Constantes.EquipageConstantes.*;

import main.Game;


public class Roi extends Equipage {

	public Roi(float x, float y) {
		super(x, y, ROI_WIDTH, ROI_HEIGHT, ROI);
		initHitbox(37, 25);
		xDrawOffset = (int)(9 * Game.SCALE);
		yDrawOffset = (int)(18 * Game.SCALE);
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
