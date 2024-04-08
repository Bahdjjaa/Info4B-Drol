package ui;

import static utils.Constantes.UI.URMButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class GameOverOverlay {
	
	private Playing playing;
	private BufferedImage img;
	private int imgX, imgY,imgW, imgH;
	private urmButton menu, joue;
	
	
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		initImage();
		initBoutons();
		
	}
	
	private void initBoutons() {
		int menuX = (int)(335 * Game.SCALE);
		int joueX = (int)(440 * Game.SCALE);
		int y = (int) (170 * Game.SCALE);
		
		joue = new urmButton(joueX, y, URM_SIZE, URM_SIZE, 0);
		menu = new urmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
		
		
	}

	private void initImage() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAMEOVER_SCREEN);
		imgW = (int)(img.getWidth() * Game.SCALE);
		imgH = (int)(img.getHeight() * Game.SCALE);
		imgX = Game.GAME_WIDTH / 2 - imgW /2;
		imgY = (int)(75* Game.SCALE);
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		g.drawImage(img, imgX, imgY, imgW, imgH, null);
		menu.draw(g);
		joue.draw(g);

	}
	
	public void update() {
		joue.update();
		menu.update();
	}
	
	private boolean isIn(urmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public void mouseMoved(MouseEvent e) {
		joue.setMouseOver(false);
		menu.setMouseOver(false);
		
		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(joue, e))
			joue.setMouseOver(true);
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;
			}
		}else if(isIn(joue, e))
			if(joue.isMousePressed()) {
				playing.resetAll();
			}
		
		menu.resetBools();
		joue.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(joue, e))
			joue.setMousePressed(true);
		
				
	}
	
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			Gamestate.state = Gamestate.MENU;
		}
	}
}
