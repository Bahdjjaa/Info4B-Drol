package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constantes.UI.PauseButtons.*;
import static utils.Constantes.UI.URMButtons.*;
import static utils.Constantes.UI.VolumeButtons.*;

public class PauseOverlay {
	
	private Playing playing;
	private BufferedImage background;
	private int bgX, bgY, bgW, bgH;
	
	private urmButton menuB, replayB, unpauseB;
	private OptionsAudio optionsAudio;
	
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		optionsAudio = playing.getGame().getAudioOptions();
		createUrmButtons();
	}
	
	private void createUrmButtons() {
		int menuX = (int)(213 * Game.SCALE);
		int replayX = (int)(287 * Game.SCALE);
		int unpauseX = (int)(362 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);
		
		menuB = new urmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new urmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new urmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
		
	}
	
	
	private void loadBackground() {
		this.background = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		this.bgW = (int)(this.background.getWidth() * Game.SCALE);
		this.bgH = (int)(this.background.getHeight() * Game.SCALE);
		this.bgX = (int)(Game.GAME_WIDTH / 2 -this.bgW/2) ;
		this.bgY = (int)(23 * Game.SCALE);
		
	}
	public void update() {
				
		menuB.update();
		replayB.update();
		unpauseB.update();
		
		optionsAudio.update();
		
	}
	
	public void draw(Graphics g) {
		//Background
		g.drawImage(background, bgX, bgY,bgW, bgH,null);
		
		//urm Buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		
		optionsAudio.draw(g);
		
		
	}
	
	public void mouseDragged(MouseEvent e) {
		optionsAudio.mouseDragged(e);
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(e, menuB))
			menuB.setMousePressed(true);
		else if(isIn(e, replayB))
			replayB.setMousePressed(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else
			optionsAudio.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {
		if(isIn(e, menuB)) {
			if(menuB.isMousePressed())
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
		}else if(isIn(e, replayB)) {
			if(replayB.isMousePressed())
			{
				playing.resetAll();
				playing.unpauseGame();
			}
		}else if(isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed())
				playing.unpauseGame();
		}else
			optionsAudio.mouseReleased(e);
		
			
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		
	}

	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
	
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else if(isIn(e, replayB))
			replayB.setMouseOver(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else
			optionsAudio.mouseMoved(e);
	
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
