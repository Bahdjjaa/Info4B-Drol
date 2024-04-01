package ui;

import static utils.Constantes.UI.URMButtons.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class LevelCompletedOverlay {

	private Playing playing;
	private urmButton menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;
	
	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImage();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int)(330 * Game.SCALE);
		int nextX = (int)(445 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		
		next = new urmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
		menu = new urmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
		
	}

	private void initImage() {
		img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
		this.bgW = (int)(img.getWidth() * Game.SCALE);
		this.bgH = (int)(img.getHeight() * Game.SCALE);
		this.bgX = Game.GAME_WIDTH / 2 - this.bgW /2;
		this.bgY = (int)(75* Game.SCALE);
	}
	
	public void update() {
		next.update();
		menu.update();
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
		menu.draw(g);
	}
	
	private boolean isIn(urmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		
		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(next, e))
			next.setMouseOver(true);
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;
			}
		}else if(isIn(next, e))
			if(next.isMousePressed()) {
				playing.loadNextLevel();
			}
		
		menu.resetBools();
		next.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(next, e))
			next.setMousePressed(true);
		
				
	}
}















