package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.OptionsAudio;
import ui.urmButton;
import utils.LoadSave;

import static utils.Constantes.UI.URMButtons.*;

public class OptionsJeu extends state implements Statemethods{

	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private urmButton menuBtn;
	private OptionsAudio optionsAudio;
	
	public OptionsJeu(Game game) {
		super(game);
		initBoutons();
		initImages();
		
		optionsAudio = game.getAudioOptions();
		
	}

	private void initBoutons() {	
		int menuBtnX = (int)(270 * Game.SCALE);
		int menuBtnY = (int)(330 * Game.SCALE);
		
		menuBtn = new urmButton(menuBtnX, menuBtnY, URM_SIZE, URM_SIZE, 2);
	}

	private void initImages() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
		optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_OPTIONS);
		bgW = (int)(optionsBackgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(optionsBackgroundImg.getHeight() * Game.SCALE);
		bgX = (int)(Game.GAME_WIDTH / 2 - this.bgW/2);
		bgY = (int)(23 * Game.SCALE);
	}

	@Override
	public void update() {
		menuBtn.update();
		optionsAudio.update();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

		menuBtn.draw(g);
		optionsAudio.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		optionsAudio.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(isIn(menuBtn, e))
			menuBtn.setMousePressed(true);
		else
			optionsAudio.mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isIn(menuBtn, e))
			if(menuBtn.isMousePressed())
				Gamestate.state = Gamestate.MENU;
			else
				optionsAudio.mouseReleased(e);
			
		menuBtn.resetBools();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuBtn.setMouseOver(false);
		if(isIn(menuBtn, e))
			menuBtn.setMouseOver(true);
		else
			optionsAudio.mouseMoved(e);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			Gamestate.state = Gamestate.MENU;
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isIn(urmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	
}
