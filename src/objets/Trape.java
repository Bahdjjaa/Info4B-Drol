package objets;
import static utils.Constantes.ObjetsConstantes.TRAPE;

public class Trape extends Objets{

	public Trape(int x, int y) {
		super(x, y, TRAPE);
		initHitbox(128, 32);
	}
	
	public void update() {
		updateAniTick();
	}
	

}
