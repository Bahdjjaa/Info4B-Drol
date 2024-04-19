package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Niveaux.Level;
import gamestates.Playing;
import main.Game;
import objets.Porte;
import utils.LoadSave;
import static utils.Constantes.EnemyConstantes.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] crabbyArr;
	private ArrayList<Crabby> crabbies;
	private int nbCrabbies;
	private static final  int SPAWN_INTERVAL = 10000;
	

	
	
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		this.crabbies = new ArrayList<Crabby>();
		loadEnemyImgs();
	}
	
	public void loadEnemies(Level lvl) {
		crabbies.clear();
		ArrayList<Porte> portes = lvl.getPortes();
		//nbCrabbies = lvl.getMaxEnnemies();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			//int n = 0;
			@Override
			public void run() {	
				for(Porte p: portes) {
					/*if(n >= nbCrabbies) {
						timer.cancel();
						return;
					}*/
					float spawnX = p.getHitbox().x;
					float spawnY = p.getHitbox().y + (int)(p.getyDrawOffset()*1.6);
					crabbies.add(new Crabby(spawnX, spawnY));
					//n++;
				}	
			}
		}, 2000, SPAWN_INTERVAL);
		
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
	
	public void enemyOut(Rectangle2D.Float hitBox) {
		for(Crabby c: crabbies) {
			if(c.isActive()) 
				if(hitBox.intersects(c.getHitbox()))
					c.out();
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
