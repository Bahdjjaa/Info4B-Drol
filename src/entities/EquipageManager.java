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
	private ArrayList<Roi> rois = new ArrayList<>();
	
	public EquipageManager(Playing playing) {
		this.playing = playing;
		loadEquipageImgs();
	}
	
	public void loadEquipage(Level lvl) {
		rois = lvl.getRois();
	}
	
	public void update(int[][] lvlData) {
		boolean isAnyActive = false;
		for(Roi r : rois) {
			if(r.isActive()) {
				r.update(lvlData);
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
	
	public void draw(Graphics g, int xLvlOffset) {
		drawRois(g, xLvlOffset);
	}
	
	public void isRescued(Rectangle2D.Float hitBox){
		for(Roi r : rois) {
			if(r.isActive())
				if(hitBox.intersects(r.hitbox)) {
					r.setActive(false);
					return;
				}
		}
	}

	private void loadEquipageImgs() {
		this.roi = new BufferedImage[8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ROI);
		for(int i = 0 ; i < roi.length; i++) {
			roi[i] = temp.getSubimage( i * ROI_WIDTH_DEFAULT ,0, ROI_WIDTH_DEFAULT, ROI_HEIGHT_DEFAULT);
		}
	}
	
	public void resetAllEquipage() {
		for(Roi r : rois)
			r.reset();
	}
}
