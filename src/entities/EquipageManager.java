package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Niveaux.Level;
import gamestates.Playing;
import utils.LoadSave;
import static utils.Constantes.EquipageConstantes.*;

public class EquipageManager {
	private Playing playing;
	private BufferedImage[] roi;
	private BufferedImage[] frog;
	private ArrayList<Roi> rois = new ArrayList<>();
	private ArrayList<Frog> frogs = new ArrayList<>();
	
	public EquipageManager(Playing playing) {
		this.playing = playing;
		loadEquipageImgs();
	}
	
	public void loadEquipage(Level lvl) {
		rois = lvl.getRois();
		frogs = lvl.getFrogs();
	}
	
	public void update(int[][] lvlData) {
		boolean isAnyActive = false;
		
		for(Roi r : rois) {
			if(r.isActive()) {
				r.update(lvlData);
				isAnyActive = true;
			}
		}
		
		for(Frog f : frogs) {
			if(f.isActive()) {
				f.update(lvlData);
				isAnyActive = true;
			}
		}
		
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	private void drawRois(Graphics g, int xLvlOffset) {
		for(Roi r: rois) 
			if(r.isActive()){
				g.drawImage(roi[r.getAniIndex()], (int)r.getHitbox().x - xLvlOffset- (int)r.getxDrawOffset() + r.flipX(), (int)r.getHitbox().y - (int)r.getyDrawOffset(), ROI_WIDTH * r.flipW(),ROI_HEIGHT, null);
			}
	}

	private void drawFrogs(Graphics g, int xLvlOffset) {
		for(Frog f: frogs) {
			if(f.isActive()) {
				g.drawImage(frog[f.getAniIndex()], (int)f.getHitbox().x - xLvlOffset - (int)f.getxDrawOffset() + f.flipX(), (int)f.getHitbox().y - (int)f.getyDrawOffset(), FROG_WIDTH * f.flipW(), FROG_HEIGHT, null);
			}
		}
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawRois(g, xLvlOffset);
		drawFrogs(g, xLvlOffset); 
	}
	
	public boolean isRescued(Rectangle2D.Float hitBox){
		for(Roi r : rois) {
			if(r.isActive())
				if(r.hitbox.intersects(hitBox)) {
					r.setActive(false);
					return true;
				}
		}
		
		for(Frog f : frogs) {
			if(f.isActive())
				if(hitBox.intersects(f.hitbox)) {
					f.setActive(false);
					return true;
				}
		}
		return false;
		
		
	}

	private void loadEquipageImgs() {
		this.roi = new BufferedImage[8];
		this.frog = new BufferedImage[12];
		
		BufferedImage temp1 = LoadSave.GetSpriteAtlas(LoadSave.ROI);
		BufferedImage temp2 = LoadSave.GetSpriteAtlas(LoadSave.FROG);
		
		for(int i = 0 ; i < roi.length; i++) {
			roi[i] = temp1.getSubimage( i * ROI_WIDTH_DEFAULT ,0, ROI_WIDTH_DEFAULT, ROI_HEIGHT_DEFAULT);
		}
		for(int i = 0; i < frog.length; i++) {
			frog[i] = temp2.getSubimage(i * FROG_WIDTH_DEFAULT, 0, FROG_WIDTH_DEFAULT, FROG_HEIGHT_DEFAULT);
		}
	}
	
	public void resetAllEquipage() {
		for(Roi r : rois)
			r.reset();
		
		for(Frog f: frogs)
			f.reset();
	}
}
