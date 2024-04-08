package ui;

import static utils.Constantes.UI.PauseButtons.SOUND_SIZE;
import static utils.Constantes.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constantes.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gamestates.Gamestate;
import main.Game;

public class Options {
	
	private SoundButton musicButton, sfxButton;
	private VolumeButton volumeButton;

		
	public Options() {
		createSoundButtons();
		createVolumeButton();
	}
	
	private void createVolumeButton() {
		int vX = (int)(190 * Game.SCALE);
		int vY = (int)(280 *Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}
	
	private void createSoundButtons() {
		int soundX = (int)(310 * Game.SCALE);
		int musicY = (int)(182* Game.SCALE);
		int sfxY = (int)(227* Game.SCALE);
		
		this.musicButton = new SoundButton(soundX,musicY, SOUND_SIZE, SOUND_SIZE);
		this.sfxButton = new SoundButton(soundX,sfxY, SOUND_SIZE, SOUND_SIZE);
		
		
	}
	
	public void update() {
		musicButton.update();
		sfxButton.update();
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		//SoundButtons
		musicButton.draw(g);
		sfxButton.draw(g);
				
		//Volume slider
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if(isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
		
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, musicButton)) {
			if(musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted());
		}else if(isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		}
			
		
		musicButton.resetBools();
		sfxButton.resetBools();
		volumeButton.resetBools();
		
	}
	

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if(isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if(isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if(isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
		
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
