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
	
	//private Options optAudio;
	private urmButton menuB, replayB, unpauseB;
	
	private SoundButton musicButton, sfxButton;
	private VolumeButton volumeButton;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createUrmButtons();
		createSoundButtons();
		createVolumeButton();
		//optAudio = playing.getGame().getOptAudio();
		
	}
	
	private void createUrmButtons() {
		int menuX = (int)(313 * Game.SCALE);
		int replayX = (int)(387 * Game.SCALE);
		int unpauseX = (int)(462 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);
		
		menuB = new urmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new urmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new urmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
		
	}
	
	private void createVolumeButton() {
		int vX = (int)(309 * Game.SCALE);
		int vY = (int)(278 *Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}
	
	private void createSoundButtons() {
		int soundX = (int)(450 * Game.SCALE);
		int musicY = (int)(140 * Game.SCALE);
		int sfxY = (int)(186 * Game.SCALE);
		
		this.musicButton = new SoundButton(soundX,musicY, SOUND_SIZE, SOUND_SIZE);
		this.sfxButton = new SoundButton(soundX,sfxY, SOUND_SIZE, SOUND_SIZE);	
		
	}
	
	
	private void loadBackground() {
		this.background = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		this.bgW = (int)(this.background.getWidth() * Game.SCALE);
		this.bgH = (int)(this.background.getHeight() * Game.SCALE);
		this.bgX = Game.GAME_WIDTH / 2 - this.bgW /2;
		this.bgY = (int)(25* Game.SCALE);
		
	}
	public void update() {
				
		menuB.update();
		replayB.update();
		unpauseB.update();
		
		musicButton.update();
		sfxButton.update();
		volumeButton.update();
	
		//optAudio.update();
	}
	
	public void draw(Graphics g) {
		//Background
		g.drawImage(background, bgX, bgY,bgW, bgH,null);
		
		
		
		//urm Buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		
		//SoundButtons
		musicButton.draw(g);
		sfxButton.draw(g);
						
		//Volume slider
		volumeButton.draw(g);
		
		//optAudio.draw(g);
		
		
	}
	
	public void mouseDragged(MouseEvent e) {
		//optAudio.mouseDragged(e);
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(e, menuB))
			menuB.setMousePressed(true);
		else if(isIn(e, replayB))
			replayB.setMousePressed(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else if(isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if(isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
			
		
		//optAudio.mousePressed(e);
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
		}else if(isIn(e, musicButton)) {
			if(musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted());
		}else if(isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		}
			
			
			
			//optAudio.mouseReleased(e);
			
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		
		musicButton.resetBools();
		sfxButton.resetBools();
		volumeButton.resetBools();
		
	}

	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else if(isIn(e, replayB))
			replayB.setMouseOver(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else if(isIn(e, musicButton))
				musicButton.setMouseOver(true);
			else if(isIn(e, sfxButton))
				sfxButton.setMouseOver(true);
			else if(isIn(e, volumeButton))
				volumeButton.setMouseOver(true);
			//optAudio.mouseMoved(e);
		
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
