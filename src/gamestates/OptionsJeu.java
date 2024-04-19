package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import modesjeu.Modejeu;
import ui.ModeJeuButtons;
import ui.NombreJoueursButtons;
import ui.OptionsAudio;
import ui.PauseButton;
import ui.urmButton;
import utils.LoadSave;

import static utils.Constantes.UI.URMButtons.*;
import static utils.Constantes.UI.Buttons.*;

public class OptionsJeu extends state implements Statemethods{

	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private urmButton menuBtn;
	private NombreJoueursButtons deuxBtn, quatreBtn, sixBtn;
	private ModeJeuButtons soloBtn, combatBtn;
	private OptionsAudio optionsAudio;
	private int nbPlayers = 0;

	public OptionsJeu(Game game) {
		super(game);
		initBoutons();
		initImages();
		
		optionsAudio = game.getAudioOptions();
		
	}

	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	private void initBoutons() {	
		int menuBtnX = (int)(290 * Game.SCALE);
		int menuBtnY = (int)(330 * Game.SCALE);
		
		int deuxBtnX = (int)(465 * Game.SCALE);
		int quatreBtnX = (int)(531 * Game.SCALE);
		int sixBtnX = (int)(597 * Game.SCALE);
		int nbJrsBtnY = (int)(260 * Game.SCALE);
		
		int modeBtnX = (int)(490 * Game.SCALE);
		int soloY = (int)(130 * Game.SCALE);
		int combatY = (int)(320 * Game.SCALE);
		
		menuBtn = new urmButton(menuBtnX, menuBtnY, URM_SIZE, URM_SIZE, 2);
		
		deuxBtn = new NombreJoueursButtons(deuxBtnX, nbJrsBtnY, URM_SIZE, URM_SIZE, 0);
		quatreBtn = new NombreJoueursButtons(quatreBtnX, nbJrsBtnY, URM_SIZE, URM_SIZE, 1);
		sixBtn = new NombreJoueursButtons(sixBtnX, nbJrsBtnY, URM_SIZE,URM_SIZE, 2);
		
		soloBtn = new ModeJeuButtons(modeBtnX, soloY, B_WIDTH, B_HEIGHT, 0);
		combatBtn = new ModeJeuButtons(modeBtnX, combatY, B_WIDTH, B_HEIGHT, 1);
	}

	private void initImages() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
		optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_OPTIONS);
		bgW = (int)(optionsBackgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(optionsBackgroundImg.getHeight() * Game.SCALE);
		bgX = (int)(Game.GAME_WIDTH / 2 - this.bgW/2 - 15);
		bgY = (int)(33 * Game.SCALE);
	}

	@Override
	public void update() {
		menuBtn.update();
		deuxBtn.update();
		quatreBtn.update();
		sixBtn.update();
		optionsAudio.update();
		soloBtn.update();
		combatBtn.update();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

		menuBtn.draw(g);
		deuxBtn.draw(g);
		quatreBtn.draw(g);
		sixBtn.draw(g);
		optionsAudio.draw(g);
		soloBtn.draw(g);
		combatBtn.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		optionsAudio.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(isIn(menuBtn, e))
			menuBtn.setMousePressed(true);
		else if(isIn(deuxBtn, e))
			deuxBtn.setMousePressed(true);
		else if(isIn(quatreBtn, e))
			quatreBtn.setMousePressed(true);
		else if(isIn(sixBtn, e))
			sixBtn.setMousePressed(true);
		else if(isIn(soloBtn, e))
			soloBtn.setMousePressed(true);
		else if(isIn(combatBtn, e))
			combatBtn.setMousePressed(true);
		else
			optionsAudio.mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isIn(menuBtn, e)) {
			if(menuBtn.isMousePressed())
				Gamestate.state = Gamestate.MENU;
		}else if(isIn(deuxBtn, e)) {
			if(deuxBtn.isMousePressed()) {
				boolean ok = deuxBtn.isSelected();
				deuxBtn.setSelected(!ok);
				quatreBtn.setSelected(false);
				sixBtn.setSelected(false);
				soloBtn.setSelected(false);
				if(ok) {
					setNbPlayers(2);
					Modejeu.mode = Modejeu.COOPERATIF;
				}
				else {
					setNbPlayers(0);
				}
	
			}
		}else if(isIn(quatreBtn, e)) {
			if(quatreBtn.isMousePressed()) {
				boolean ok = quatreBtn.isSelected();
				quatreBtn.setSelected(!ok);
				deuxBtn.setSelected(false);
				sixBtn.setSelected(false);
				soloBtn.setSelected(false);
				if(ok) {
					setNbPlayers(4);
					Modejeu.mode = Modejeu.COOPERATIF;
				}
				else
					setNbPlayers(0);
			}
		}else if(isIn(sixBtn, e)) {
			if(sixBtn.isMousePressed()) {
				boolean ok = sixBtn.isSelected();
				sixBtn.setSelected(!ok);
				quatreBtn.setSelected(false);
				deuxBtn.setSelected(false);
				soloBtn.setSelected(false);
				if(ok) {
					setNbPlayers(6);
					Modejeu.mode = Modejeu.COOPERATIF;
				}
				else
					setNbPlayers(0);
			}
		}else if(isIn(soloBtn, e)) {
			if(soloBtn.isMousePressed()) {
				boolean ok = soloBtn.isSelected();
				soloBtn.setSelected(!ok);
				deuxBtn.setSelected(false);
				quatreBtn.setSelected(false);
				sixBtn.setSelected(false);
				combatBtn.setSelected(false);
				if(ok) {
					setNbPlayers(1);
					Modejeu.mode = Modejeu.SOLO;
				}
			}
		}else if(isIn(combatBtn, e)) {
			if(combatBtn.isMousePressed()) {
				boolean ok = combatBtn.isSelected();
				combatBtn.setSelected(!ok);
				//deuxBtn.setSelected(false);
				//quatreBtn.setSelected(false);
				//sixBtn.setSelected(false);
				soloBtn.setSelected(false);
				if(ok) {
					setNbPlayers(getNbPlayers());
					Modejeu.mode = Modejeu.SOLO;
				}
			}
		}else
			optionsAudio.mouseReleased(e);
			
		menuBtn.resetBools();
		deuxBtn.resetBools();
		quatreBtn.resetBools();
		sixBtn.resetBools();
		soloBtn.resetBools();
		combatBtn.resetBools();
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuBtn.setMouseOver(false);
		deuxBtn.setMouseOver(false);
		quatreBtn.setMouseOver(false);
		sixBtn.setMouseOver(false);
		soloBtn.setMouseOver(false);
		combatBtn.setMouseOver(false);
		
		if(isIn(menuBtn, e))
			menuBtn.setMouseOver(true);
		else if (isIn(deuxBtn, e)) 
			deuxBtn.setMouseOver(true);
		else if (isIn(quatreBtn, e))
			quatreBtn.setMouseOver(true);
		else if (isIn(sixBtn, e))
			sixBtn.setMouseOver(true);
		else if (isIn(soloBtn, e))
			soloBtn.setMouseOver(true);
		else if (isIn(combatBtn, e))
			combatBtn.setMouseOver(true);
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
	
	private boolean isIn(PauseButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	
}
