package ui;

import static utils.Constantes.UI.PauseButtons.SOUND_SIZE;
import static utils.Constantes.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constantes.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gamestates.Gamestate;
import main.Game;

public class OptionsAudio {
	
	private SoundButton musicButton, sfxButton;
	private VolumeButton volumeButton;
	private int vX, vY, soundX, musicY, sfxY;
	private Game game;


	public OptionsAudio(Game game) {
		this.game = game;
		setSoundButtons();
		createSoundButtons();
		createVolumeButton();
	}
	

	private void setSoundButtons() {
		setMusicY((int)(140* Game.SCALE));
		setSfxY((int)(185 * Game.SCALE));
		setSoundX((int)(345 * Game.SCALE));
		setvX((int)(210 * Game.SCALE));
		setvY((int)(278 * Game.SCALE));
	}

	private void createVolumeButton() {
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}
	
	private void createSoundButtons() {
		
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
			float valInit = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valApres = volumeButton.getFloatValue();
			if(valInit != valApres) {
				game.getAudioManager().setVolume(valApres);
			}
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
			if(musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
				game.getAudioManager().sonsMute();
			}
		}else if(isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted());
				game.getAudioManager().effetsMute();
			}
				
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
	
	public int getvX() {
		return vX;
	}



	public void setvX(int vX) {
		this.vX = vX;
	}



	public int getvY() {
		return vY;
	}



	public void setvY(int vY) {
		this.vY = vY;
	}



	public int getSoundX() {
		return soundX;
	}



	public void setSoundX(int soundX) {
		this.soundX = soundX;
	}



	public int getMusicY() {
		return musicY;
	}



	public void setMusicY(int musicY) {
		this.musicY = musicY;
	}



	public int getSfxY() {
		return sfxY;
	}



	public void setSfxY(int sfxY) {
		this.sfxY = sfxY;
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
