package objets;

import static utils.Constantes.ObjetsConstantes.PORTE_HEIGHT;
import static utils.Constantes.ObjetsConstantes.PORTE_WIDTH;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Niveaux.Level;
import entities.Enemy;
import gamestates.Playing;
import utils.LoadSave;

public class ObjetsManager {
	
	private Playing playing;
	private BufferedImage porte;
	private ArrayList<Porte> portes = new ArrayList<Porte>();
	
	public ObjetsManager(Playing playing) {
		this.playing = playing;
		loadObjetsImgs();
	
	}
	
	public void checkPorteTouche(Rectangle2D.Float hitbox) {
			playing.checkPorteTouche(hitbox);
	}

	
	public void loadObjets(Level lvl) {
		portes.clear();
		portes = lvl.getPortes();
	}

	private void loadObjetsImgs() {
		this.porte =  LoadSave.GetSpriteAtlas(LoadSave.PORTE);
	}
	
	public void update() {
		for(Porte p: portes) {
				p.update();
				checkPorteTouche(p.getHitbox());
		}
	}
	
	
	private void drawPortes(Graphics g, int xLvlOffset) {
		for(Porte p: portes) {
				g.drawImage(porte,
						(int)p.getHitbox().x - (int)p.getxDrawOffset() - xLvlOffset,
						(int)p.getHitbox().y - (int)p.getyDrawOffset(),
						PORTE_WIDTH ,
						PORTE_HEIGHT,
						null);
			
		}
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawPortes(g, xLvlOffset);
	}
	
}
