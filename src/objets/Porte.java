package objets;

import static utils.Constantes.ObjetsConstantes.PORTE;

import main.Game;


public class Porte extends Objets {
	
	public Porte(int x, int y) {
		super(x, y, PORTE);
		initHitbox(39,49);
		xDrawOffset = (int)(13 * Game.SCALE);
		yDrawOffset = (int)(16 * Game.SCALE);
		hitbox.y -= yDrawOffset + (int)(Game.SCALE * 2);  
		
	}
	
	public void update() {
		updateAniTick();
	}

}
