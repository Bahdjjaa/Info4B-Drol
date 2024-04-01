package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Niveaux.Level;
import gamestates.Playing;
import utils.LoadSave;
import static utils.Constantes.EnemyConstantes.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] crabbyArr;
	private ArrayList<Crabby> crabbies = new ArrayList<>();
	
	
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	
	public void loadEnemies(Level lvl) {
		crabbies = lvl.getCrabs();
		
	}

	public void update(int[][] lvlData, Joueur joueur) {
		for(Crabby c: crabbies)
			if(c.isActive())
				c.update(lvlData, joueur);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCrabs(g, xLvlOffset);
	}
	
	private void drawCrabs(Graphics g, int xLvlOffset) {
		for(Crabby c : crabbies) 
			if(c.isActive()){
				g.drawImage(crabbyArr[c.getEtat()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset- CRABBY_DRAWOFFSET_X + c.flipX(), (int)c.getHitbox().y - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH * c.flipW(),CRABBY_HEIGHT, null);
				//c.drawAttackBox(g, xLvlOffset);
			}
	}
	
	public void enemyHit(Rectangle2D.Float attackBox) {
		for(Crabby c : crabbies)
			if(c.isActive())
			 if(attackBox.intersects(c.getHitbox())) { //Made a change here
				c.hurt(10);
				return;
			 }
	}

	private void loadEnemyImgs() {
		this.crabbyArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
		for(int i = 0 ; i < crabbyArr.length; i++) {
			for (int j = 0 ; j < crabbyArr[i].length ; j++) {
				crabbyArr[i][j] = temp.getSubimage(j * CRABBY_WIDTH_DEFAULT , i* CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
			}
		}
		
	}

	public void resetAllEnemies() {
		for(Crabby c: crabbies)
			c.resetEnemy();
	}
	
	
}
